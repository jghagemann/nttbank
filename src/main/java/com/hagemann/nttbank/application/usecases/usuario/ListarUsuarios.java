package com.hagemann.nttbank.application.usecases.usuario;

import com.hagemann.nttbank.application.gateways.RepositorioDeUsuario;
import com.hagemann.nttbank.domain.entities.Usuario;

import java.util.List;

public class ListarUsuarios {

    private final RepositorioDeUsuario repositorioDeUsuario;

    public ListarUsuarios(RepositorioDeUsuario repositorioDeUsuario) {
        this.repositorioDeUsuario = repositorioDeUsuario;
    }

    public List<Usuario> listarTodosUsuarios() {
        return this.repositorioDeUsuario.listarTodos();
    }
}
