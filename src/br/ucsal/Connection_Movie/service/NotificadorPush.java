package br.ucsal.Connection_Movie.service;

import br.ucsal.Connection_Movie.model.Recomendacao;
import br.ucsal.Connection_Movie.model.Usuario;

import java.util.List;

public interface NotificadorPush {


    public void enviar(Usuario usuario, List<Recomendacao> recomendacoes);
}
