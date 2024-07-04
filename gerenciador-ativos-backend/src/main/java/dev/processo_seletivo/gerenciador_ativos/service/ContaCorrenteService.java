package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.entity.Movimentacao;
import dev.processo_seletivo.gerenciador_ativos.exception.SaldoNegativoException;
import dev.processo_seletivo.gerenciador_ativos.model.Posicao;
import dev.processo_seletivo.gerenciador_ativos.repository.ContaCorrenteRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository listaContasCorrente;

    @Autowired
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private AtivoFinanceiroService ativoFinanceiroService;

    public Optional<ContaCorrente> consultarContaCorrentePorId(Long contaCorrenteId) {
        return listaContasCorrente
            .findById(contaCorrenteId);
    }

    public BigDecimal consultarSaldoContaCorrente(Long contaCorrenteId, LocalDateTime dataPosicao) {
        ContaCorrente contaCorrente = contaCorrenteServiceHelper
            .consultarContaCorrentePorId(contaCorrenteId)
            .orElseThrow(() -> new NoSuchElementException("Conta corrente não encontrada"));

        return contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, dataPosicao);
    }

    public List<Posicao> consultarPosicoesContaCorrente(Long contaCorrenteId, LocalDateTime dataPosicao) {
        List<Movimentacao> movimentacoes = movimentacaoService.consultarMovimentacoesPorContaAteData(contaCorrenteId, dataPosicao);

        var movimentacoesPorAtivoPorTipo = movimentacoes
            .stream()
            .collect( // @formatter:off
                groupingBy(
                    Movimentacao::getAtivoFinanceiro,
                groupingBy(
                    Movimentacao::getTipo,
                mapping(
                    Movimentacao::getQuantidade,
                reducing(
                    BigDecimal.ZERO,
                    BigDecimal::add
                ))))); //@formatter:on

        List<Posicao> posicoesContaCorrente = new ArrayList<>();
        for (var movimentacaoPorTipo : movimentacoesPorAtivoPorTipo.entrySet()) {
            AtivoFinanceiro ativoFinanceiro = movimentacaoPorTipo.getKey();
            var movimentacao = movimentacaoPorTipo.getValue();
            BigDecimal quantidadeTotal = calcularQuantidadeTotal(movimentacao);
            BigDecimal rendimento = calcularRendimento(contaCorrenteId, dataPosicao, ativoFinanceiro);
            BigDecimal valorVendas = calcularValorVendas(contaCorrenteId, dataPosicao, ativoFinanceiro);
            BigDecimal valorCompras = calcularValorCompras(contaCorrenteId, dataPosicao, ativoFinanceiro);
            Posicao posicao = Posicao.of()
                .ativoFinanceiro(ativoFinanceiro)
                .quantidadeTotal(quantidadeTotal)
                .valorMercadoTotal(calcularValorMercadoTotal(quantidadeTotal, ativoFinanceiro))
                .rendimento(rendimento)
                .lucro(valorVendas.subtract(valorCompras))
                .build();
            posicoesContaCorrente.add(posicao);
        }

        return posicoesContaCorrente;
    }

    private @NotNull BigDecimal calcularValorMercadoTotal(@NotNull BigDecimal quantidadeTotal, AtivoFinanceiro ativoFinanceiro) {
        return quantidadeTotal.multiply(ativoFinanceiroService
            .consultarValorMercado(ativoFinanceiro)
            .getValor());
    }

    private @NotNull BigDecimal calcularValorCompras(Long contaCorrenteId, LocalDateTime dataPosicao, @NotNull AtivoFinanceiro ativoFinanceiro) {
        return movimentacaoService
            .consultarMovimentacoesCompra(contaCorrenteId, ativoFinanceiro.getId(), dataPosicao)
            .stream()
            .map(Movimentacao::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private @NotNull BigDecimal calcularValorVendas(Long contaCorrenteId, LocalDateTime dataPosicao, @NotNull AtivoFinanceiro ativoFinanceiro) {
        return movimentacaoService
            .consultarMovimentacoesVenda(contaCorrenteId, ativoFinanceiro.getId(), dataPosicao)
            .stream()
            .map(Movimentacao::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private @NotNull BigDecimal calcularRendimento(Long contaCorrenteId, LocalDateTime dataPosicao, AtivoFinanceiro ativoFinanceiro) {
        return ativoFinanceiroService
            .consultarValorMercado(ativoFinanceiro)
            .getValor()
            .divide(BigDecimal.valueOf(movimentacaoService
                    .consultarMovimentacoesCompra(contaCorrenteId, ativoFinanceiro.getId(), dataPosicao)
                    .stream()
                    .mapToDouble(m -> m.getValor().doubleValue())
                    .average()
                    .orElseThrow(() -> new RuntimeException("Não foi possível calcular o preço médio das compras.")))
                , RoundingMode.FLOOR);
    }

    private @NotNull BigDecimal calcularQuantidadeTotal(@NotNull Map<Movimentacao.TipoMovimentacao, BigDecimal> movimentacao) {
        BigDecimal quantidadeTotal = movimentacao
            .getOrDefault(Movimentacao.TipoMovimentacao.VENDA, BigDecimal.ZERO)
            .subtract(movimentacao.getOrDefault(Movimentacao.TipoMovimentacao.COMPRA, BigDecimal.ZERO));
        if (quantidadeTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNegativoException("A quantidade do ativo é negativa.");
        }
        return quantidadeTotal;
    }

}
