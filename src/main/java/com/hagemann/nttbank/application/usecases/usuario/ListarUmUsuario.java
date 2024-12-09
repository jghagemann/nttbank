package com.hagemann.nttbank.application.usecases.usuario;

import com.hagemann.nttbank.application.gateways.RepositorioDeUsuario;
import com.hagemann.nttbank.domain.entities.Usuario;

import java.math.BigInteger;

public class ListarUmUsuario {

    private final RepositorioDeUsuario repositorio;

    public ListarUmUsuario(RepositorioDeUsuario repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario listar(BigInteger id) {
        return  repositorio.listar(id);
    }
}
