package com.hagemann.nttbank.domain.correntista;

import com.hagemann.nttbank.domain.conta.Conta;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    private String nome;

    private String sobrenome;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "correntista")
    private Set<Conta> contas = new HashSet<>();

    private Boolean ativo;
}
