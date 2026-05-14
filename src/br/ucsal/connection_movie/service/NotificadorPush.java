package br.ucsal.connection_movie.service;

import br.ucsal.connection_movie.model.Recomendacao;
import br.ucsal.connection_movie.model.Usuario;

import java.util.List;

public interface NotificadorPush {


    void enviar(Usuario usuario, List<Recomendacao> recomendacoes);
}
