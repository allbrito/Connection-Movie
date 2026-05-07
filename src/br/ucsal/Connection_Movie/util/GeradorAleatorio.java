package br.ucsal.Connection_Movie.util;

import br.ucsal.Connection_Movie.model.Recomendacao;

import java.util.List;

public interface GeradorAleatorio {

    public int desempatar(int min, int max);
    public List<Recomendacao> gerarAleatorio(int qntFilmes);
}
