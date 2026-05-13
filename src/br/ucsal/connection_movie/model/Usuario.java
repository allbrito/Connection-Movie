package br.ucsal.connection_movie.model;

public class Usuario {

    private String id;
    private String nome;
    private int idade;
    private PerfilCinefilo perfil;
    private boolean notificacoesHabilitadas;

    public Usuario(String id, String nome, int idade, PerfilCinefilo perfil, boolean notificacoesHabilitadas) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.perfil = perfil;
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public PerfilCinefilo getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilCinefilo perfil) {
        this.perfil = perfil;
    }

    public boolean isNotificacoesHabilitadas() {
        return notificacoesHabilitadas;
    }

    public void setNotificacoesHabilitadas(boolean notificacoesHabilitadas) {
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }
}
