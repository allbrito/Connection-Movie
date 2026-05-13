package br.ucsal.connection_movie.model.enums;

public enum ClassificacaoEtaria {
    LIVRE(1), DEZ(2), DOZE(3), QUATORZE(4), DEZESSEIS(5), DEZOITO(6);



    private int id;

    ClassificacaoEtaria(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
