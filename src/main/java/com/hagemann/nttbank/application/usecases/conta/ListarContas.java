package com.hagemann.nttbank.application.usecases.conta;

import com.hagemann.nttbank.application.gateways.RepositorioDeConta;
import com.hagemann.nttbank.domain.entities.Conta;

import java.util.List;

public class ListarContas {

    private final RepositorioDeConta repositorio;

    public ListarContas(RepositorioDeConta repositorio) {
        this.repositorio = repositorio;
    }

    public List<Conta> listarContas() {
        return repositorio.listarContas();
    }
}
