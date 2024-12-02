package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.domain.correntista.AtualizarDadosCorrentistaDto;
import com.hagemann.nttbank.domain.correntista.CorrentistaDto;
import com.hagemann.nttbank.domain.correntista.DetalheCorrentistaDto;
import com.hagemann.nttbank.domain.usuario.DetalheUsuarioDto;
import com.hagemann.nttbank.service.CorrentistaService;
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

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

class CorrentistaControllerTest {

    @InjectMocks
    CorrentistaController correntistaController;

    @Mock
    CorrentistaService correntistaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve criar um correntista")
    @Test
    void shouldCriarCorrentista() {

        DetalheCorrentistaDto detalheCorrentistaDto = new DetalheCorrentistaDto(BigInteger.ONE, "Nome", "Sobrenome", "teste@email.com", true);
        CorrentistaDto correntistaDto = new CorrentistaDto("Nome", "Sobrenome", "teste@email.com");

        URI uri = UriComponentsBuilder.fromPath("/correntistas/{id}")
                .buildAndExpand(detalheCorrentistaDto.id()).toUri();

        Mockito.when(correntistaService.cadastrar(Mockito.any())).thenReturn(detalheCorrentistaDto);

        ResponseEntity<DetalheCorrentistaDto> result = correntistaController.cadastrar(correntistaDto, UriComponentsBuilder.fromUri(uri));

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @DisplayName("Deve listar todos os correntista")
    @Test
    void shouldListarTodosCorrentistas() {

        DetalheCorrentistaDto detalheCorrentistaDto = new DetalheCorrentistaDto(BigInteger.ONE, "Nome", "Sobrenome", "teste@email.com", true);
        Page<DetalheCorrentistaDto> page = new PageImpl<>(List.of(detalheCorrentistaDto));

        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(correntistaService.listarTodos(Mockito.any())).thenReturn(page);

        ResponseEntity<Page<DetalheCorrentistaDto>> result = correntistaController.listarTodos(pageable);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getTotalElements());
        Assertions.assertEquals(detalheCorrentistaDto, result.getBody().getContent().getFirst());

    }

    @DisplayName("Deve listar um correntista específico")
    @Test
    void shouldListarUmCorrentista() {

        DetalheCorrentistaDto detalheCorrentistaDto = new DetalheCorrentistaDto(BigInteger.ONE, "Nome", "Sobrenome", "teste@email.com", true);
        Mockito.when(correntistaService.listar(Mockito.any())).thenReturn(detalheCorrentistaDto);

        ResponseEntity<DetalheCorrentistaDto> result = correntistaController.listar(BigInteger.ONE);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(detalheCorrentistaDto, result.getBody());

    }

    @DisplayName("Deve atualizar um correntista")
    @Test
    void shouldAtualizarUmaConta() {

        AtualizarDadosCorrentistaDto atualizarDadosCorrentistaDto = new AtualizarDadosCorrentistaDto(BigInteger.ONE, "Nome novo", "Sobrenome Novo", "teste.novo@email.com", true);
        DetalheCorrentistaDto detalheCorrentistaDto = new DetalheCorrentistaDto(BigInteger.ONE, "Nome novo", "Sobrenome Novo", "teste.novo@email.com", true);
        Mockito.when(correntistaService.atualizarDados(Mockito.any())).thenReturn(detalheCorrentistaDto);

        ResponseEntity<DetalheCorrentistaDto> result = correntistaController.atualizar(atualizarDadosCorrentistaDto);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(atualizarDadosCorrentistaDto.id(), result.getBody().id());
        Assertions.assertEquals(atualizarDadosCorrentistaDto.nome(), result.getBody().nome());
        Assertions.assertEquals(atualizarDadosCorrentistaDto.sobrenome(), result.getBody().sobrenome());
        Assertions.assertEquals(atualizarDadosCorrentistaDto.email(), result.getBody().email());
        Assertions.assertEquals(atualizarDadosCorrentistaDto.ativo(), result.getBody().ativo());

    }

    @DisplayName("Deve realizar desativação de uma conta")
    @Test
    void shouldExcluirUmaConta() {

        Mockito.doNothing().when(correntistaService).desativar(Mockito.any());

        ResponseEntity<Void> result = correntistaController.desativar(BigInteger.ONE);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}