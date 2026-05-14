package br.ucsal.connection_movie.model;

import br.ucsal.connection_movie.exception.DuracaoInvalidaException;
import br.ucsal.connection_movie.exception.NotaInvalidaException;
import br.ucsal.connection_movie.exception.PerfilIncompletoException;
import br.ucsal.connection_movie.exception.PesoInvalidoException;
import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilCinefilo {

    private Map<Genero, Double> pesosGenero;
    private int duracaoMinimaPreferida;
    private int duracaoMaximaPreferida;
    private ClassificacaoEtaria classificacaoEtaria;
    private List<Idioma> idiomasAceitos;
    private List<Filme> filmesAssistidos;
    private Map<Filme, Integer> notas;

    public PerfilCinefilo(int duracaoMinimaPreferida, int duracaoMaximaPreferida, ClassificacaoEtaria classificacaoEtaria, List<Idioma> idiomasAceitos) {
        if(duracaoMinimaPreferida <= 0 || duracaoMaximaPreferida < duracaoMinimaPreferida)
            throw new DuracaoInvalidaException(duracaoMinimaPreferida, duracaoMaximaPreferida);
        if(classificacaoEtaria == null)
            throw new PerfilIncompletoException(classificacaoEtaria);
        if (idiomasAceitos == null || idiomasAceitos.isEmpty())
            throw new PerfilIncompletoException(idiomasAceitos);

        this.pesosGenero = new HashMap<>();
        this.duracaoMinimaPreferida = duracaoMinimaPreferida;
        this.duracaoMaximaPreferida = duracaoMaximaPreferida;
        this.classificacaoEtaria = classificacaoEtaria;
        this.idiomasAceitos = idiomasAceitos;
        this.filmesAssistidos = new ArrayList<>();
        this.notas = new HashMap<>();
    }

    public void adicionarNota(Filme filme, int nota){
        if(nota < 1 || nota > 5)
            throw new NotaInvalidaException(nota);
        notas.put(filme, nota);
    }

    public void adicionarPeso(Genero genero, double peso) {
        if(peso > 1.0 || peso < 0.0) {
            throw new PesoInvalidoException(peso);
        }
        pesosGenero.put(genero, peso);
    }

    public void adicionarIdioma(Idioma idioma) {
        idiomasAceitos.add(idioma);
    }

    public void marcarAssistido(Filme filme) {
        filmesAssistidos.add(filme);
    }

    public boolean jaAssistiu(Filme filme) {
        return filmesAssistidos.contains(filme);
    }

    public List<Idioma> getIdiomasAceitos() {
        return idiomasAceitos;
    }

    public ClassificacaoEtaria getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public int getDuracaoMinimaPreferida() {
        return duracaoMinimaPreferida;
    }

    public int getDuracaoMaximaPreferida() {
        return duracaoMaximaPreferida;
    }

    public List<Filme> getFilmesAssistidos() {
        return filmesAssistidos;
    }

    public Map<Genero, Double> getPesosGenero() {
        return pesosGenero;
    }

    public Map<Filme, Integer> getNotas() {
        return notas;
    }
}
