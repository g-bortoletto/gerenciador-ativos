package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.model.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.model.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.model.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.model.Movimentacao;
import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.dto.MovimentacaoDto;
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

@Service
public class MovimentacaoService {

    @Autowired
    private AtivoFinanceiroService ativoFinanceiroService;

    @Autowired
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    LancamentoService lancamentoService;

    @Transactional
    @SneakyThrows
    public Movimentacao incluirMovimentacao(@NotNull MovimentacaoDto movimentacaoDto) {
        AtivoFinanceiro ativoFinanceiro = ativoFinanceiroService.consultarAtivoFinanceiroPorId(movimentacaoDto.getAtivoFinanceiroId());
        if (movimentacaoDto.getData().isBefore(ativoFinanceiro.getDataEmissao())
            || movimentacaoDto.getData().isAfter(ativoFinanceiro.getDataVencimento())
            || ehFinalDeSemana(movimentacaoDto.getData())) {
            throw new IllegalArgumentException("Data de movimentação inválida");
        }

        ContaCorrente contaCorrente = contaCorrenteServiceHelper.consultarContaCorrentePorId(movimentacaoDto.getContaCorrenteId())
            .orElseThrow(() -> new NoSuchElementException("Conta não encontrada."));
        BigDecimal saldo = contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, movimentacaoDto.getData());
        if (movimentacaoDto.getTipo() == Movimentacao.TipoMovimentacao.VENDA
            && movimentacaoDto.getQuantidade().multiply(movimentacaoDto.getValor()).compareTo(saldo) > 0) {
            throw new RuntimeException("Saldo insuficiente para realizar movimentação.");
        }

        Lancamento lancamento = lancamentoService.incluirLancamento(
            movimentacaoDto.getContaCorrenteId(),
            new LancamentoDto(
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
        ContaCorrente contaCorrente = contaCorrenteServiceHelper.consultarContaCorrentePorId(contaCorrenteId)
            .orElseThrow(() -> new NoSuchElementException("Conta corrente não existe."));
        return movimentacaoRepository.findByContaCorrente(contaCorrente);
    }

    private boolean ehFinalDeSemana(@NotNull LocalDateTime data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        return diaDaSemana == DayOfWeek.SATURDAY || diaDaSemana == DayOfWeek.SUNDAY;
    }

}
