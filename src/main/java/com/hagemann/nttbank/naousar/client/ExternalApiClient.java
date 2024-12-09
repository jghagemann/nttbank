package com.hagemann.nttbank.naousar.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ExternalApiClient {

    private WebClient webClient;

    @Value("${exchange.rates.api.key}")
    private String apiKey;

    public ExternalApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.exchangeratesapi.io/")
                .defaultHeader("content-type", MediaType.APPLICATION_JSON_VALUE).build();
    }

    public Mono<ConversaoDtoResponse> get(ConversaoDtoRequest conversaoDtoRequest) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("v1")
                        .path("/latest")
                        .queryParam("access_key", apiKey)
                        .queryParam("symbols", conversaoDtoRequest.symbols())
                        .build())
                .retrieve()
                .bodyToMono(ConversaoDtoResponse.class);
    }
}
