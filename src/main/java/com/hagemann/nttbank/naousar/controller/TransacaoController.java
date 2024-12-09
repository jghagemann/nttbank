package com.hagemann.nttbank.naousar.controller;

import com.hagemann.nttbank.naousar.domain.transacao.AtualizarTransacaoDto;
import com.hagemann.nttbank.naousar.domain.transacao.DetalheTransacaoDto;
import com.hagemann.nttbank.naousar.domain.transacao.TransacaoDto;
import com.hagemann.nttbank.naousar.service.TransacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;

@RestController
@RequestMapping("/transacoes")
@SecurityRequirement(name = "bearer-key")
public class TransacaoController {

    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping()
    public ResponseEntity<DetalheTransacaoDto> criarTransacao(
            @RequestBody @Valid TransacaoDto transacaoDto,
            UriComponentsBuilder uriComponentsBuilder) {
        DetalheTransacaoDto detalheTransacaoDto = transacaoService.criarTransacao(transacaoDto);
        URI uri = uriComponentsBuilder.path("/transacoes/{id}").buildAndExpand(detalheTransacaoDto.id()).toUri();

        return ResponseEntity.created(uri).body(detalheTransacaoDto);
    }

    @GetMapping("/{correntistaId}/{contaId}")
    public ResponseEntity<Page<DetalheTransacaoDto>> listar(
            @PathVariable BigInteger correntistaId,
            @PathVariable BigInteger contaId,
            @PageableDefault(sort = {"dataTransacao"}) Pageable pageable) {
        Page<DetalheTransacaoDto> listaTransacoes = transacaoService.listar(correntistaId, contaId, pageable);

        return ResponseEntity.ok(listaTransacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheTransacaoDto> listarUm(@PathVariable BigInteger id) {
        DetalheTransacaoDto transacaoDto = transacaoService.listarUm(id);

        return ResponseEntity.ok(transacaoDto);
    }

    @PutMapping
    public ResponseEntity<DetalheTransacaoDto> atualizarTransacao(@RequestBody @Valid AtualizarTransacaoDto atualizarTransacaoDto) {
        DetalheTransacaoDto detalheTransacaoDto = transacaoService.atualizarTransacao(atualizarTransacaoDto);
        return ResponseEntity.ok(detalheTransacaoDto);
    }
}
