package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.client.ConversaoDtoRequest;
import com.hagemann.nttbank.naousar.client.ConversaoDtoResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ExternalApiService {

    Mono<ResponseEntity<ConversaoDtoResponse>> get(ConversaoDtoRequest conversaoDtoRequest);
}
