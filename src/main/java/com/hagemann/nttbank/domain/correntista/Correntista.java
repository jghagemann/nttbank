package com.hagemann.nttbank.domain.correntista;

import com.hagemann.nttbank.domain.conta.Conta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Table(name = "correntistas")
@Entity(name = "Correntista")
@Getter
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
    private Set<Conta> contas;

    private Boolean ativo;

    public Correntista(CorrentistaDto correntistaDto) {
        this.nome = correntistaDto.nome();
        this.sobrenome = correntistaDto.sobrenome();
        this.email = correntistaDto.email();
        this.contas = new HashSet<>();
        this.ativo = true;
    }

    public void atualizarDados(AtualizarDadosCorrentistaDto atualizarDadosCorrentistaDto) {
        if (atualizarDadosCorrentistaDto.nome() != null) {
            this.nome = atualizarDadosCorrentistaDto.nome();
        }
        if (atualizarDadosCorrentistaDto.sobrenome() != null) {
            this.sobrenome = atualizarDadosCorrentistaDto.sobrenome();
        }
        if (atualizarDadosCorrentistaDto.email() != null) {
            this.email = atualizarDadosCorrentistaDto.email();
        }

    }

    public void excluir() {
        this.ativo = false;
    }

}
