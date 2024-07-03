package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.model.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.repository.ContaCorrenteRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository listaContasCorrente;

    @Autowired
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

    @Autowired
    private MovimentacaoService movimentacaoService;

    public ContaCorrente incluirContaCorrente() {
        return listaContasCorrente.save(new ContaCorrente());
    }

    public List<ContaCorrente> consultarContasCorrente() {
        return listaContasCorrente.findAll();
    }

    public Optional<ContaCorrente> consultarContaCorrentePorId(Long contaCorrenteId) {
        return contaCorrenteServiceHelper.consultarContaCorrentePorId(contaCorrenteId);
    }

    public boolean removerContaCorrentePorId(Long contaCorrenteId) {
        Optional<ContaCorrente> contaCorrenteOptional = contaCorrenteServiceHelper
            .consultarContaCorrentePorId(contaCorrenteId);
        if (contaCorrenteOptional.isEmpty()) {
            return false;
        }
        listaContasCorrente.delete(contaCorrenteOptional.get());
        return true;
    }

    public BigDecimal consultarSaldoContaCorrente(Long contaCorrenteId, LocalDateTime dataPosicao) {
        ContaCorrente contaCorrente = contaCorrenteServiceHelper
            .consultarContaCorrentePorId(contaCorrenteId)
            .orElseThrow(() -> new NoSuchElementException("Conta corrente não encontrada"));

        return contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, dataPosicao);
    }

}
