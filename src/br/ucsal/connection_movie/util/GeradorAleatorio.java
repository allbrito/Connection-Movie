package br.ucsal.connection_movie.util;

import br.ucsal.connection_movie.model.Recomendacao;

import java.util.List;

public interface GeradorAleatorio {

    int desempatar(int min, int max);
    List<Recomendacao> gerarAleatorio(int quantidadeFilmes);
}
