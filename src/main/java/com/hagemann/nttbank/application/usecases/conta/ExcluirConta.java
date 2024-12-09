package com.hagemann.nttbank.application.usecases.conta;

import com.hagemann.nttbank.application.gateways.RepositorioDeConta;
import com.hagemann.nttbank.domain.entities.Conta;

import java.math.BigInteger;

public class ExcluirConta {

    private final RepositorioDeConta repositorio;

    public ExcluirConta(RepositorioDeConta repositorio) {
        this.repositorio = repositorio;
    }

    public void removerConta(BigInteger id) {
        repositorio.removerConta(id);
    }
}
