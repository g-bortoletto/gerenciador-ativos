package dev.processo_seletivo.gerenciador_ativos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(
        name = "conta_corrente_id",
        referencedColumnName = "id",
        nullable = false)
    private ContaCorrente contaCorrente;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(
        name = "ativo_financeiro_id",
        referencedColumnName = "id",
        nullable = false)
    private AtivoFinanceiro ativoFinanceiro;

    @Column(nullable = false)
    private BigDecimal quantidade;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

    public enum TipoMovimentacao {
        VENDA, COMPRA
    }

}
