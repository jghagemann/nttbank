package com.hagemann.nttbank.service;

import com.hagemann.nttbank.client.ConversaoDtoRequest;
import com.hagemann.nttbank.client.ConversaoDtoResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ExternalApiService {

    Mono<ResponseEntity<ConversaoDtoResponse>> get(ConversaoDtoRequest conversaoDtoRequest);
}
