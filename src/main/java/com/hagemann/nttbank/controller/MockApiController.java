package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.client.AccountDtoResponse;
import com.hagemann.nttbank.service.MockApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("accounts")
public class MockApiController {

    private MockApiService mockApiService;

    public MockApiController(MockApiService mockApiService) {
        this.mockApiService = mockApiService;
    }

    @GetMapping
    public Mono<ResponseEntity<List<AccountDtoResponse>>> getall() {
        return mockApiService.getAll();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountDtoResponse>> getOne(@PathVariable BigInteger id) {
        return mockApiService.getOne(id);
    }
}
