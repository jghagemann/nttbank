package com.hagemann.nttbank.application.usecases.conta;

import com.hagemann.nttbank.application.gateways.RepositorioDeConta;
import com.hagemann.nttbank.domain.entities.Conta;

public class CriarConta {

    private final RepositorioDeConta repositorio;

    public CriarConta(RepositorioDeConta repositorio) {
        this.repositorio = repositorio;
    }

    public Conta criarConta(Conta conta) {
        return repositorio.criarConta(conta);
    }
}
