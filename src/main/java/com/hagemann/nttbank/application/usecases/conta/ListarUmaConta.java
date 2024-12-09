package com.hagemann.nttbank.application.usecases.conta;

import com.hagemann.nttbank.application.gateways.RepositorioDeConta;
import com.hagemann.nttbank.domain.entities.Conta;

import java.math.BigInteger;

public class ListarUmaConta {

    private final RepositorioDeConta repositorio;

    public ListarUmaConta(RepositorioDeConta repositorio) {
        this.repositorio = repositorio;
    }

    public Conta listar(BigInteger id) {
        return repositorio.listar(id);
    }
}
