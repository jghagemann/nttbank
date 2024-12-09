package com.hagemann.nttbank.naousar.controller;

import com.hagemann.nttbank.naousar.client.ConversaoDtoRequest;
import com.hagemann.nttbank.naousar.client.ConversaoDtoResponse;
import com.hagemann.nttbank.naousar.service.ExternalApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("converter")
public class ExternalApiController {

    private ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping()
    public Mono<ResponseEntity<ConversaoDtoResponse>> converter(@RequestBody ConversaoDtoRequest conversaoDtoRequest) {
        return externalApiService.get(conversaoDtoRequest);

    }
}
