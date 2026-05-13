package br.ucsal.connection_movie.util;

import br.ucsal.connection_movie.model.Recomendacao;

import java.util.List;

public interface GeradorAleatorio {

    public int desempatar(int min, int max);
    public List<Recomendacao> gerarAleatorio(int qntFilmes);
}
