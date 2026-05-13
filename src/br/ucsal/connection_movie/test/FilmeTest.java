package br.ucsal.connection_movie.test;

import br.ucsal.connection_movie.model.Filme;
import br.ucsal.connection_movie.model.PerfilCinefilo;
import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmeTest {

    Filme filme;
    List<Idioma> idiomas;
    List<Genero> generos;

    PerfilCinefilo perfilCinefilo;
    @BeforeEach
    void prepararAmbiente() {

        idiomas = new ArrayList<Idioma>();
        idiomas.add(Idioma.PORTUGUES);
        perfilCinefilo = new PerfilCinefilo(2, 5, ClassificacaoEtaria.DEZ, idiomas);
        generos = new ArrayList();
        generos.add(Genero.ACAO);

        filme = new Filme("F01", "Duna: Parte Dois", 3, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 78.0);
    }

    @Test
    void testCriacaoDeFilmeComTodosOsAtributos() {
        assertAll("Testa se em um filme todas os atributos estão preenchidos",
                () -> assertNotNull(filme.getId()),
                () -> assertNotNull(filme.getNome()),
                () -> assertTrue(filme.getDuracao() > 0),
                () -> assertNotNull(filme.getGeneros()),
                () -> assertFalse(filme.getGeneros().isEmpty()),
                () -> assertNotNull(filme.getClassificacaoEtaria()),
                () -> assertNotNull(filme.getIdioma()),
                () -> assertTrue(filme.getPopularidade() >= 0)
        );
    }


    @Test
    @DisplayName("Testa se o sistema reconhece a duplicidade de um filme pelo seu ID")
    void testFilmesIguais() {

        Filme filme2 = new Filme("F01", "Duna: Parte Dois", 3, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 78.0);

        assertTrue(filme.equals(filme2));
    }
}
