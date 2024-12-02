package com.hagemann.nttbank.service;

import com.hagemann.nttbank.client.ConversaoDtoRequest;
import com.hagemann.nttbank.client.ConversaoDtoResponse;
import com.hagemann.nttbank.client.ExternalApiClient;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExternalApiServiceImplTest {

    @InjectMocks
    private ExternalApiServiceImpl externalApiServiceImpl;

    @Mock
    private ExternalApiClient externalApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve retornar uma conversão de moeda")
    @Test
    void shouldReturnConversionResponse() {
        // Arrange
        ConversaoDtoRequest request = new ConversaoDtoRequest("BRL");

        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", BigDecimal.TEN);
        ConversaoDtoResponse response = new ConversaoDtoResponse(true,12314334, "EUR", "2024-11-29", rates);

        Mockito.when(externalApiClient.get(Mockito.any(ConversaoDtoRequest.class)))
                .thenReturn(Mono.just(response));

        Mono<ResponseEntity<ConversaoDtoResponse>> result = externalApiServiceImpl.get(request);

        result.doOnTerminate(() -> {
            assertNotNull(result.block());
            assertEquals("EUR", result.block().getBody().base());
            assertEquals(BigDecimal.TEN, result.block().getBody().rates().get("BRL"));
        }).block();
    }

}