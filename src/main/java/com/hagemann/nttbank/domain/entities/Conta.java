package com.hagemann.nttbank.domain.entities;

import com.hagemann.nttbank.domain.enums.TipoConta;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Conta {

    private BigInteger id;
    private BigInteger usuarioId;
    private String agencia;
    private String numero;
    private BigDecimal saldo;
    private TipoConta tipoConta;
    private Boolean bloqueada;
}
