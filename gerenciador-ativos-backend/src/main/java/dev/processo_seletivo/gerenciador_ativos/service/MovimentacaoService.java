package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.dto.MovimentacaoDto;
import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.entity.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.entity.Movimentacao;
import dev.processo_seletivo.gerenciador_ativos.exception.ContaInexistenteException;
import dev.processo_seletivo.gerenciador_ativos.exception.DataMovimentacaoInvalidaException;
import dev.processo_seletivo.gerenciador_ativos.exception.QuantidadeAtivoInsuficienteException;
import dev.processo_seletivo.gerenciador_ativos.exception.SaldoInsuficienteException;
import dev.processo_seletivo.gerenciador_ativos.repository.MovimentacaoRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.stream.Collectors.*;

@Service
public class MovimentacaoService {

    @Autowired
    private AtivoFinanceiroService ativoFinanceiroService;

    @Autowired
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Transactional
    @SneakyThrows
    public Movimentacao incluirMovimentacao(@NotNull MovimentacaoDto movimentacaoDto) {
        AtivoFinanceiro ativoFinanceiro = ativoFinanceiroService.consultarAtivoFinanceiroPorId(movimentacaoDto.getAtivoFinanceiroId());
        if (movimentacaoDto.getData().isBefore(ativoFinanceiro.getDataEmissao())
            || movimentacaoDto.getData().isAfter(ativoFinanceiro.getDataVencimento())
            || movimentacaoDto.getData().isEqual(ativoFinanceiro.getDataVencimento())
            || ehFinalDeSemana(movimentacaoDto.getData())) {
            throw new DataMovimentacaoInvalidaException("Data de movimentação inválida");
        }

        ContaCorrente contaCorrente = contaCorrenteServiceHelper
            .consultarContaCorrentePorId(movimentacaoDto.getContaCorrenteId())
            .orElseThrow(() -> new ContaInexistenteException("Conta não encontrada."));

        BigDecimal saldo = contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, movimentacaoDto.getData());
        BigDecimal quantidadeAtivo = consultarQuantidadeAtivo(movimentacaoDto);
        if (movimentacaoDto.getTipo() == Movimentacao.TipoMovimentacao.VENDA) {
            if (movimentacaoDto.getValor().compareTo(saldo) > 0)
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar movimentação.");
            if (quantidadeAtivo.subtract(movimentacaoDto.getQuantidade()).compareTo(BigDecimal.ZERO) < 0) {
                throw new QuantidadeAtivoInsuficienteException("Quantidade insuficiente (%.2f/%.2f) do ativo para realizar movimentação."
                    .formatted(movimentacaoDto.getQuantidade(), quantidadeAtivo));
            }
        }

        lancamentoService.incluirLancamento(
            new LancamentoDto(
                movimentacaoDto.getContaCorrenteId(),
                switch (movimentacaoDto.getTipo()) {
                    case COMPRA -> Lancamento.TipoLancamento.SAIDA;
                    case VENDA -> Lancamento.TipoLancamento.ENTRADA;
                },
                movimentacaoDto.getValor(),
                "Movimentação de %s (%.2f) do ativo financeiro %d.".formatted(movimentacaoDto.getTipo(), movimentacaoDto.getValor(), movimentacaoDto.getAtivoFinanceiroId()),
                movimentacaoDto.getData()));

        Movimentacao movimentacao = criarMovimentacao(movimentacaoDto, contaCorrente, ativoFinanceiro);

        return movimentacaoRepository.save(movimentacao);
    }

    private static @NotNull Movimentacao criarMovimentacao(@NotNull MovimentacaoDto movimentacaoDto, ContaCorrente contaCorrente, AtivoFinanceiro ativoFinanceiro) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setContaCorrente(contaCorrente);
        movimentacao.setAtivoFinanceiro(ativoFinanceiro);
        movimentacao.setTipo(movimentacaoDto.getTipo());
        movimentacao.setQuantidade(movimentacaoDto.getQuantidade());
        movimentacao.setData(movimentacaoDto.getData());
        movimentacao.setValor(movimentacaoDto.getValor());
        return movimentacao;
    }

    public List<Movimentacao> consultarMovimentacoesPorConta(Long contaCorrenteId) {
        ContaCorrente contaCorrente = contaCorrenteServiceHelper
            .consultarContaCorrentePorId(contaCorrenteId)
            .orElseThrow(() -> new ContaInexistenteException("Conta corrente não existe."));
        return movimentacaoRepository.findByContaCorrente(contaCorrente);
    }

    public List<Movimentacao> consultarMovimentacoesPorContaAteData(Long contaCorrenteId, LocalDateTime dataPosicao) {
        return consultarMovimentacoesPorConta(contaCorrenteId)
            .stream()
            .filter(movimentacao -> movimentacao.getData().isBefore(dataPosicao)
                || movimentacao.getData().isEqual(dataPosicao))
            .toList();
    }

    public List<Movimentacao> consultarMovimentacoesPorContaNoPeriodo(Long contaCorrenteId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultarMovimentacoesPorContaAteData(contaCorrenteId, dataFim)
            .stream()
            .filter(movimentacao -> movimentacao.getData().isAfter(dataInicio)
                || movimentacao.getData().isEqual(dataInicio))
            .toList();
    }

    public BigDecimal consultarQuantidadeAtivo(@NotNull MovimentacaoDto movimentacaoDto) {
        var movimentacoesPorTipo = consultarMovimentacoesPorContaAteData(movimentacaoDto.getContaCorrenteId(), movimentacaoDto.getData())
            .stream()
            .collect( // @formatter:off
                filtering(m -> Objects.equals(m.getAtivoFinanceiro().getId(),movimentacaoDto.getAtivoFinanceiroId()),
                groupingBy(
                    Movimentacao::getTipo,
                mapping(
                    Movimentacao::getQuantidade,
                reducing(
                    BigDecimal.ZERO,
                    BigDecimal::add
                )))));//@formatter:on
        return movimentacoesPorTipo.getOrDefault(Movimentacao.TipoMovimentacao.VENDA, BigDecimal.ZERO)
            .subtract(movimentacoesPorTipo.getOrDefault(Movimentacao.TipoMovimentacao.COMPRA, BigDecimal.ZERO));
    }

    private List<Movimentacao> consultarMovimentacoesPorTipo(Long contaCorrenteId, Long ativoFinanceiroId, LocalDateTime dataPosicao, Movimentacao.TipoMovimentacao tipo) {
        return consultarMovimentacoesPorContaAteData(contaCorrenteId, dataPosicao)
            .stream()
            .filter(movimentacao -> movimentacao.getAtivoFinanceiro().getId().equals(ativoFinanceiroId)
                && movimentacao.getTipo() == tipo)
            .toList();
    }

    public List<Movimentacao> consultarMovimentacoesCompra(Long contaCorrenteId, Long ativoFinanceiroId, LocalDateTime dataPosicao) {
        return consultarMovimentacoesPorTipo(contaCorrenteId, ativoFinanceiroId, dataPosicao, Movimentacao.TipoMovimentacao.COMPRA);
    }

    public List<Movimentacao> consultarMovimentacoesVenda(Long contaCorrenteId, Long ativoFinanceiroId, LocalDateTime dataPosicao) {
        return consultarMovimentacoesPorTipo(contaCorrenteId, ativoFinanceiroId, dataPosicao, Movimentacao.TipoMovimentacao.VENDA);
    }

    private boolean ehFinalDeSemana(@NotNull LocalDateTime data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        return diaDaSemana == DayOfWeek.SATURDAY || diaDaSemana == DayOfWeek.SUNDAY;
    }

}
