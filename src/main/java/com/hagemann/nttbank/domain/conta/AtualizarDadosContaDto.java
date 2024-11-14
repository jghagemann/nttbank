package com.hagemann.nttbank.domain.conta;

import java.math.BigDecimal;
import java.math.BigInteger;

public record AtualizarDadosContaDto(BigInteger id, BigDecimal saldo, String numero) {
}
