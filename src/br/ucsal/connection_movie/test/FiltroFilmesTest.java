package br.ucsal.connection_movie.test;

import br.ucsal.connection_movie.model.Filme;
import br.ucsal.connection_movie.model.PerfilCinefilo;
import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;
import br.ucsal.connection_movie.service.FiltroFilmes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FiltroFilmesTest {

    FiltroFilmes filtroFilmes;
    Filme filme;
    PerfilCinefilo perfilCinefilo;
    List<Idioma> idiomas;
    List<Genero> generos;
    List<Filme> filmes;

    @BeforeEach
    void prepararAmbiente(){
        filtroFilmes = new FiltroFilmes();
        generos = new ArrayList<>();
        idiomas = new ArrayList<>();
        idiomas.add(Idioma.PORTUGUES);
        perfilCinefilo = new PerfilCinefilo(2, 5, ClassificacaoEtaria.DEZ, idiomas);
        filmes = new ArrayList<>();
    }

    @Test
    @DisplayName("Testa se os filmes assistidos estão na lista de filtro de filmes")
    void testFilmeAssistidoNoFiltro(){
        filme = new Filme("F01", "Duna: Parte Um", 3, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 78.0);
        filmes.add(filme);
        perfilCinefilo.marcarAssistido(filme);
        assertFalse(filtroFilmes.filtrar(filmes, perfilCinefilo).contains(filme));
    }

    @Test
    @DisplayName("Testa se os filmes acima da classificação etária estão na lista de filtro de filmes")
    void testFilmeAcimaClassificacaoEtariaNoFiltro(){
        filme = new Filme("F01", "Duna: Parte Um", 3, generos, ClassificacaoEtaria.DEZOITO, Idioma.PORTUGUES, 78.0);
        filmes.add(filme);
        assertFalse(filtroFilmes.filtrar(filmes, perfilCinefilo).contains(filme));
    }

    @Test
    @DisplayName("Testa se os filmes com idiomas não selecionados estão na lista de filtro de filmes")
    void testFilmeIdiomaDiferenteNoFiltro(){
        filme = new Filme("F01", "Duna: Parte Um", 3, generos, ClassificacaoEtaria.DEZ, Idioma.INGLES, 78.0);
        filmes.add(filme);
        assertFalse(filtroFilmes.filtrar(filmes, perfilCinefilo).contains(filme));
    }

    @Test
    @DisplayName("Testa se o filme com gênero de peso 0 está na lista de filtro de filmes")
    void testFilmeComGeneroPesoZeroNoFiltro() {
        generos.add(Genero.ACAO);
        perfilCinefilo.adicionarPeso(Genero.ACAO, 0.0);
        filme = new Filme("F01", "Duna: Parte Um", 3, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 78.0);
        filmes.add(filme);
        assertFalse(filtroFilmes.filtrar(filmes, perfilCinefilo).contains(filme));
    }
}
