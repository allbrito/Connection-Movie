package br.ucsal.connection_movie.test;


import br.ucsal.connection_movie.model.Filme;
import br.ucsal.connection_movie.model.PerfilCinefilo;
import br.ucsal.connection_movie.model.Recomendacao;
import br.ucsal.connection_movie.model.Usuario;
import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;
import br.ucsal.connection_movie.service.*;
import br.ucsal.connection_movie.util.GeradorAleatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RecomendadorTest {

    @ExtendWith(MockitoExtension.class)

    @Mock
    private CatalogoFilmesAPI catalogo;
    @Mock
    private HistoricoUsuarioRepository historico;
    @Mock
    private NotificadorPush notificador;
    @Mock
    private GeradorAleatorio gerador;


    private CalculadoraScore calculadora;
    private FiltroFilmes filtro;

    private Recomendador recomendador;

    List<Filme> filmes;
    PerfilCinefilo perfil;
    Usuario user;


    @BeforeEach
    void prepararAmbiente() {
        calculadora = new CalculadoraScore();
        filtro = new FiltroFilmes();

        recomendador = new Recomendador(catalogo, historico, notificador, gerador, calculadora, filtro);

        filmes = List.of(new Filme(
                "F01",
                "Duna: Parte Dois",
                166,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.QUATORZE,
                Idioma.INGLES,
                92
        ), new Filme(
                "F02",
                "Ela (Her)",
                126,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA, Genero.ROMANCE),
                ClassificacaoEtaria.DEZESSEIS,
                Idioma.INGLES,
                78
        ), new Filme(
                "F03",
                "O Iluminado",
                146,
                List.of(Genero.TERROR),
                ClassificacaoEtaria.DEZOITO,
                Idioma.INGLES,
                88
        ), new Filme(
                "F04",
                "Interestelar",
                169,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.INGLES,
                95
        ), new Filme(
                "F05",
                "Tropa de Elite",
                115,
                List.of(Genero.ACAO, Genero.DRAMA),
                ClassificacaoEtaria.DEZOITO,
                Idioma.PORTUGUES,
                80
        ), new Filme(
                "F06",
                "Click",
                107,
                List.of(Genero.COMEDIA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.INGLES,
                65
        ), new Filme(
                "F07",
                "A Chegada",
                116,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.INGLES,
                84
        ), new Filme(
                        "F09",
                        "Como treinar seu dragão",
                        110,
                        List.of(Genero.ANIMACAO, Genero.ACAO),
                        ClassificacaoEtaria.LIVRE,
                        Idioma.INGLES,
                        84
                ), new Filme("F10",
                        "Como treinar seu dragão 2",
                        110,
                        List.of(Genero.ANIMACAO, Genero.ACAO),
                        ClassificacaoEtaria.LIVRE,
                        Idioma.INGLES,
                        84
                ), new Filme(
                        "F11",
                        "Vingadores 2",
                        110,
                        List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO),
                        ClassificacaoEtaria.LIVRE,
                        Idioma.INGLES,
                        84
                )

        ) ;


        // Criando o PerfilCinefilo de Maria
        perfil = new PerfilCinefilo(
                90,                      // duracaoMinimaPreferida
                150,                                        // duracaoMaximaPreferida
                ClassificacaoEtaria.DEZESSEIS,              // classificacaoEtaria (máx 16 anos)
                List.of(Idioma.PORTUGUES, Idioma.INGLES)    // idiomasAceitos
        );

        // Adicionando pesos de gênero
        perfil.adicionarPeso(Genero.FICCAO_CIENTIFICA, 0.9);
        perfil.adicionarPeso(Genero.DRAMA, 0.6);
        perfil.adicionarPeso(Genero.COMEDIA, 0.5);
        perfil.adicionarPeso(Genero.TERROR, 0.0);
        perfil.adicionarPeso(Genero.ROMANCE, 0.4);

        // Registrando filmes já assistidos
        perfil.marcarAssistido(filmes.get(3));
        perfil.marcarAssistido(filmes.get(4));
        perfil.marcarAssistido(filmes.get(6));

        // Registrando notas dadas
        perfil.adicionarNota(filmes.get(3), 5);
        perfil.adicionarNota(filmes.get(6), 2);

        user = new Usuario(
                "usr-001",// id
                "Maria",     // nome
                28,          // idade
                perfil,      // perfil
                true         // notificacoesHabilitadas
        );
    }


    @Test
    void testDevolveListRespeitandoTamanhoPedido() {

        when(catalogo.buscarTodos()).thenReturn(filmes);

        List<Recomendacao> resultado = recomendador.recomendar(user, 5);
        System.out.println("Filme | Duração | Score final");
        for (Recomendacao r : resultado) {
            System.out.printf("%s -- %s | %d | %.1f%n", r.filme().getId(), r.filme().getNome(), r.filme().getDuracao(), r.score());
        }
        assertEquals(5, resultado.size());
    }

    @Test
    void testDevolveListVaziaSeCatologoEstiverVazio() {
        when(catalogo.buscarTodos()).thenReturn(new ArrayList<>());

        List<Recomendacao> resultado = recomendador.recomendar(user, 5);

        assertEquals(0, resultado.size());
    }

    @Test
    void testDevolveListVaziaSeCatologoEstiverIndisponivel() {
        assertDoesNotThrow(() -> recomendador.recomendar(user, 5));
    }

    @Test
    void testNotificacoesIndisponiveis() {
        when(catalogo.buscarTodos()).thenReturn(filmes);
        assertDoesNotThrow(() -> recomendador.recomendar(user, 5));

    }

    @Test
    void testRegistrarRecomendacaoEChamadoAposRecomendar() {
        when(catalogo.buscarTodos()).thenReturn(filmes);

        List<Recomendacao> resultado = recomendador.recomendar(user, 5);

        verify(historico).registrarRecomendacao(eq(user), anyList());
    }

    @Test
    void testNofificacoesSaoChamadasSeLigado() {
        when(catalogo.buscarTodos()).thenReturn(filmes);
        List<Recomendacao> resultado = recomendador.recomendar(user, 5);
        verify(notificador).enviar(eq(user), anyList());
    }
    @Test
    void testNofificacoesNaoSaoChamadasSeLigado() {
        user.setNotificacoesHabilitadas(false);

        when(catalogo.buscarTodos()).thenReturn(filmes);
        List<Recomendacao> resultado = recomendador.recomendar(user, 5);
        verify(notificador, times(0)).enviar(eq(user), anyList());
    }

    @Test
    void testGerarAleatorioDevolveListaDoConjuntoFiltrado() {
        when(catalogo.buscarTodos()).thenReturn(filmes);
        when(gerador.gerarAleatorio(eq(filmes.size()-1))).thenReturn(List.of(5,2,3,4,6));

        List<Recomendacao> resultado = recomendador.recomendarAleatorio();

        Filme[] filmesRecebidos = resultado.stream().map(Recomendacao::filme).toArray(Filme[]::new);
        Filme[] filmesEsperados = {filmes.get(5), filmes.get(2), filmes.get(3), filmes.get(4), filmes.get(6)};

        assertArrayEquals(filmesEsperados, filmesRecebidos);

    }

    @Test
    void testGerarAleatorioDesempata() {
        when(catalogo.buscarTodos()).thenReturn(filmes);
        when(gerador.desempatar(anyInt(), anyInt())).thenReturn(1);
        List<Recomendacao> resultado = recomendador.recomendar(user, 5);

        assertEquals(5, resultado.size());
    }

}
