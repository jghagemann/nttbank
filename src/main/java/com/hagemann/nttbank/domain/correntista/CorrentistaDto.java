package com.hagemann.nttbank.domain.correntista;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CorrentistaDto(
        @NotBlank
        String nome,
        @NotBlank
        String sobrenome,

        @NotBlank
        @Email
        String email){
}
