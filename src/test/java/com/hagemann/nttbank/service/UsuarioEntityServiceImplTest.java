package com.hagemann.nttbank.service;

import com.hagemann.nttbank.naousar.domain.usuario.DetalheUsuarioDto;
import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;
import com.hagemann.nttbank.naousar.domain.usuario.UsuarioDto;
import com.hagemann.nttbank.infra.persistence.UsuarioRepository;
import com.hagemann.nttbank.naousar.service.UsuarioService;
import com.hagemann.nttbank.naousar.service.UsuarioServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.util.Optional;

class UsuarioEntityServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    void criarUsuario() {

        UsuarioDto usuarioDto = new UsuarioDto("usuario_teste", "senha123");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(BigInteger.ONE);
        usuarioEntity.setLogin(usuarioDto.login());
        usuarioEntity.setSenha("senhaCodificada");

        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("senhaCodificada");
        Mockito.when(usuarioRepository.save(Mockito.any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        DetalheUsuarioDto resultado = usuarioServiceImpl.criarUsuario(usuarioDto);

        Assertions.assertNotNull(resultado);
    }

    @Test
    @DisplayName("Deve excluir um usuário com sucesso para um ID válido")
    void excluirUsuario() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(BigInteger.ONE);
        usuarioEntity.setLogin("usuario_teste");
        usuarioEntity.setSenha("senhaCodificada");

        Mockito.when(usuarioRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(usuarioEntity));

        Assertions.assertDoesNotThrow(() -> usuarioServiceImpl.excluirUsuario(BigInteger.ONE));
        Mockito.verify(usuarioRepository, Mockito.times(1)).delete(usuarioEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir um usuário inexistente")
    void excluirUsuarioInexistente() {
        Mockito.when(usuarioRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> usuarioServiceImpl.excluirUsuario(BigInteger.ONE)
        );

        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
    }
}