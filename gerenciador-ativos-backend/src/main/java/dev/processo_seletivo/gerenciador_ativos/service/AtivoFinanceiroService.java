package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.dto.AtivoFinanceiroDto;
import dev.processo_seletivo.gerenciador_ativos.entity.ValorMercado;
import dev.processo_seletivo.gerenciador_ativos.exception.DataMovimentacaoInvalidaException;
import dev.processo_seletivo.gerenciador_ativos.repository.AtivoFinanceiroRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AtivoFinanceiroService {

    @Autowired
    private AtivoFinanceiroRepository ativoFinanceiroRepository;

    @Autowired
    private ValorMercadoService valorMercadoService;

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
            throw new DataMovimentacaoInvalidaException("A data de vencimento do ativo deve ser posterior à data de emissão.");
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

    @Transactional
    public AtivoFinanceiro removerAtivoFinanceiro(Long ativoFinanceiroId) {
        AtivoFinanceiro ativoFinanceiro = consultarAtivoFinanceiroPorId(ativoFinanceiroId);
        ativoFinanceiroRepository.delete(ativoFinanceiro);
        return ativoFinanceiro;
    }

    @Transactional
    public AtivoFinanceiro incluirValorMercado(Long ativoFinanceiroId, LocalDateTime data, BigDecimal valor) {

        AtivoFinanceiro ativoFinanceiro = ativoFinanceiroRepository
            .findById(ativoFinanceiroId)
            .orElseThrow();

        valorMercadoService.validarDataPosicao(data, ativoFinanceiro.getDataEmissao(), ativoFinanceiro.getDataVencimento());

        ValorMercado valorMercado = new ValorMercado();
        valorMercado.setAtivoFinanceiro(ativoFinanceiro);
        valorMercado.setData(data);
        valorMercado.setValor(valor);

        return ativoFinanceiroRepository.save(ativoFinanceiro);

    }

    public ValorMercado consultarValorMercado(AtivoFinanceiro ativoFinanceiro) {
        return valorMercadoService.consultarValorMercadoMaisRecenteDeAtivo(ativoFinanceiro);
    }

    public List<ValorMercado> consultarValoresMercado(Long ativoFinanceiroId) {
        AtivoFinanceiro ativoFinanceiro = ativoFinanceiroRepository.findById(ativoFinanceiroId)
            .orElseThrow(() -> new NoSuchElementException("Ativo financeiro inexistente."));
        return valorMercadoService.consultarValoresMercadoDeAtivo(ativoFinanceiro);
    }

    @Transactional
    public AtivoFinanceiro removerValorMercado(Long valorMercadoId) {
        ValorMercado valorMercado = valorMercadoService.removerValorMercado(valorMercadoId);
        return valorMercado.getAtivoFinanceiro();
    }

}
