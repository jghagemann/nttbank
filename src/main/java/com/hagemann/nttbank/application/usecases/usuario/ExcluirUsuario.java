package com.hagemann.nttbank.application.usecases.usuario;

import com.hagemann.nttbank.application.gateways.RepositorioDeUsuario;

import java.math.BigInteger;

public class ExcluirUsuario {

    private final RepositorioDeUsuario repositorio;

    public ExcluirUsuario(RepositorioDeUsuario repositorio) {
        this.repositorio = repositorio;
    }

    public void excluirUsuario(BigInteger id) {
        repositorio.excluirUsuario(id);
    }}
