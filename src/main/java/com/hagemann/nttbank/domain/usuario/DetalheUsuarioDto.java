package com.hagemann.nttbank.domain.usuario;

import java.math.BigInteger;

public record DetalheUsuarioDto(BigInteger id, String login) {

    public DetalheUsuarioDto(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin());
    }
}
