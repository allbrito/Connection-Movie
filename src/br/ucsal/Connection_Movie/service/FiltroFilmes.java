package br.ucsal.Connection_Movie.service;

import br.ucsal.Connection_Movie.model.Filme;
import br.ucsal.Connection_Movie.model.PerfilCinefilo;

import java.util.ArrayList;
import java.util.List;

public class FiltroFilmes {

    public List<Filme> filtrar(List<Filme> filmes, PerfilCinefilo perfil) {

        List<Filme> filmesAprovados = new ArrayList<>();

        for (Filme filme : filmes) {
            if (perfil.jaAssistiu(filme))
                continue;
            if (filme.getClassificacaoEtaria().getId() > perfil.getClassificacaoEtaria().getId())
                continue;
            if (!perfil.getIdiomasAceitos().contains(filme.getIdioma()))
                continue;

            filmesAprovados.add(filme);
        }

        return filmesAprovados;
    }

}
