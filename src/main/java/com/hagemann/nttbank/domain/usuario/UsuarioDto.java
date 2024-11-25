package com.hagemann.nttbank.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(
        @NotBlank
        String login,
        @NotBlank
        String senha) {
}
