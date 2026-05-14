package br.ucsal.connection_movie.test;

import br.ucsal.connection_movie.model.Filme;
import br.ucsal.connection_movie.model.PerfilCinefilo;
import br.ucsal.connection_movie.model.Usuario;
import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.model.enums.Idioma;
import br.ucsal.connection_movie.service.*;
import br.ucsal.connection_movie.util.GeradorAleatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.mockito.Mockito.when;

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

    @InjectMocks
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
        ));


        // Criando o PerfilCinefilo de Maria
        perfil = new PerfilCinefilo(
                90,                                              // duracaoMinimaPreferida
                150,                                             // duracaoMaximaPreferida
                ClassificacaoEtaria.DEZESSEIS,              // classificacaoEtaria (máx 16 anos)
                List.of(Idioma.PORTUGUES, Idioma.INGLES)         // idiomasAceitos
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
                "usr-001",   // id
                "Maria",     // nome
                28,          // idade
                perfil, // perfil
                true         // notificacoesHabilitadas
        );
    }


    @Test
    void testDevolveListRespeitandoTamanhoPedido() {
        when(catalogo.buscarTodos()).thenReturn(filmes);

        recomendador.recomendar(user, 5);
    }

//    @Test
//    void testNotificacoesIndisponiveis() {
//
//        when(notificador.enviar();)
//        Assertions.assertDoesNotThrow();
//    }
}
