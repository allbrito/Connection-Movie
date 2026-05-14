package br.ucsal.connection_movie.service;

import br.ucsal.connection_movie.exception.CatalogoIndisponivelException;
import br.ucsal.connection_movie.exception.NotificacoesIndisponiveisException;
import br.ucsal.connection_movie.model.*;
import br.ucsal.connection_movie.model.enums.Genero;
import br.ucsal.connection_movie.util.GeradorAleatorio;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Recomendador {

    private CatalogoFilmesAPI catalogo;
    private HistoricoUsuarioRepository historico;
    private NotificadorPush notificador;
    private GeradorAleatorio gerador;
    private CalculadoraScore calculadora;
    private FiltroFilmes filtro;

    public Recomendador(CatalogoFilmesAPI catalogo, HistoricoUsuarioRepository historico, NotificadorPush notificador, GeradorAleatorio gerador, CalculadoraScore calculadora, FiltroFilmes filtro) {
        this.catalogo = catalogo;
        this.historico = historico;
        this.notificador = notificador;
        this.gerador = gerador;
        this.calculadora = calculadora;
        this.filtro = filtro;
    }

    public List<Recomendacao> recomendar(Usuario usuario, int topN) {
        List<Filme> filmesDoCatalogo;
        try {
            filmesDoCatalogo = catalogo.buscarTodos();
        } catch (CatalogoIndisponivelException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }


        PerfilCinefilo perfilCinefilo = usuario.getPerfil();

        List<Filme> filmesFiltrados = filtro.filtrar(filmesDoCatalogo, perfilCinefilo);
        if (filmesFiltrados.isEmpty()) {
            return Collections.emptyList();
        }

        List<Recomendacao> recomendacoes = filmesFiltrados.stream().map(filme -> {
            double score = calculadora.calcular(filme, perfilCinefilo);
            String justificativa = gerarJustificativa(filme, perfilCinefilo, score);
            return new Recomendacao(filme, score, justificativa);
        }).collect(Collectors.toList());



        List<Recomendacao> resultado = ordenarLista(recomendacoes, topN);

        registrarRecomendacao(usuario, resultado);

        try {
            notificarUsuario(usuario, resultado);
        } catch (NotificacoesIndisponiveisException e) {
            System.out.println(e.getMessage());
        }

        return resultado;

    }

    public List<Recomendacao> recomendarAleatorio() {

        List<Filme> filmesDoCatalogo;
        try {
            filmesDoCatalogo = catalogo.buscarTodos();
        } catch (CatalogoIndisponivelException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }

        if (filmesDoCatalogo.isEmpty()) {
            return Collections.emptyList();
        }

        List<Recomendacao> recomendacoes = new ArrayList<>();

        List<Integer> numerosGerados = gerador.gerarAleatorio(filmesDoCatalogo.size()-1);
        try {
            for (int i : numerosGerados){
                recomendacoes.add(new Recomendacao(filmesDoCatalogo.get(i), 100.00, "Gerado Aleatoriamente"));
            }
        } catch (Exception e) {
            for (int i = 0; i<5; i++){
                 recomendacoes.add(new Recomendacao(filmesDoCatalogo.get(i), 100.00, "Gerado Aleatoriamente"));
            }
        }

        return recomendacoes;
    }

    private List<Recomendacao> ordenarLista(List<Recomendacao> recomendacoes, int topN){

        List<Recomendacao> recomendacoesOrdenadas = new ArrayList<>(recomendacoes);

        // Insertion sort com desempate
        for (int i = 1; i < recomendacoesOrdenadas.size(); i++) {
            Recomendacao atual = recomendacoesOrdenadas.get(i);
            int j = i - 1;

            while (j >= 0) {
                Recomendacao anterior = recomendacoesOrdenadas.get(j);

                if (anterior.score() > atual.score()) break;

                if (anterior.score() == atual.score()) {
                    int sorteado = gerador.desempatar(0, 1); // 0 ou 1
                    if (sorteado == 0) {
                        // atual vence — sobe uma posição
                        recomendacoesOrdenadas.set(j + 1, anterior);
                        recomendacoesOrdenadas.set(j, atual);
                    }
                    // sorteado == 1 — anterior permanece, para o loop
                    break;
                }

                // atual tem score maior — sobe
                recomendacoesOrdenadas.set(j + 1, anterior);
                j--;
            }

            recomendacoesOrdenadas.set(j + 1, atual);
        }

        return recomendacoesOrdenadas.subList(0, Math.min(topN, recomendacoesOrdenadas.size()));
    }

    private String gerarJustificativa(Filme filme, PerfilCinefilo perfil, double score) {
        String generos = filme.getGeneros().stream()
                .map(Genero::name)
                .collect(Collectors.joining(", "));
        return String.format(
                "Recomendado por compatibilidade de perfil (score %.1f). Generos: %s.",
                score, generos
        );
    }

    private void registrarRecomendacao(Usuario usuario, List<Recomendacao> recomendacao) {
        historico.registrarRecomendacao(usuario, recomendacao);
    }

    private void notificarUsuario(Usuario usuario, List<Recomendacao> recomendacao) {
        if (usuario.isNotificacoesHabilitadas()){
            notificador.enviar(usuario, recomendacao);
        }
    }
}
