package dev.processo_seletivo.gerenciador_ativos.model;

import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(builderMethodName = "of")
public class Posicao {

    private final AtivoFinanceiro ativoFinanceiro;
    private final BigDecimal quantidadeTotal;
    private final BigDecimal valorMercadoTotal;
    private final BigDecimal rendimento;
    private final BigDecimal lucro;

}
