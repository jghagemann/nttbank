package com.hagemann.nttbank.application.usecases.usuario;

import com.hagemann.nttbank.application.gateways.RepositorioDeUsuario;
import com.hagemann.nttbank.domain.entities.Usuario;

public class AtualizarUsuario {

    private final RepositorioDeUsuario repositorio;

    public AtualizarUsuario(RepositorioDeUsuario repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        return repositorio.atualizar(usuario);
    }
}
