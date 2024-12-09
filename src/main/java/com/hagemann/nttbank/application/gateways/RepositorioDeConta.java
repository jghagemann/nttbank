package com.hagemann.nttbank.application.gateways;

import com.hagemann.nttbank.domain.entities.Conta;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public interface RepositorioDeConta {

    Conta criarConta(Conta conta);

    List<Conta> listarContas();

    Conta listar(BigInteger id);

    Conta alterarConta(Conta conta);

    void removerConta(BigInteger id);
}
