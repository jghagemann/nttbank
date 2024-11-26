package com.hagemann.nttbank.service;

import com.hagemann.nttbank.client.AccountDtoResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

public interface MockApiService {

    Mono<ResponseEntity<List<AccountDtoResponse>>> getAll();

    Mono<ResponseEntity<AccountDtoResponse>> getOne(BigInteger id);

}
