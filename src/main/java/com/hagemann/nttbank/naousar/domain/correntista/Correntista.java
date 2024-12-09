package com.hagemann.nttbank.naousar.domain.correntista;

import com.hagemann.nttbank.naousar.domain.conta.Conta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Table(name = "correntistas")
@Entity(name = "Correntista")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Correntista {

    @Id
    @GeneratedValue(generator = "correntista", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "correntista", sequenceName = "seq_correntista", allocationSize = 1)
    private BigInteger id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "correntista")
    private Set<Conta> contas = new HashSet<>();

    @Column(nullable = false)
    private Boolean ativo;
}
