package com.hagemann.nttbank.domain.enums;

import lombok.Getter;

@Getter
public enum TipoConta {

    CORRENTE("Corrente"),
    INVESTIMENTO("Investimento");

    private String tipo;

    TipoConta(String tipo) {
        this.tipo = tipo;
    }
}
