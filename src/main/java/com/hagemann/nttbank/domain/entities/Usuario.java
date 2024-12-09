package com.hagemann.nttbank.domain.entities;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    private BigInteger id;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String login;
    private String senha;
    private LocalDate nascimento;
    private String email;
}
