package br.ucsal.Connection_Movie.service;

import br.ucsal.Connection_Movie.model.PerfilCinefilo;
import br.ucsal.Connection_Movie.model.Recomendacao;
import br.ucsal.Connection_Movie.model.Usuario;

import java.util.List;

public interface HistoricoUsuarioRepository {

    public PerfilCinefilo getPerfil(Usuario usuario);

    public void registrarRecomendacao(Usuario usuario, List<Recomendacao> recomendacao);
}
