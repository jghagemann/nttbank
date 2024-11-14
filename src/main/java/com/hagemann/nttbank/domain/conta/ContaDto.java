package com.hagemann.nttbank.domain.conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ContaDto(

        @NotNull
        BigDecimal saldo,
        @NotBlank
        String numero,
        @NotNull
        BigInteger correntistaId,
        @NotNull
        TipoConta tipoConta) {
}
