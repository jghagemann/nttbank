package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.client.ConversaoDtoRequest;
import com.hagemann.nttbank.naousar.client.ConversaoDtoResponse;
import com.hagemann.nttbank.naousar.client.ExternalApiClient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    private ExternalApiClient externalApiClient;

    public ExternalApiServiceImpl(ExternalApiClient externalApiClient) {
        this.externalApiClient = externalApiClient;
    }

    @Override
    public Mono<ResponseEntity<ConversaoDtoResponse>> get(@Valid ConversaoDtoRequest conversaoDtoRequest) {
        return externalApiClient.get(conversaoDtoRequest).map(ResponseEntity::ok);
    }
}
