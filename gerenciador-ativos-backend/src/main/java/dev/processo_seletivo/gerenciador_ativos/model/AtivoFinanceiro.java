package dev.processo_seletivo.gerenciador_ativos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AtivoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated
    @Column(nullable = false)
    private TipoAtivoFinanceiro tipo;

    @Column(nullable = false)
    private LocalDateTime dataEmissao;

    @Column(nullable = false)
    private LocalDateTime dataVencimento;

    public enum TipoAtivoFinanceiro {
        RV, RF, FUNDO
    }

}
