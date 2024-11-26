package com.hagemann.nttbank.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Component
public class MockApiClient {

    private WebClient webClient;

    public MockApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://6746167f512ddbd807fad04c.mockapi.io/api/v1")
                .defaultHeader("content-type", MediaType.APPLICATION_JSON_VALUE).build();
    }

    public Flux<AccountDtoResponse> getAll() {
        return this.webClient.get()
                .uri("/accounts")  // Replace with your specific API endpoint
                .retrieve()
                .bodyToFlux(AccountDtoResponse.class);
    }

    public Mono<AccountDtoResponse> getOne(BigInteger id) {
        return this.webClient.get()
                .uri("/accounts/{id}", id)  // Replace with your specific API endpoint
                .retrieve()
                .bodyToMono(AccountDtoResponse.class);
    }
}
