package com.hagemann.nttbank.domain.correntista;

import com.hagemann.nttbank.domain.conta.Conta;

import java.math.BigInteger;
import java.util.Set;

public record DetalheCorrentistaDto(BigInteger id, String nome,
                                    String sobrenome, String email, Set<Conta> contas, Boolean ativo) {

    public DetalheCorrentistaDto(Correntista correntista) {
        this(correntista.getId(), correntista.getNome(), correntista.getSobrenome(), correntista.getEmail(),
                correntista.getContas(), correntista.getAtivo());
    }
}
