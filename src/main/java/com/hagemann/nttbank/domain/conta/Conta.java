package com.hagemann.nttbank.domain.conta;

import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.transacao.Transacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

@Table(name = "contas")
@Entity(name = "Conta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    private BigDecimal saldo;

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    private Correntista correntista;

    @OneToMany(mappedBy = "contaOrigem")
    private Set<Transacao> transacoes;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    public Conta(BigDecimal saldo, String numero, Correntista correntista, TipoConta tipoConta) {
        this.saldo = saldo;
        this.numero = numero;
        this.correntista = correntista;
        this.tipoConta = tipoConta;
    }

    public void atualizarDados(AtualizarDadosContaDto atualizarDadosContaDto) {
        if (!atualizarDadosContaDto.numero().isBlank()) {
            this.numero = atualizarDadosContaDto.numero();
        }
    }
}
