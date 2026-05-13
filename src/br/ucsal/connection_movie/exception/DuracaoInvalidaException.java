package br.ucsal.connection_movie.exception;

public class DuracaoInvalidaException extends RuntimeException {
    public DuracaoInvalidaException(int minima, int maxima) {
        super("Duração inválida: mínima (" + minima + ") não pode ser maior que máxima (" + maxima + ") e/ou não pode ser menor ou igual a 0.");
    }
}
