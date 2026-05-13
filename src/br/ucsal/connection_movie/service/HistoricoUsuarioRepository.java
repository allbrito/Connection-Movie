package br.ucsal.connection_movie.service;

import br.ucsal.connection_movie.model.PerfilCinefilo;
import br.ucsal.connection_movie.model.Recomendacao;
import br.ucsal.connection_movie.model.Usuario;

import java.util.List;

public interface HistoricoUsuarioRepository {

    public PerfilCinefilo getPerfil(Usuario usuario);

    public void registrarRecomendacao(Usuario usuario, List<Recomendacao> recomendacao);
}
