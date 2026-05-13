package br.ucsal.connection_movie.model;

public class Recomendacao {

    private Filme filme;
    private double score;
    private String justificativa;

    public Recomendacao(Filme filme) {
        this.filme = filme;
    }

    public Recomendacao(Filme filme, double score,String justificativa) {
        this.filme = filme;
        this.score = score;
        this.justificativa = justificativa;
    }

    public double getScore() {
        return score;
    }

    public Filme getFilme() {
        return filme;
    }
}
