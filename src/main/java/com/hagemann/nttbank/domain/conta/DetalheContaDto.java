package com.hagemann.nttbank.domain.conta;

import com.hagemann.nttbank.domain.correntista.Correntista;

import java.math.BigDecimal;
import java.math.BigInteger;

public record DetalheContaDto(
        BigInteger id, BigDecimal saldo, String numero, BigInteger correntistaId,
            TipoConta tipoConta) {

            public DetalheContaDto(Conta conta) {
                this(conta.getId(), conta.getSaldo(), conta.getNumero(), conta.getCorrentista().getId(), conta.getTipoConta());
            }
}
