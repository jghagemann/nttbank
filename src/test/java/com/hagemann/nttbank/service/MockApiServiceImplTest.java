package com.hagemann.nttbank.service;

import com.hagemann.nttbank.naousar.client.AccountDtoResponse;
import com.hagemann.nttbank.naousar.client.MockApiClient;
import com.hagemann.nttbank.naousar.service.MockApiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MockApiServiceImplTest {

    @InjectMocks
    private MockApiServiceImpl mockApiServiceImpl;

    @Mock
    private MockApiClient mockApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar todas as contas")
    void shouldReturnAllAccounts() {
        AccountDtoResponse account1 = new AccountDtoResponse("1", "BRL", "1000.00", "178926");
        AccountDtoResponse account2 = new AccountDtoResponse("2", "USD", "1500.0", "8764387");
        List<AccountDtoResponse> accountList = List.of(account1, account2);

        Mockito.when(mockApiClient.getAll()).thenReturn(Flux.fromIterable(accountList));

        Mono<ResponseEntity<List<AccountDtoResponse>>> result = mockApiServiceImpl.getAll();

        result.doOnTerminate(() -> {
            assertNotNull(result.block());
            assertEquals(2, result.block().getBody().size());
            assertEquals("BRL", result.block().getBody().get(0).currency());
            assertEquals("1000.00", result.block().getBody().get(0).balance());
        }).block();
    }

    @Test
    @DisplayName("Deve retornar uma conta")
    void shouldReturnSingleAccount() {
        AccountDtoResponse account = new AccountDtoResponse("1", "GBP", "1000.00", "178926");

        Mockito.when(mockApiClient.getOne(Mockito.any(BigInteger.class))).thenReturn(Mono.just(account));

        Mono<ResponseEntity<AccountDtoResponse>> result = mockApiServiceImpl.getOne(BigInteger.ONE);

        // Assert
        result.doOnTerminate(() -> {
            assertNotNull(result.block());
            assertEquals("GBP", result.block().getBody().currency());
            assertEquals("1000.00", result.block().getBody().balance());
        }).block();
    }
}