package dev.processo_seletivo.gerenciador_ativos.exception;

import java.util.NoSuchElementException;

public class ContaInexistenteException extends NoSuchElementException {

    public ContaInexistenteException(String s) {
        super(s);
    }
}
