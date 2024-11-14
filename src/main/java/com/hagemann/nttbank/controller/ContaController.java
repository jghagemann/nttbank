package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.domain.conta.Conta;
import com.hagemann.nttbank.domain.conta.ContaDto;
import com.hagemann.nttbank.domain.conta.DetalheContaDto;
import com.hagemann.nttbank.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping()
    ResponseEntity<DetalheContaDto> criarConta(@RequestBody @Valid ContaDto contaDto,
        UriComponentsBuilder uriComponentsBuilder) {

        Conta conta = contaService.criarConta(contaDto);
        URI uri = uriComponentsBuilder.path("/contas/{id}").buildAndExpand(conta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalheContaDto(conta));


    }
}
