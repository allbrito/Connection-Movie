package br.ucsal.Connection_Movie.service;

import br.ucsal.Connection_Movie.model.*;
import br.ucsal.Connection_Movie.model.enums.Genero;
import br.ucsal.Connection_Movie.util.GeradorAleatorio;

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


    public List<Recomendacao> recomendar(Usuario usuario, int topN) {

        List<Filme> filmesDoCatalogo = catalogo.buscarTodos();
        if (filmesDoCatalogo.isEmpty()) {
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
        notificarUsuario(usuario, resultado);
        return resultado;

    }

    public List<Recomendacao> recomendarAleatorio() {

        List<Filme> filmesDoCatalogo = catalogo.buscarTodos();

        if (filmesDoCatalogo.isEmpty()) {
            return Collections.emptyList();
        }

        return gerador.gerarAleatorio(filmesDoCatalogo.size());
    }

    private List<Recomendacao> ordenarLista(List<Recomendacao> recomendacoes, int topN){
        return recomendacoes.stream()
                .sorted(Comparator
                        .comparingDouble(Recomendacao::getScore).reversed()
                        .thenComparingDouble(r -> -r.getFilme().getPopularidade())
                        .thenComparingInt(r -> gerador.desempatar(0, 1)))
                .limit(topN)
                .collect(Collectors.toList());
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

    private List<Filme> buscarCatalogo() {
        try {
            return catalogo.buscarTodos();
        } catch (Exception e) {
            // API indisponível: loga e devolve vazio (nunca derruba o serviço)
            System.out.println("Catálogo indisponível: {}" + e.getMessage());
            return Collections.emptyList();
        }
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
