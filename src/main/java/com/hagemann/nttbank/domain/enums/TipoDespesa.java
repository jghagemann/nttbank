package com.hagemann.nttbank.domain.enums;

public enum TipoDespesa {

    ALIMENTACAO("Alimentação"),
    SAUDE("Saúde"),
    EDUCACAO("Educação"),
    LAZER("Lazer"),
    VESTUARIO("Vestuário"),
    MORADIA("Moradia");

    private String tipo;

    TipoDespesa(String tipo) {
        this.tipo = tipo;
    }
}
