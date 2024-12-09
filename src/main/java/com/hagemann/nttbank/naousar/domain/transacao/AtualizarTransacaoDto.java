package com.hagemann.nttbank.naousar.domain.transacao;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record AtualizarTransacaoDto(
        @NotNull
        BigInteger id,
        @NotNull
        Categoria categoria) {
}
