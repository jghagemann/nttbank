package com.hagemann.nttbank.client;

import jakarta.validation.constraints.NotBlank;

public record ConversaoDtoRequest(
        @NotBlank
        String symbols) {
}
