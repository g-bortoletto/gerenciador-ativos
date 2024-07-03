package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.model.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.dto.AtivoFinanceiroDto;
import dev.processo_seletivo.gerenciador_ativos.repository.AtivoFinanceiroRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AtivoFinanceiroService {

    @Autowired
    private AtivoFinanceiroRepository ativoFinanceiroRepository;

    @Transactional
    public AtivoFinanceiro incluirAtivoFinanceiro(@NotNull AtivoFinanceiroDto ativoFinanceiro) throws RuntimeException {
        validarPeriodoDatas(ativoFinanceiro.getDataEmissao(), ativoFinanceiro.getDataVencimento());

        AtivoFinanceiro novoAtivoFinanceiro = new AtivoFinanceiro();
        novoAtivoFinanceiro.setTipo(ativoFinanceiro.getTipo());
        novoAtivoFinanceiro.setNome(ativoFinanceiro.getNome());
        novoAtivoFinanceiro.setDataEmissao(ativoFinanceiro.getDataEmissao());
        novoAtivoFinanceiro.setDataVencimento(ativoFinanceiro.getDataVencimento());

        return ativoFinanceiroRepository.save(novoAtivoFinanceiro);
    }

    private void validarPeriodoDatas(@NotNull LocalDateTime dataEmissao, @NotNull LocalDateTime dataVencimento) throws RuntimeException {
        if (dataVencimento.isBefore(dataEmissao) || dataVencimento.isEqual(dataEmissao)) {
            throw new RuntimeException("A data de vencimento do ativo deve ser posterior à data de emissão.");
        }
    }

    public List<AtivoFinanceiro> consultarAtivosFinanceiros() {
        return ativoFinanceiroRepository.findAll();
    }

    public AtivoFinanceiro consultarAtivoFinanceiroPorId(Long id) {
        return ativoFinanceiroRepository
            .findById(id)
            .orElseThrow(() -> new NoSuchElementException("Ativo financeiro %d não encontrado.".formatted(id)));
    }

    @Transactional
    public AtivoFinanceiro editarAtivoFinanceiro(Long id, @NotNull AtivoFinanceiroDto detalhesAtivoFinanceiro) {
        AtivoFinanceiro ativoFinanceiro = ativoFinanceiroRepository
            .findById(id)
            .orElseThrow();

        validarPeriodoDatas(ativoFinanceiro.getDataEmissao(), ativoFinanceiro.getDataVencimento());

        ativoFinanceiro.setTipo(detalhesAtivoFinanceiro.getTipo());
        ativoFinanceiro.setNome(detalhesAtivoFinanceiro.getNome());
        ativoFinanceiro.setDataEmissao(detalhesAtivoFinanceiro.getDataEmissao());
        ativoFinanceiro.setDataVencimento(detalhesAtivoFinanceiro.getDataVencimento());

        return ativoFinanceiroRepository.save(ativoFinanceiro);
    }

}
