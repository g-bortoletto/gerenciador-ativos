package dev.processo_seletivo.gerenciador_ativos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Data
public class ValorMercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(
        name = "ativo_financeiro_id",
        referencedColumnName = "id",
        nullable = false)
    private AtivoFinanceiro ativoFinanceiro;

    @Column(nullable = false, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

    public void setValor(@NotNull BigDecimal valor) {
        this.valor = valor.setScale(2, RoundingMode.FLOOR);
    }

}
