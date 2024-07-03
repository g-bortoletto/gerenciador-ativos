package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.model.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.model.ValorMercado;
import dev.processo_seletivo.gerenciador_ativos.dto.ValorMercadoDto;
import dev.processo_seletivo.gerenciador_ativos.repository.ValorMercadoRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ValorMercadoService {

    @Autowired
    private ValorMercadoRepository valorMercadoRepository;

    @Transactional
    public ValorMercado incluirValorMercadoEmAtivo(@NotNull AtivoFinanceiro ativoFinanceiro, @NotNull ValorMercadoDto valorMercadoDto) {
        validarDataPosicao(valorMercadoDto.getData(), ativoFinanceiro.getDataEmissao(), ativoFinanceiro.getDataVencimento());

        ValorMercado valorMercado = new ValorMercado();
        valorMercado.setAtivoFinanceiro(ativoFinanceiro);
        valorMercado.setData(valorMercadoDto.getData());
        valorMercado.setValor(valorMercadoDto.getValor());

        return valorMercadoRepository.save(valorMercado);
    }

    public List<ValorMercado> consultarValoresMercado() {
        return valorMercadoRepository.findAll();
    }

    public Optional<ValorMercado> consultarValorMercadoPorId(Long valorMercadoId) {
        return valorMercadoRepository.findById(valorMercadoId);
    }

    public List<ValorMercado> consultarValoresMercadoDeAtivo(AtivoFinanceiro ativoFinanceiro) {
        return valorMercadoRepository.findByAtivoFinanceiro(ativoFinanceiro);
    }

    public List<ValorMercado> consultarValoresMercadoDeAtivoDesde(AtivoFinanceiro ativoFinanceiro, LocalDateTime dataPosicao) {
        return consultarValoresMercadoDeAtivo(ativoFinanceiro).stream()
            .filter(valorMercado -> valorMercado.getData().isBefore(dataPosicao) || valorMercado.getData().isEqual(dataPosicao))
            .toList();
    }

    public List<ValorMercado> consultarValoresMercadoDeAtivoNoPeriodo(AtivoFinanceiro ativoFinanceiro, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return consultarValoresMercadoDeAtivoDesde(ativoFinanceiro, dataFim).stream()
            .filter(valorMercado -> valorMercado.getData().isAfter(dataInicio) || valorMercado.getData().isEqual(dataInicio))
            .toList();
    }

    private void validarDataPosicao(@NotNull LocalDateTime dataPosicao, LocalDateTime dataEmissao, LocalDateTime dataVencimento) {
        if (dataPosicao.isBefore(dataEmissao) || dataPosicao.isAfter(dataVencimento)) {
            throw new RuntimeException("A data do valor de mercado deve estar entre a data de emissão e a data de vencimento do ativo financeiro.");
        }
    }

}
