package dev.processo_seletivo.gerenciador_ativos.exception;

public class SaldoNegativoException extends IllegalStateException {
    public SaldoNegativoException(String s) {
        super(s);
    }
}
