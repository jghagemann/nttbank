package com.hagemann.nttbank.service;

import com.hagemann.nttbank.client.AccountDtoResponse;
import com.hagemann.nttbank.client.MockApiClient;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@Service
public class MockApiServiceImpl implements MockApiService {

    private MockApiClient mockApiClient;

    public MockApiServiceImpl(MockApiClient mockApiClient) {
        this.mockApiClient = mockApiClient;
    }

    @Override
    public Mono<ResponseEntity<List<AccountDtoResponse>>> getAll() {
        return mockApiClient.getAll()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<AccountDtoResponse>> getOne(@NotNull BigInteger id) {
        return mockApiClient.getOne(id)
                .map(ResponseEntity::ok);
    }
}
