package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.model.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.model.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.repository.LancamentoRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
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

    public Lancamento incluirLancamento(Long contaCorrenteId, @NotNull LancamentoDto lancamentoDto) {
        ContaCorrente contaCorrente = contaCorrenteServiceHelper
            .consultarContaCorrentePorId(contaCorrenteId)
            .orElseThrow(() -> new NoSuchElementException("Conta corrente %d não existe.".formatted(contaCorrenteId)));

        Lancamento lancamento = new Lancamento();
        lancamento.setContaCorrente(contaCorrente);
        lancamento.setTipo(lancamentoDto.getTipo());
        lancamento.setDescricao(lancamentoDto.getDescricao());
        lancamento.setValor(lancamentoDto.getValor());
        lancamento.setData(lancamentoDto.getData());

        BigDecimal saldo = contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, lancamento.getData());
        if (lancamento.getTipo() == Lancamento.TipoLancamento.SAIDA && saldo.compareTo(lancamento.getValor()) < 0) {
            throw new RuntimeException("Saldo insuficiente na conta %d (%.2f).".formatted(contaCorrente.getId(), saldo));
        }

        return lancamentoRepository.save(lancamento);
    }

    public List<Lancamento> consultarLancamentosPorContaCorrente(ContaCorrente contaCorrente) {
        return lancamentoRepository.findByContaCorrente(contaCorrente);
    }

    public List<Lancamento> consultarLancamentosPorContaCorrenteAteData(ContaCorrente contaCorrente, LocalDateTime data) {
        return contaCorrenteServiceHelper
            .consultarLancamentosPorContaCorrente(contaCorrente).stream()
            .filter(lancamento -> lancamento.getData().isBefore(data) || lancamento.getData().isEqual(data))
            .toList();
    }

}
