package br.ucsal.connection_movie.exception;

public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException(int nota) {
        super("Nota Inválida: ("+nota+")");
    }
}
