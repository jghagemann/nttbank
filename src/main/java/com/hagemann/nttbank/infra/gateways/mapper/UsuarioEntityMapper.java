package com.hagemann.nttbank.infra.gateways.mapper;

import com.hagemann.nttbank.domain.entities.Usuario;
import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;

public class UsuarioEntityMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        return new UsuarioEntity(usuario.getId(), usuario.getCpf(), usuario.getNome(), usuario.getSobrenome(), usuario.getLogin(),
                usuario.getSenha(), usuario.getNascimento(), usuario.getEmail());
    }

    public Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(entity.getId(), entity.getCpf(), entity.getNome(), entity.getSobrenome(), entity.getLogin(),
                entity.getPassword(), entity.getNascimento(),
            entity.getEmail());
    }
}
