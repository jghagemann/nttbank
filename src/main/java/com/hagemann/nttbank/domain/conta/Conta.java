package com.hagemann.nttbank.domain.conta;

import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.transacao.Transacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

@Table(name = "contas")
@Entity(name = "Conta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conta {

    @Id
    @GeneratedValue(generator = "conta", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "conta", sequenceName = "seq_conta", allocationSize = 1)
    private BigInteger id;

    @Column(nullable = false)
    private BigDecimal saldo;

    @Column(unique = true, nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    private Correntista correntista;

    @OneToMany(mappedBy = "contaOrigem")
    private Set<Transacao> transacoes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

}
