package com.hagemann.nttbank.domain.transacao;

import com.hagemann.nttbank.domain.conta.Conta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table(name = "transacoes")
@Entity(name = "Transacao")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    private LocalDateTime dataTransacao;
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaDestino;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    public Transacao(BigDecimal valor, Conta contaOrigem, Conta contaDestino, TipoTransacao tipoTransacao) {
        this.dataTransacao = LocalDateTime.now();
        this.valor = valor;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.tipoTransacao = tipoTransacao;
    }
}
