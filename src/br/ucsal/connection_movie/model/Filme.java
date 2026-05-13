package br.ucsal.connection_movie.model;

import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;

import java.util.List;

public class Filme {

    private Long id;
    private String nome;
    private int duracao;
    private List<Genero> generos;
    private ClassificacaoEtaria classificacaoEtaria;
    private Idioma idioma;
    private double popularidade;

    public Filme(Long id, String nome, int duracao, List<Genero> generos, ClassificacaoEtaria classificacaoEtaria, Idioma idioma, double popularidade) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
        this.generos = generos;
        this.classificacaoEtaria = classificacaoEtaria;
        this.idioma = idioma;
        this.popularidade = popularidade;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public ClassificacaoEtaria getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public double getPopularidade() {
        return popularidade;
    }
}
