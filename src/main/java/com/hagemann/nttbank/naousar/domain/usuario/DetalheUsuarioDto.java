package com.hagemann.nttbank.naousar.domain.usuario;

import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;

import java.math.BigInteger;

public record DetalheUsuarioDto(BigInteger id, String login) {

    public DetalheUsuarioDto(UsuarioEntity usuarioEntity) {
        this(usuarioEntity.getId(), usuarioEntity.getLogin());
    }
}
