package com.hagemann.nttbank.domain.conta;

import com.hagemann.nttbank.domain.correntista.Correntista;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Table(name = "contas")
@Entity(name = "Conta")
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

}
