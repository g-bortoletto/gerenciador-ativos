package dev.processo_seletivo.gerenciador_ativos.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
