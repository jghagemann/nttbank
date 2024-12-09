package com.hagemann.nttbank.naousar.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(
        @NotBlank
        String login,
        @NotBlank
        String senha) {
}
