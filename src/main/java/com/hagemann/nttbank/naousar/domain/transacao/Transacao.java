package com.hagemann.nttbank.naousar.domain.transacao;

import com.hagemann.nttbank.naousar.domain.conta.Conta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table(name = "transacoes")
@Entity(name = "Transacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

    @Id
    @GeneratedValue(generator = "transacao", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "transacao", sequenceName = "seq_transacao", allocationSize = 1)
    private BigInteger id;

    @Column(nullable = false)
    private LocalDateTime dataTransacao;

    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaDestino;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

}
