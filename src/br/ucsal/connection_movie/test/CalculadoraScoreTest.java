package br.ucsal.connection_movie.test;

import br.ucsal.connection_movie.model.Filme;
import br.ucsal.connection_movie.model.PerfilCinefilo;
import br.ucsal.connection_movie.model.Usuario;
import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;
import br.ucsal.connection_movie.service.CalculadoraScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraScoreTest {


    Filme filme;
    List<Idioma> idiomas;
    List<Genero> generos;
    PerfilCinefilo perfilCinefilo;

    CalculadoraScore calculadoraScore;

    @BeforeEach
    void prepararAmbiente() {

        idiomas = new ArrayList<>();
        idiomas.add(Idioma.PORTUGUES);
        perfilCinefilo = new PerfilCinefilo(2, 5, ClassificacaoEtaria.DEZ, idiomas);
        perfilCinefilo.adicionarPeso(Genero.ACAO, 1.0);
        perfilCinefilo.adicionarPeso(Genero.FICCAO_CIENTIFICA, 1.0);

        generos = new ArrayList<>();
        generos.add(Genero.ACAO);
        generos.add(Genero.FICCAO_CIENTIFICA);
        filme = new Filme("F01", "Duna: Parte Dois", 3, generos, ClassificacaoEtaria.QUATORZE, Idioma.PORTUGUES, 100);


        calculadoraScore = new CalculadoraScore();
    }

    @Test
    void testFilmeComTodosOsGenerosFavoritos() {

        filme = new Filme("F01", "Duna: Parte Dois", 100, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 0);
        assertEquals(50, calculadoraScore.calcular(filme, perfilCinefilo));

    }

    @Test
    @DisplayName("Testa se um filme com duração dentro dos valores preferidos pelo usuario retorna valor esperado de acordo com os pesos registrados")
    void testFilmeComDuracaoPreferida() {
        generos=new ArrayList<>();
        filme = new Filme("F01", "Duna: Parte Dois", 3, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 0);
        assertEquals(20, calculadoraScore.calcular(filme, perfilCinefilo));
    }



    @Test
    void testFilmeComPopularidadeMaxima() {
        generos=new ArrayList<>();
        filme = new Filme("F01", "Duna: Parte Dois", 100, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 100);
        assertEquals(15, calculadoraScore.calcular(filme, perfilCinefilo));
    }

    @Test
    void testFilmeComAfinidadeMaxima() {

        Filme filme2 = new Filme("F02", "Ela (Her)", 500, generos, ClassificacaoEtaria.DEZESSEIS, Idioma.PORTUGUES, 0);
        perfilCinefilo = new PerfilCinefilo(2, 5, ClassificacaoEtaria.DEZ, idiomas);
        perfilCinefilo.marcarAssistido(filme2);
        perfilCinefilo.adicionarNota(filme2, 5);

        filme = new Filme("F01", "Duna: Parte Dois", 100, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 0);
        assertEquals(15, calculadoraScore.calcular(filme, perfilCinefilo));
    }

    @Test
    void testFilmeComTodosOsAtributosMaximos() {

        generos.add(Genero.ROMANCE);
        Filme filme2 = new Filme("F02", "Ela (Her)", 500, generos, ClassificacaoEtaria.DEZESSEIS, Idioma.PORTUGUES, 0);
        perfilCinefilo.marcarAssistido(filme2);
        perfilCinefilo.adicionarNota(filme2, 5);

        assertEquals(100, calculadoraScore.calcular(filme, perfilCinefilo));

    }

}

