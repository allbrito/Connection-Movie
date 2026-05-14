package br.ucsal.connection_movie.service;

import br.ucsal.connection_movie.model.Filme;
import br.ucsal.connection_movie.model.PerfilCinefilo;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroFilmes {

    public List<Filme> filtrar(List<Filme> filmes, PerfilCinefilo perfil) {

        return filmes.stream()
                .filter(filme -> !perfil.jaAssistiu(filme))
                .filter(filme -> filme.getClassificacaoEtaria().getId() <= perfil.getClassificacaoEtaria().getId())
                .filter(filme -> perfil.getIdiomasAceitos().contains(filme.getIdioma()))
                .filter(filme -> filme.getGeneros().stream().filter(genero -> perfil.getPesosGenero().containsKey(genero)).noneMatch(genero -> perfil.getPesosGenero().get(genero) == 0))
                .collect(Collectors.toList());

    }
}
