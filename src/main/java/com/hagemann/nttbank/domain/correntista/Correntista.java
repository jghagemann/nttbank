package com.hagemann.nttbank.domain.correntista;

import com.hagemann.nttbank.domain.conta.Conta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
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
    private Set<Conta> contas = new HashSet<>();

    private Boolean ativo;

    public Correntista(CorrentistaDto correntistaDto) {
        this.nome = correntistaDto.nome();
        this.sobrenome = correntistaDto.sobrenome();
        this.email = correntistaDto.email();
        this.ativo = true;
    }

    public void atualizarDados(AtualizarDadosCorrentistaDto atualizarDadosCorrentistaDto) {
        if (!atualizarDadosCorrentistaDto.nome().isBlank()) {
            this.nome = atualizarDadosCorrentistaDto.nome();
        }
        if (!atualizarDadosCorrentistaDto.sobrenome().isBlank()) {
            this.sobrenome = atualizarDadosCorrentistaDto.sobrenome();
        }
        if (!atualizarDadosCorrentistaDto.email().isBlank()) {
            this.email = atualizarDadosCorrentistaDto.email();
        }

        if (atualizarDadosCorrentistaDto.ativo() != null) {
            this.ativo = atualizarDadosCorrentistaDto.ativo();
        }

    }

    public void excluir() {
        this.ativo = false;
    }

}
