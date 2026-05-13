package br.ucsal.connection_movie.exception;

import br.ucsal.connection_movie.model.enums.ClassificacaoEtaria;
import br.ucsal.connection_movie.model.enums.Idioma;

import java.util.List;

public class PerfilIncompletoException extends RuntimeException {
    public PerfilIncompletoException(ClassificacaoEtaria campo) {
        super("Perfil incompleto: o campo ClassificacaoEtaria é obrigatório.");
    }

    public PerfilIncompletoException(List<Idioma> campo) {
        super("Perfil incompleto: o campo Idioma é obrigatório.");
    }
}
