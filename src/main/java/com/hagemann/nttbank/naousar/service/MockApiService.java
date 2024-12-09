package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.client.AccountDtoResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

public interface MockApiService {

    Mono<ResponseEntity<List<AccountDtoResponse>>> getAll();

    Mono<ResponseEntity<AccountDtoResponse>> getOne(BigInteger id);

}
