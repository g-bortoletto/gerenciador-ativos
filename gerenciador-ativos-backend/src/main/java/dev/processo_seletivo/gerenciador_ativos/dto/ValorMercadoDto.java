package dev.processo_seletivo.gerenciador_ativos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValorMercadoDto {

    public Long ativoFinanceiroId;
    public BigDecimal valor;
    public LocalDateTime data;

}
