package com.hagemann.nttbank.infra.controller.dto;

import com.hagemann.nttbank.domain.enums.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ContaDto(

        BigInteger id,

        @NotNull
        BigInteger usuarioId,

        @NotBlank
        String agencia,

        @NotBlank
        String numero,

        @NotNull
        BigDecimal saldo,

        @NotNull
        TipoConta tipoConta,

        @NotNull
        Boolean bloqueada
) {
}
