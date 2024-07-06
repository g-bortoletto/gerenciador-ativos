package dev.processo_seletivo.gerenciador_ativos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JoinColumn(
        name = "conta_corrente_id",
        referencedColumnName = "id",
        nullable = false)
    private ContaCorrente contaCorrente;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @JoinColumn(
        name = "ativo_financeiro_id",
        referencedColumnName = "id",
        nullable = false)
    private AtivoFinanceiro ativoFinanceiro;

    @Column(nullable = false, scale = 2)
    private BigDecimal quantidade;

    @Column(nullable = false, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

    public enum TipoMovimentacao {
        VENDA, COMPRA
    }

    public void setQuantidade(@NotNull BigDecimal quantidade) {
        this.quantidade = quantidade.setScale(2, RoundingMode.FLOOR);
    }

    public void setValor(@NotNull BigDecimal valor) {
        this.valor = quantidade.setScale(2, RoundingMode.FLOOR);
    }

}
