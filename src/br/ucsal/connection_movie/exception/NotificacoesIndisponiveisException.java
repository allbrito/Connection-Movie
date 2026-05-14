package br.ucsal.connection_movie.exception;

public class NotificacoesIndisponiveisException extends RuntimeException {
    public NotificacoesIndisponiveisException() {
        super("Notificações indisponíveis no momento");
    }
}
