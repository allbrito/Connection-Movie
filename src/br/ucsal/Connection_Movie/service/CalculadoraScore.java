package br.ucsal.Connection_Movie.service;

import br.ucsal.Connection_Movie.model.Filme;
import br.ucsal.Connection_Movie.model.PerfilCinefilo;
import br.ucsal.Connection_Movie.model.enums.Genero;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculadoraScore {


    double PESO_GENERO = 0.5;
    double PESO_DURACAO = 0.2;
    double PESO_POPULARIDADE = 0.15;
    double PESO_AFINIDADE = 0.15;

    public double calcular(Filme filme, PerfilCinefilo perfil) {

        double score = 0;

        score = scoreGenero(filme, perfil)*PESO_GENERO;
        score = scoreDuracao(filme, perfil)*PESO_DURACAO;
        score = scorePopularidade(filme, perfil)*PESO_POPULARIDADE;
        score = scoreAfinidade(filme, perfil)*PESO_AFINIDADE;

        return score;
    }

    private double scoreGenero(Filme filme, PerfilCinefilo perfil) {

       List<Genero> generosDoFilme = filme.getGeneros();

       if (generosDoFilme.isEmpty())
           return 0.0;

       double somaDosPesosDosGeneros = generosDoFilme.stream().mapToDouble(g -> perfil.getPesoGenero(g)).sum();

       double mediaDosPesosDosGeneros = somaDosPesosDosGeneros/generosDoFilme.size();

       return mediaDosPesosDosGeneros;
    }

    private double scoreDuracao(Filme filme, PerfilCinefilo perfil) {

        if (filme.getDuracao()>perfil.getDuracaoMinimaPreferida() && filme.getDuracao()>perfil.getDuracaoMaximaPreferida())
            return 100;
        if (filme.getDuracao()>perfil.getDuracaoMinimaPreferida()-perfil.getDuracaoMinimaPreferida()*0.2 || filme.getDuracao()>perfil.getDuracaoMaximaPreferida()+perfil.getDuracaoMaximaPreferida()*0.2)
            return 50;
        return 0;
    }

    private double scorePopularidade(Filme filme, PerfilCinefilo perfil){
        return filme.getPopularidade();

    }

    private double scoreAfinidade(Filme filme, PerfilCinefilo perfil) {

        if (filme.getGeneros() == null)
            return 0;
        double mediaDasAvaliacoesDosGeneros = 0;

        for (Genero genero : filme.getGeneros()) {

            List<Filme> filmesAssisitidos = perfil.getFilmesAssistidos();
            filmesAssisitidos = filmesAssisitidos.stream().filter(f -> f.getGeneros().contains(genero)).toList();


            Map<Filme, Integer> notasDosFilmes = perfil.getNotas();

            List<Filme> filmesAvaliados = new ArrayList<>();

            for (Filme filmeAssistido : filmesAssisitidos) {
                if (notasDosFilmes.containsKey(filmeAssistido)){
                    filmesAvaliados.add(filmeAssistido);
                }
            }

            double somaDasAvaliacoes = 0;

            for (Filme filmeAvaliado : filmesAvaliados) {

                double notaFormatada = notasDosFilmes.get(filmeAvaliado)/5; //Recebe a nota do filme de 1-5 e transforma em 0.0 - 1.0

                somaDasAvaliacoes += notaFormatada;
            }

            double mediaDasAvaliacoesParaFilmesDoGenero = somaDasAvaliacoes/filmesAvaliados.size();

            mediaDasAvaliacoesDosGeneros = mediaDasAvaliacoesParaFilmesDoGenero * 100;
        }



        return mediaDasAvaliacoesDosGeneros;
    }



}
