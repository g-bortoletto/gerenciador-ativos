package dev.processo_seletivo.gerenciador_ativos.dto;

import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtivoFinanceiroDto {

    private String nome;
    private AtivoFinanceiro.TipoAtivoFinanceiro tipo;
    private LocalDateTime dataEmissao;
    private LocalDateTime dataVencimento;

}
