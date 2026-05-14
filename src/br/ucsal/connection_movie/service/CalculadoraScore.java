package br.ucsal.connection_movie.service;

import br.ucsal.connection_movie.model.Filme;
import br.ucsal.connection_movie.model.PerfilCinefilo;
import br.ucsal.connection_movie.model.enums.Genero;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculadoraScore {


    public static final double PESO_GENERO = 0.5;
    public static final double PESO_DURACAO = 0.2;
    public static final double PESO_POPULARIDADE = 0.15;
    public static final double PESO_AFINIDADE = 0.15;

    public double calcular(Filme filme, PerfilCinefilo perfil) {

        double score = 0;

        score += scoreGenero(filme, perfil)*PESO_GENERO;
        score += scoreDuracao(filme, perfil)*PESO_DURACAO;
        score += scorePopularidade(filme, perfil)*PESO_POPULARIDADE;
        score += scoreAfinidade(filme, perfil)*PESO_AFINIDADE;
        return score;
    }

    private double scoreGenero(Filme filme, PerfilCinefilo perfil) {

       List<Genero> generosDoFilme = filme.getGeneros();


       if (generosDoFilme.isEmpty())
           return 0.0;

       List<Genero> generosAvaliados = generosDoFilme.stream().filter(g -> perfil.getPesosGenero().containsKey(g)).toList();

       if (generosAvaliados.isEmpty())
           return 50;

       double somaDosPesosDosGeneros = generosAvaliados.stream().mapToDouble(g -> perfil.getPesosGenero().get(g)).sum();

       double mediaDosPesosDosGeneros = somaDosPesosDosGeneros/generosAvaliados.size();

       return mediaDosPesosDosGeneros*100;
    }

    private double scoreDuracao(Filme filme, PerfilCinefilo perfil) {
        int duracao = filme.getDuracao();
        int min = perfil.getDuracaoMinimaPreferida();
        int max = perfil.getDuracaoMaximaPreferida();

        if (duracao >= min && duracao <= max) return 100;

        // Caso 2: Duração fora da faixa - calcular penalidade
        int minutosForaDaFaixa;
        if (duracao < min) {
            minutosForaDaFaixa = min - duracao;
        } else {
            minutosForaDaFaixa = duracao - max;
        }

        double penalidade = minutosForaDaFaixa * 2.0;
        double score = 100.0 - penalidade;

        return Math.max(0.0, score);
    }

    private double scorePopularidade(Filme filme, PerfilCinefilo perfil){

        return filme.getPopularidade();

    }

    private double scoreAfinidade(Filme filme, PerfilCinefilo perfil) {

        if (perfil.getFilmesAssistidos().isEmpty())
            return 0;

        double somaDasMedias = 0;
        int qntGenerosAvaliados = 0;


        for (Genero genero : filme.getGeneros()) {

            List<Integer> notasDoGenero = perfil.getFilmesAssistidos().stream().filter(f -> f.getGeneros().contains(genero)).filter(f -> perfil.getNotas().containsKey(f)).map(f -> perfil.getNotas().get(f)).toList();


            if (notasDoGenero.isEmpty())
                continue;


           double mediaDoGenero = notasDoGenero.stream().mapToDouble(Integer::doubleValue).sum()/notasDoGenero.size();

            somaDasMedias += mediaDoGenero/5;

            qntGenerosAvaliados++;
        }

        if (qntGenerosAvaliados==0)
            return 0;

        return (somaDasMedias/qntGenerosAvaliados)*100;
    }



}
