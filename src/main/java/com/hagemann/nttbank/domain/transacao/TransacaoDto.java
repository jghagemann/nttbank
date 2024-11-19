package com.hagemann.nttbank.domain.transacao;

import com.hagemann.nttbank.domain.conta.Conta;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoDto(

        @NotNull
        LocalDateTime dataTransacao,

        @NotNull
        BigDecimal valor,

        @NotNull
        Conta contaOrigem,

        @NotNull
        Conta contaDestino,

        @NotNull
        TipoTransacao tipoTransacao,

        @NotNull
        Categoria categoria) {
}
