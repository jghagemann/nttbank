package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.ContaRepository;
import com.hagemann.nttbank.domain.correntista.*;
import com.hagemann.nttbank.exceptions.CorrentistaException;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

class CorrentistaServiceImplTest {

    @InjectMocks
    CorrentistaServiceImpl correntistaServiceImpl;

    @Mock
    CorrentistaService correntistaService;

    @Mock
    CorrentistaRepository correntistaRepository;

    @Mock
    ContaRepository contaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar um correntista com sucesso")
    void cadastrarCorrentistaComSucesso() {
        CorrentistaDto correntistaDto = new CorrentistaDto("Nome", "Sobrenome", "email@email.com");
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", null, true);

        Mockito.when(correntistaRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(correntistaRepository.save(Mockito.any(Correntista.class))).thenReturn(correntista);

        DetalheCorrentistaDto result = correntistaServiceImpl.cadastrar(correntistaDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Nome", result.nome());
        Assertions.assertEquals("Sobrenome", result.sobrenome());
    }

    @Test
    @DisplayName("Deve lançar erro ao cadastrar correntista com email já cadastrado")
    void cadastrarCorrentistaEmailDuplicado() {
        CorrentistaDto correntistaDto = new CorrentistaDto("Nome", "Sobrenome", "email@email.com");

        Mockito.when(correntistaRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            correntistaServiceImpl.cadastrar(correntistaDto);
        });

        Assertions.assertEquals("Email já cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve listar todos os correntistas com sucesso")
    void listarTodosCorrentistasComSucesso() {
        Correntista correntista1 = new Correntista(BigInteger.ONE, "Nome1", "Sobrenome1", "email1@email.com", null, true);
        Correntista correntista2 = new Correntista(BigInteger.TWO, "Nome2", "Sobrenome2", "email2@email.com", null, true);
        Page<Correntista> correntistaPage = new PageImpl<>(List.of(correntista1, correntista2));

        Mockito.when(correntistaRepository.findAll(Mockito.any(Pageable.class))).thenReturn(correntistaPage);

        Page<DetalheCorrentistaDto> result = correntistaServiceImpl.listarTodos(Pageable.unpaged());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("Deve lançar erro ao listar correntistas quando a lista está vazia")
    void listarTodosCorrentistasListaVazia() {
        Page<Correntista> emptyPage = Page.empty();

        Mockito.when(correntistaRepository.findAll(Mockito.any(Pageable.class))).thenReturn(emptyPage);

        CorrentistaException exception = Assertions.assertThrows(CorrentistaException.class, () -> {
            correntistaServiceImpl.listarTodos(Pageable.unpaged());
        });

        Assertions.assertEquals("A lista está vazia", exception.getMessage());
    }

    @Test
    @DisplayName("Deve listar um correntista específico")
    void listarCorrentista() {
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", null, true);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        DetalheCorrentistaDto result = correntistaServiceImpl.listar(BigInteger.ONE);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Nome", result.nome());
        Assertions.assertEquals("Sobrenome", result.sobrenome());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar listar correntista inexistente")
    void listarCorrentistaInexistente() {
        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            correntistaServiceImpl.listar(BigInteger.ONE);
        });

        Assertions.assertEquals("Correntista não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar dados de um correntista")
    void atualizarDadosCorrentista() {
        AtualizarDadosCorrentistaDto atualizarDto = new AtualizarDadosCorrentistaDto(BigInteger.ONE, "NovoNome", "NovoSobrenome", "novo@email.com", true);
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", new HashSet<>(), true);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        DetalheCorrentistaDto result = correntistaServiceImpl.atualizarDados(atualizarDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("NovoNome", result.nome());
        Assertions.assertEquals("NovoSobrenome", result.sobrenome());
        Assertions.assertEquals("novo@email.com", result.email());
    }

    @Test
    @DisplayName("Deve manter os dados do correntista iguais quando em branco")
    void atualizarDadosCorrentistaDadosIguais() {
        AtualizarDadosCorrentistaDto atualizarDto = new AtualizarDadosCorrentistaDto(BigInteger.ONE, "", "", "", null);
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", new HashSet<>(), true);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        DetalheCorrentistaDto result = correntistaServiceImpl.atualizarDados(atualizarDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Nome", result.nome());
        Assertions.assertEquals("Sobrenome", result.sobrenome());
        Assertions.assertEquals("email@email.com", result.email());
    }

    @Test
    @DisplayName("Deve desativar um correntista com sucesso")
    void desativarCorrentista() {
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", null, true);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        Assertions.assertDoesNotThrow(() -> correntistaServiceImpl.desativar(BigInteger.ONE));

        Mockito.verify(correntistaRepository, Mockito.times(1)).save(correntista);
        Assertions.assertFalse(correntista.getAtivo());
    }
}