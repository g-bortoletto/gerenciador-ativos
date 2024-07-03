package dev.processo_seletivo.gerenciador_ativos.dto;

import dev.processo_seletivo.gerenciador_ativos.model.Lancamento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LancamentoDto {

    private Lancamento.TipoLancamento tipo;
    private BigDecimal valor;
    private String descricao;
    private LocalDateTime data;

}
