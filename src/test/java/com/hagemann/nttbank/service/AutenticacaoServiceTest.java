package com.hagemann.nttbank.service;

import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;
import com.hagemann.nttbank.infra.persistence.UsuarioRepository;
import com.hagemann.nttbank.naousar.service.AutenticacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;

class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve carregar detalhes do usuário pelo login")
    void loadUserByUsernameSuccess() {
        // Arrange
        String username = "usuario_teste";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(BigInteger.ONE);
        usuarioEntity.setLogin(username);
        usuarioEntity.setSenha("senhaCodificada");

        Mockito.when(usuarioRepository.findByLogin(Mockito.any())).thenReturn(usuarioEntity);

        // Act
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        // Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(username, userDetails.getUsername());
        Assertions.assertEquals(usuarioEntity.getSenha(), userDetails.getPassword());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByLogin(username);
    }
}