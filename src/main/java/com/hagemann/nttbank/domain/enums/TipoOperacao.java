package com.hagemann.nttbank.domain.enums;

public enum TipoOperacao {

    DEBITO("Débito"),
    CREDITO("Crédito");

    private String operacao;

    TipoOperacao(String operacao) {
        this.operacao = operacao;
    }
}
