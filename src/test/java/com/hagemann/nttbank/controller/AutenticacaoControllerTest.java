package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.naousar.controller.AutenticacaoController;
import com.hagemann.nttbank.naousar.domain.usuario.AutenticacaoDto;
import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;
import com.hagemann.nttbank.infra.persistence.security.DadosTokenJwtDto;
import com.hagemann.nttbank.config.TokenService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.math.BigInteger;

class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController autenticacaoController;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve gerar um token de autenticação")
    @Test
    void shouldGenerateAuthenticationToken() {

        UsuarioEntity usuarioEntity = new UsuarioEntity(); // Mocked domain user
        usuarioEntity.setId(BigInteger.ONE);
        AutenticacaoDto autenticacaoDto = new AutenticacaoDto("teste@teste.com", "123456");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(usuarioEntity);

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(tokenService.gerarToken(Mockito.any())).thenReturn("eYOKlkjdsaDFADJSDKs");

        ResponseEntity<DadosTokenJwtDto> result = autenticacaoController.login(autenticacaoDto);

        Assertions.assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
    }
}