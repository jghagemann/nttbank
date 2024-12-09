package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.naousar.controller.TransacaoController;
import com.hagemann.nttbank.naousar.domain.conta.Conta;
import com.hagemann.nttbank.naousar.domain.transacao.*;
import com.hagemann.nttbank.naousar.service.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

class TransacaoControllerTest {

    @InjectMocks
    TransacaoController transacaoController;

    @Mock
    TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve criar uma Transação")
    @Test
    void shouldCriarTransacao() {
        Conta contaOrigem = new Conta();
        contaOrigem.setId(BigInteger.ONE);
        Conta contaDestino = new Conta();
        contaDestino.setId(BigInteger.TWO);

        DetalheTransacaoDto detalheTransacaoDto = new DetalheTransacaoDto(BigInteger.ONE, BigDecimal.TEN,
                LocalDateTime.now(), BigInteger.ONE, BigInteger.TWO, TipoTransacao.DEPOSITO, Categoria.LAZER);

        TransacaoDto transacaoDto = new TransacaoDto(LocalDateTime.now(), BigDecimal.TEN, contaOrigem, contaDestino,
                TipoTransacao.DEPOSITO, Categoria.LAZER);

        URI uri = UriComponentsBuilder.fromPath("/correntistas/{id}")
                .buildAndExpand(detalheTransacaoDto.id()).toUri();

        Mockito.when(transacaoService.criarTransacao(Mockito.any())).thenReturn(detalheTransacaoDto);

        ResponseEntity<DetalheTransacaoDto> result = transacaoController.criarTransacao(transacaoDto,
                UriComponentsBuilder.fromUri(uri));

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @DisplayName("Deve listar todas as transações")
    @Test
    void shouldListarTodasTransacoes() {

        DetalheTransacaoDto detalheTransacaoDto = new DetalheTransacaoDto(BigInteger.ONE, BigDecimal.TEN,
                LocalDateTime.now(), BigInteger.ONE, BigInteger.TWO, TipoTransacao.DEPOSITO, Categoria.LAZER);

        Page<DetalheTransacaoDto> page = new PageImpl<>(List.of(detalheTransacaoDto));

        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(transacaoService.listar(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

        ResponseEntity<Page<DetalheTransacaoDto>> result = transacaoController.listar(BigInteger.ONE, BigInteger.ONE, pageable);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
        Assertions.assertEquals(detalheTransacaoDto, result.getBody().getContent().getFirst());

    }

    @DisplayName("Deve listar uma trasação específica")
    @Test
    void shouldListarUmaTransacao() {

        DetalheTransacaoDto detalheTransacaoDto = new DetalheTransacaoDto(BigInteger.ONE, BigDecimal.TEN,
                LocalDateTime.now(), BigInteger.ONE, BigInteger.TWO, TipoTransacao.DEPOSITO, Categoria.LAZER);

        Mockito.when(transacaoService.listarUm(Mockito.any()))
                .thenReturn(detalheTransacaoDto);

        ResponseEntity<DetalheTransacaoDto> result = transacaoController.listarUm(BigInteger.ONE);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(detalheTransacaoDto, result.getBody());

    }

    @DisplayName("Deve atualizar uma transação")
    @Test
    void shouldAtualizarUmaConta() {

        AtualizarTransacaoDto atualizarTransacaoDto = new AtualizarTransacaoDto(BigInteger.ONE, Categoria.LAZER);

        DetalheTransacaoDto detalheTransacaoDto = new DetalheTransacaoDto(BigInteger.ONE, BigDecimal.TEN,
                LocalDateTime.now(), BigInteger.ONE, BigInteger.TWO, TipoTransacao.DEPOSITO, Categoria.LAZER);

        Mockito.when(transacaoService.atualizarTransacao(Mockito.any())).thenReturn(detalheTransacaoDto);

        ResponseEntity<DetalheTransacaoDto> result = transacaoController.atualizarTransacao(atualizarTransacaoDto);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(atualizarTransacaoDto.id(), result.getBody().id());
        Assertions.assertEquals(atualizarTransacaoDto.categoria(), result.getBody().categoria());

    }
}