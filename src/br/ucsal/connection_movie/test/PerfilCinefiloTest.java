package br.ucsal.connection_movie.test;

import br.ucsal.connection_movie.exception.DuracaoInvalidaException;
import br.ucsal.connection_movie.exception.NotaInvalidaException;
import br.ucsal.connection_movie.exception.PerfilIncompletoException;
import br.ucsal.connection_movie.exception.PesoInvalidoException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PerfilCinefiloTest {

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

        filme = new Filme("F01", "Duna: Parte Dois", 3, generos, ClassificacaoEtaria.DEZ, Idioma.PORTUGUES, 78.0);
    }

    @Test
    @DisplayName("Testa se Lança Exceção Ao Adicionar Nota Invalida")
    void testLancaExcecaoAoAdicionarNotaInvalida() {
        assertThrows(NotaInvalidaException.class, () -> perfilCinefilo.adicionarNota(filme, 0));
    }

    @Test
    @DisplayName("Testa se Lança Exceção Ao Adicionar Peso Invalido")
    void testLancaExcecaoAoAdicionarPesoInvalido() {
        assertThrows(PesoInvalidoException.class, () -> perfilCinefilo.adicionarPeso(Genero.ACAO, 73));
    }

    @Test
    @DisplayName("Testa se lança Excecao Ao Criar Perfil Com Duracao Invalida")
    void testLancaExcecaoAoCriarPerfilComDuracaoInvalida() {
        assertThrows(DuracaoInvalidaException.class, () -> new PerfilCinefilo(0, 5, ClassificacaoEtaria.DEZ, idiomas));
    }

    @Test
    @DisplayName("Testa se lança Excecao Ao Criar Perfil Com Classificacao Invalida")
    void testLancaExcecaoAoCriarPerfilComClassificacaoInvalida() {
        assertThrows(PerfilIncompletoException.class, () -> new PerfilCinefilo(1, 5, null, idiomas));
    }
}
