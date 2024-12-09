package com.hagemann.nttbank.naousar.domain.correntista;

import java.math.BigInteger;

public record DetalheCorrentistaDto(BigInteger id, String nome,
                                    String sobrenome, String email, Boolean ativo) {

    public DetalheCorrentistaDto(Correntista correntista) {
        this(correntista.getId(), correntista.getNome(), correntista.getSobrenome(), correntista.getEmail(),
                correntista.getAtivo());
    }
}
