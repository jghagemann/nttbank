package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.usuario.Usuario;
import com.hagemann.nttbank.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        Usuario usuario = new Usuario();
        usuario.setId(BigInteger.ONE);
        usuario.setLogin(username);
        usuario.setSenha("senhaCodificada");

        Mockito.when(usuarioRepository.findByLogin(Mockito.any())).thenReturn(usuario);

        // Act
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        // Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(username, userDetails.getUsername());
        Assertions.assertEquals(usuario.getSenha(), userDetails.getPassword());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByLogin(username);
    }
}