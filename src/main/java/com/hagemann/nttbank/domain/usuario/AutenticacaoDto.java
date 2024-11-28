package com.hagemann.nttbank.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDto(

        @NotBlank
        String login,
        @NotBlank
        String senha) {
}
