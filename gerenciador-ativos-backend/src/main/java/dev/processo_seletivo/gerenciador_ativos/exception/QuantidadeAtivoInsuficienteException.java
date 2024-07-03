package dev.processo_seletivo.gerenciador_ativos.exception;

public class QuantidadeAtivoInsuficienteException extends RuntimeException {
    public QuantidadeAtivoInsuficienteException(String message) {
        super(message);
    }
}
