package br.ucsal.connection_movie.model;

import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;

import java.util.List;
import java.util.Map;

public class PerfilCinefilo {

    private Map<Genero, Double> pesosGenero;
    private int duracaoMinimaPreferida;
    private int duracaoMaximaPreferida;
    private ClassificacaoEtaria ClassificacaoEtaria;
    private List<Idioma> idiomasAceitos;
    private List<Filme> filmesAssistidos;
    private Map<Filme, Integer> notas;

    public PerfilCinefilo(Map pesosGenero, int duracaoMinimaPreferida, int duracaoMaximaPreferida, ClassificacaoEtaria classificacaoEtaria, List idiomasAceitos, List filmesAssistidos, Map notas) {
        this.pesosGenero = pesosGenero;
        this.duracaoMinimaPreferida = duracaoMinimaPreferida;
        this.duracaoMaximaPreferida = duracaoMaximaPreferida;
        ClassificacaoEtaria = classificacaoEtaria;
        this.idiomasAceitos = idiomasAceitos;
        this.filmesAssistidos = filmesAssistidos;
        this.notas = notas;
    }

    public void adicionarNota(Filme filme, int nota){
        notas.put(filme, nota);
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
        return ClassificacaoEtaria;
    }

    public int getDuracaoMinimaPreferida() {
        return duracaoMinimaPreferida;
    }

    public int getDuracaoMaximaPreferida() {
        return duracaoMaximaPreferida;
    }

    public double getPesoGenero(Genero genero) {
        return pesosGenero.getOrDefault(genero, 0.0);
    }

    public List<Filme> getFilmesAssistidos() {
        return filmesAssistidos;
    }

    public Map<Filme, Integer> getNotas() {
        return notas;
    }
}
