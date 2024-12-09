package com.hagemann.nttbank.application.usecases.conta;

import com.hagemann.nttbank.application.gateways.RepositorioDeConta;
import com.hagemann.nttbank.domain.entities.Conta;

public class AlterarConta {

    private final RepositorioDeConta repositorio;

    public AlterarConta(RepositorioDeConta repositorio) {
        this.repositorio = repositorio;
    }

    public Conta alterarConta(Conta conta) {
        return repositorio.alterarConta(conta);
    }
}
