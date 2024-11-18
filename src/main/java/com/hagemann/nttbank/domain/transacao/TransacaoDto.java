package com.hagemann.nttbank.domain.transacao;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

public record TransacaoDto(
        @NotNull
        BigDecimal valor,

        @NotNull
        BigInteger contaOrigemId,

        @NotNull
        BigInteger contaDestinoId,

        @NotNull
        TipoTransacao tipoTransacao) {
}
