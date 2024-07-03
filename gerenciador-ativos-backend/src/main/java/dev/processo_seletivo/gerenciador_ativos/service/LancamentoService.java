package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.entity.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.exception.ContaInexistenteException;
import dev.processo_seletivo.gerenciador_ativos.exception.SaldoInsuficienteException;
import dev.processo_seletivo.gerenciador_ativos.repository.LancamentoRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

    public Lancamento incluirLancamento(@NotNull LancamentoDto lancamentoDto) {
        ContaCorrente contaCorrente = contaCorrenteServiceHelper
            .consultarContaCorrentePorId(lancamentoDto.getContaCorrenteId())
            .orElseThrow(() -> new ContaInexistenteException("Conta corrente %d não existe.".formatted(lancamentoDto.getContaCorrenteId())));

        Lancamento lancamento = new Lancamento();
        lancamento.setContaCorrente(contaCorrente);
        lancamento.setTipo(lancamentoDto.getTipo());
        lancamento.setDescricao(lancamentoDto.getDescricao());
        lancamento.setValor(lancamentoDto.getValor());
        lancamento.setData(lancamentoDto.getData());

        BigDecimal saldo = contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, lancamento.getData());
        if (lancamento.getTipo() == Lancamento.TipoLancamento.SAIDA && saldo.compareTo(lancamento.getValor()) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta %d (%.2f).".formatted(contaCorrente.getId(), saldo));
        }

        return lancamentoRepository.save(lancamento);
    }

    public List<Lancamento> consultarLancamentosPorContaCorrente(ContaCorrente contaCorrente) {
        return lancamentoRepository.findByContaCorrente(contaCorrente);
    }

    public List<Lancamento> consultarLancamentosPorContaCorrenteAteData(ContaCorrente contaCorrente, LocalDateTime data) {
        return consultarLancamentosPorContaCorrente(contaCorrente).stream()
            .filter(lancamento -> lancamento.getData().isBefore(data) || lancamento.getData().isEqual(data))
            .toList();
    }

    public List<Lancamento> consultarLancamentosPorContaCorrenteEmPeriodo(ContaCorrente contaCorrente, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultarLancamentosPorContaCorrenteAteData(contaCorrente, dataFim).stream()
            .filter(lancamento -> lancamento.getData().isAfter(dataInicio) || lancamento.getData().isEqual(dataInicio))
            .toList();
    }

}
