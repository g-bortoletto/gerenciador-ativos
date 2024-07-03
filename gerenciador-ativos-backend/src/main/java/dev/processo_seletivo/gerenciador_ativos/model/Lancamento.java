package dev.processo_seletivo.gerenciador_ativos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(
        name = "conta_corrente_id",
        nullable = false)
    private ContaCorrente contaCorrente;

    @Enumerated
    @Column(nullable = false)
    private TipoLancamento tipo;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime data;

    public enum TipoLancamento {
        ENTRADA, SAIDA
    }

}
