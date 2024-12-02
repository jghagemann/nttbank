package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.domain.conta.AtualizarDadosContaDto;
import com.hagemann.nttbank.domain.conta.ContaDto;
import com.hagemann.nttbank.domain.conta.DetalheContaDto;
import com.hagemann.nttbank.domain.conta.TipoConta;
import com.hagemann.nttbank.service.ContaService;
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
import java.util.List;

class ContaControllerTest {

    @InjectMocks
    ContaController contaController;

    @Mock
    ContaService contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve criar uma nova conta")
    @Test
    void shouldCriarConta() {
        DetalheContaDto  detalheContaDto = new DetalheContaDto(BigInteger.ONE, BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);
        ContaDto contaDto = new ContaDto(BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);

        URI uri = UriComponentsBuilder.fromPath("/contas/{id}")
                .buildAndExpand(detalheContaDto.id()).toUri();

        Mockito.when(contaService.criarConta(Mockito.any())).thenReturn(detalheContaDto);

        ResponseEntity<DetalheContaDto> result = contaController.criarConta(contaDto, UriComponentsBuilder.fromUri(uri));

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @DisplayName("Deve listar todas as contas de um correntista")
    @Test
    void shouldListarTodasContas() {

        DetalheContaDto  detalheContaDto = new DetalheContaDto(BigInteger.ONE, BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);
        Page<DetalheContaDto> page = new PageImpl<>(List.of(detalheContaDto));

        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(contaService.listarContas(Mockito.any(), Mockito.any())).thenReturn(page);

        ResponseEntity<Page<DetalheContaDto>> result = contaController.listarContas(BigInteger.ONE, pageable);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
        Assertions.assertEquals(detalheContaDto, result.getBody().getContent().getFirst());

    }

    @DisplayName("Deve listar uma conta específica de um correntista")
    @Test
    void shouldListarUmaConta() {

        DetalheContaDto  detalheContaDto = new DetalheContaDto(BigInteger.ONE, BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);
        Mockito.when(contaService.listar(Mockito.any())).thenReturn(detalheContaDto);

        ResponseEntity<DetalheContaDto> result = contaController.listar(BigInteger.ONE);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(detalheContaDto, result.getBody());

    }

    @DisplayName("Deve atualizar uma conta")
    @Test
    void shouldAtualizarUmaConta() {

        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");
        DetalheContaDto  detalheContaDto = new DetalheContaDto(BigInteger.ONE, BigDecimal.TEN, "0001-2", BigInteger.ONE, TipoConta.CORRENTE);
        Mockito.when(contaService.atualizarDadosConta(Mockito.any())).thenReturn(detalheContaDto);

        ResponseEntity<DetalheContaDto> result = contaController.atualizarDadosConta(atualizarDadosContaDto);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(atualizarDadosContaDto.id(), result.getBody().id());
        Assertions.assertEquals(atualizarDadosContaDto.numero(), result.getBody().numero());
    }

    @DisplayName("Deve realizar exclusão de uma conta")
    @Test
    void shouldExcluirUmaConta() {

        Mockito.doNothing().when(contaService).excluir(Mockito.any());

        ResponseEntity<Void> result = contaController.excluir(BigInteger.ONE);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}