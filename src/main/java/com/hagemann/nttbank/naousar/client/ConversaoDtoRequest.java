package com.hagemann.nttbank.naousar.client;

import jakarta.validation.constraints.NotBlank;

public record ConversaoDtoRequest(
        @NotBlank
        String symbols) {
}
