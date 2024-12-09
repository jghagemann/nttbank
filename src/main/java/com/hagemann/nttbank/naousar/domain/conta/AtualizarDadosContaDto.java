package com.hagemann.nttbank.naousar.domain.conta;

import jakarta.validation.constraints.NotBlank;

import java.math.BigInteger;

public record AtualizarDadosContaDto(

        BigInteger id,

        @NotBlank
        String numero) {
}