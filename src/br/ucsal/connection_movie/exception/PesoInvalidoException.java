package br.ucsal.connection_movie.exception;

public class PesoInvalidoException extends RuntimeException {
    public PesoInvalidoException(double peso) {
        super("Peso de gênero inválido: " + peso + ". Deve estar entre 0.0 e 1.0.");
    }
}
