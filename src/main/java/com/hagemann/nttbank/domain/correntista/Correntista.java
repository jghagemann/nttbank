package com.hagemann.nttbank.domain.correntista;

import com.hagemann.nttbank.domain.conta.Conta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Table(name = "correntistas")
@Entity(name = "Correntista")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Correntista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String nome;
    private String sobrenome;

    @Column(unique = true)
    private String email;

    private List<Conta> contas;

    private Boolean ativo;

    public Correntista(CorrentistaDto correntistaDto) {
        this.nome = correntistaDto.nome();
        this.sobrenome = correntistaDto.sobrenome();
        this.email = correntistaDto.email();
        this.contas = correntistaDto.contas();
        this.ativo = correntistaDto.ativo();
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
