package com.hagemann.nttbank.domain.entities;

import com.hagemann.nttbank.domain.enums.TipoDespesa;
import com.hagemann.nttbank.domain.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transacao {

    private BigInteger id;

    private BigInteger contaId;

    private BigDecimal valor;

    private String descricao;

    private TipoOperacao tipoOperacao;

    private BigInteger contaIdTransferencia;

    private TipoDespesa tipoDespesa;
}
