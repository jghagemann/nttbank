package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.client.ConversaoDtoRequest;
import com.hagemann.nttbank.client.ConversaoDtoResponse;
import com.hagemann.nttbank.service.ExternalApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExternalApiControllerTest {

    @InjectMocks
    private ExternalApiController externalApiController;

    @Mock
    private ExternalApiService externalApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar dados da conversão de moedas")
    void converter_ShouldReturnConversionData() {
        ConversaoDtoRequest request = new ConversaoDtoRequest("USD,EUR");
        ConversaoDtoResponse response = new ConversaoDtoResponse(
                true,
                1621234567,
                "USD",
                "2024-12-02",
                Map.of("EUR", BigDecimal.valueOf(0.84), "USD", BigDecimal.ONE)
        );

        Mockito.when(externalApiService.get(Mockito.any(ConversaoDtoRequest.class)))
                .thenReturn(Mono.just(ResponseEntity.ok(response)));

        ResponseEntity<ConversaoDtoResponse> result = externalApiController.converter(request).block();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("USD", result.getBody().base());
        assertEquals(BigDecimal.valueOf(0.84), result.getBody().rates().get("EUR"));
        assertEquals(BigDecimal.ONE, result.getBody().rates().get("USD"));
    }
}