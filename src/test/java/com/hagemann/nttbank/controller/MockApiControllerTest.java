package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.naousar.client.AccountDtoResponse;
import com.hagemann.nttbank.naousar.controller.MockApiController;
import com.hagemann.nttbank.naousar.service.MockApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MockApiControllerTest {

    @InjectMocks
    MockApiController mockApiController;

    @Mock
    MockApiService mockApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @DisplayName("Deve retornar uma lista com todos os saldos das contas")
    @Test
    void getall() {
        List<AccountDtoResponse> accounts = List.of(
                new AccountDtoResponse("1", "USD", "100.00", "1234"),
                new AccountDtoResponse("2", "EUR", "200.00", "5678")
        );
        Mockito.when(mockApiService.getAll()).thenReturn(Mono.just(ResponseEntity.ok(accounts)));

        ResponseEntity<List<AccountDtoResponse>> response = mockApiController.getall().block();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("USD", response.getBody().get(0).currency());
    }

    @DisplayName("Deve retornar uma saldo de apenas uma conta")
    @Test
    void getOne() {
        AccountDtoResponse account = new AccountDtoResponse("1", "USD", "100.00", "1234");
        Mockito.when(mockApiService.getOne(Mockito.any(BigInteger.class))).thenReturn(Mono.just(ResponseEntity.ok(account)));

        ResponseEntity<AccountDtoResponse> response = mockApiController.getOne(BigInteger.ONE).block();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("USD", response.getBody().currency());
        assertEquals("1234", response.getBody().number());
    }
}