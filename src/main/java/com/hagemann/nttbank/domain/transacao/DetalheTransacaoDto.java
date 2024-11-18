package com.hagemann.nttbank.domain.transacao;

import java.math.BigDecimal;
import java.math.BigInteger;

public record DetalheTransacaoDto(BigInteger id, BigDecimal valor, BigInteger contaOrigemId, BigInteger contaDestinoId,
                                  TipoTransacao tipoTransacao) {

    public DetalheTransacaoDto(Transacao transacao) {
        this(transacao.getId(), transacao.getValor(), transacao.getContaOrigem().getId(), transacao.getContaDestino().getId(),
                transacao.getTipoTransacao());
    }
}
