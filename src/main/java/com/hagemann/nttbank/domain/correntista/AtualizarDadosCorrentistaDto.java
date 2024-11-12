package com.hagemann.nttbank.domain.correntista;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record AtualizarDadosCorrentistaDto(

        @NotNull
        BigInteger id,
        String nome,
        String sobrenome,
        String email
) {
}
