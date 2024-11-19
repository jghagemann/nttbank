package com.hagemann.nttbank.domain.transacao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public record DetalheTransacaoDto(BigInteger id, BigDecimal valor, LocalDateTime dataTransacao, BigInteger contaOrigemId, BigInteger contaDestinoId,
                                  TipoTransacao tipoTransacao, Categoria categoria) {

    public DetalheTransacaoDto(Transacao transacao) {
        this(transacao.getId(), transacao.getValor(), transacao.getDataTransacao(), transacao.getContaOrigem().getId(), transacao.getContaDestino().getId(),
                transacao.getTipoTransacao(), transacao.getCategoria());
    }
}
