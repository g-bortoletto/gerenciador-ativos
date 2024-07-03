package dev.processo_seletivo.gerenciador_ativos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RespostaErro {

    private int status;
    private String mensagem;
    private String detalhes;

}
