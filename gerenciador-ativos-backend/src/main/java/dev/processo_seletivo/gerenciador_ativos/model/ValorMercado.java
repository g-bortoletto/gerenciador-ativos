package dev.processo_seletivo.gerenciador_ativos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
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

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

}
