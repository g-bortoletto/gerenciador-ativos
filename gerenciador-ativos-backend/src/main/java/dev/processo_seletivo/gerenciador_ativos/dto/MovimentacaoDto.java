package dev.processo_seletivo.gerenciador_ativos.dto;

import dev.processo_seletivo.gerenciador_ativos.model.Movimentacao;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
@ToString
public class MovimentacaoDto {

    private Long contaCorrenteId;
    private Movimentacao.TipoMovimentacao tipo;
    private Long ativoFinanceiroId;
    private BigDecimal quantidade;
    private BigDecimal valor;
    private LocalDateTime data;

}
