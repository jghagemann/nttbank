package com.hagemann.nttbank.application.usecases.usuario;

import com.hagemann.nttbank.application.gateways.RepositorioDeUsuario;
import com.hagemann.nttbank.domain.entities.Usuario;

public class CriarUsuario {

    private final RepositorioDeUsuario repositorioDeUsuario;

    public CriarUsuario(RepositorioDeUsuario repositorioDeUsuario) {
        this.repositorioDeUsuario = repositorioDeUsuario;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        return repositorioDeUsuario.cadastrarUsuario(usuario);
    }

}
