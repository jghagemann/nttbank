package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.naousar.controller.UsuarioAdminController;
import com.hagemann.nttbank.naousar.domain.usuario.DetalheUsuarioDto;
import com.hagemann.nttbank.naousar.domain.usuario.UsuarioDto;
import com.hagemann.nttbank.naousar.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
class UsuarioEntityAdminControllerTest {

    @InjectMocks
    UsuarioAdminController usuarioAdminController;

    @Mock
    UsuarioService usuarioService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @DisplayName("Deve criar usuário admin")
    @Test
    void criarUsuario() {

        DetalheUsuarioDto detalheUsuarioDto = new DetalheUsuarioDto(BigInteger.ONE, "teste@teste.com");
        UsuarioDto usuarioDto = new UsuarioDto("teste@teste.com", "S3nhH@");

        URI uri = UriComponentsBuilder.fromPath("/administrador/{id}")
                .buildAndExpand(detalheUsuarioDto.id()).toUri();

        Mockito.when(usuarioService.criarUsuario(Mockito.any())).thenReturn(detalheUsuarioDto);

        ResponseEntity<DetalheUsuarioDto> result = usuarioAdminController.criarUsuario(usuarioDto, UriComponentsBuilder.fromUri(uri));

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @DisplayName("Deve excluir usuário admin")
    @Test
    void excluirUsuario() {
        Mockito.doNothing().when(usuarioService).excluirUsuario(Mockito.any());

        ResponseEntity<Void> result = usuarioAdminController.excluirUsuario(BigInteger.ONE);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}