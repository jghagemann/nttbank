package com.hagemann.nttbank.domain.correntista;

import com.hagemann.nttbank.domain.conta.Conta;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CorrentistaDto(
        @NotBlank
        String nome,
        @NotBlank
        String sobrenome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        List<Conta>contas,
        @NotBlank
        Boolean ativo) {
}
