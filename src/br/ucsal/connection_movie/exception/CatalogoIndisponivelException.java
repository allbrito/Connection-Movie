package br.ucsal.connection_movie.exception;

public class CatalogoIndisponivelException extends RuntimeException {
    public CatalogoIndisponivelException(String message) {
        super("Catalogo Indisponível");
    }
}
