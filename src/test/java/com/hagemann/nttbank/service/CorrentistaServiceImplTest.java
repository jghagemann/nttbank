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
import java.util.List;
import java.util.Optional;

class CorrentistaServiceImplTest {

    @InjectMocks
    private CorrentistaServiceImpl correntistaServiceImpl;

    @Mock
    private CorrentistaRepository correntistaRepository;

    @Mock
    private ContaRepository contaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar novo correntista")
    void shouldRegisterNewCorrentista() {
        CorrentistaDto dto = new CorrentistaDto("John", "Doe", "john.doe@example.com");
        Correntista savedCorrentista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);

        Mockito.when(correntistaRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(correntistaRepository.save(Mockito.any(Correntista.class))).thenReturn(savedCorrentista);

        DetalheCorrentistaDto result = correntistaServiceImpl.cadastrar(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.nome());
        Assertions.assertEquals("Doe", result.sobrenome());
        Mockito.verify(correntistaRepository, Mockito.times(1)).save(Mockito.any(Correntista.class));
    }

    @Test
    @DisplayName("Deve jogar exceção quando há correntista com e-mail já cadastrado")
    void shouldThrowErrorForDuplicateEmail() {
        CorrentistaDto dto = new CorrentistaDto("John", "Doe", "john.doe@example.com");

        Mockito.when(correntistaRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> correntistaServiceImpl.cadastrar(dto));

        Assertions.assertEquals("Email já cadastrado", exception.getMessage());
        Mockito.verify(correntistaRepository, Mockito.never()).save(Mockito.any(Correntista.class));
    }

    @Test
    @DisplayName("Deve listar todos os correntistas")
    void shouldListAllCorrentistas() {
        Correntista correntista1 = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);
        Correntista correntista2 = new Correntista(BigInteger.TWO, "Jane", "Doe", "jane.doe@example.com", null, true);
        Page<Correntista> correntistaPage = new PageImpl<>(List.of(correntista1, correntista2));

        Mockito.when(correntistaRepository.findAll(Mockito.any(Pageable.class))).thenReturn(correntistaPage);

        Page<DetalheCorrentistaDto> result = correntistaServiceImpl.listarTodos(Pageable.unpaged());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
        Mockito.verify(correntistaRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Deve jogar exceção quando lista estiver vazia")
    void shouldThrowErrorForEmptyCorrentistaList() {
        Mockito.when(correntistaRepository.findAll(Mockito.any(Pageable.class))).thenReturn(Page.empty());

        CorrentistaException exception = Assertions.assertThrows(CorrentistaException.class, () -> correntistaServiceImpl.listarTodos(Pageable.unpaged()));

        Assertions.assertEquals("A lista está vazia", exception.getMessage());
        Mockito.verify(correntistaRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Deve listar um correntista específico")
    void shouldListSpecificCorrentista() {
        Correntista correntista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        DetalheCorrentistaDto result = correntistaServiceImpl.listar(BigInteger.ONE);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.nome());
        Assertions.assertEquals("Doe", result.sobrenome());
        Mockito.verify(correntistaRepository, Mockito.times(1)).findById(Mockito.any(BigInteger.class));
    }

    @Test
    @DisplayName("Deve jogar exceção quando não encontrar um correntista")
    void shouldThrowErrorForNonExistentCorrentista() {
        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> correntistaServiceImpl.listar(BigInteger.ONE));

        Assertions.assertEquals("Correntista não encontrado", exception.getMessage());
        Mockito.verify(correntistaRepository, Mockito.times(1)).findById(Mockito.any(BigInteger.class));
    }

    @Test
    @DisplayName("Deve atualizar corretamente os dados do correntista")
    void shouldUpdateCorrentistaData() {
        AtualizarDadosCorrentistaDto dto = new AtualizarDadosCorrentistaDto(BigInteger.ONE, "John", "Smith", "john.smith@example.com", false);
        Correntista correntista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        DetalheCorrentistaDto result = correntistaServiceImpl.atualizarDados(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.nome());
        Assertions.assertEquals("Smith", result.sobrenome());
        Assertions.assertEquals("john.smith@example.com", result.email());
        Assertions.assertFalse(result.ativo());
        Mockito.verify(correntistaRepository, Mockito.never()).save(Mockito.any(Correntista.class));
    }

    @Test
    @DisplayName("Deve retornar os dados do correntista iguais quando não forem passados dados")
    void shouldUpdateCorrentistaNullData() {
        AtualizarDadosCorrentistaDto dto = new AtualizarDadosCorrentistaDto(BigInteger.ONE, "", "", "", null);
        Correntista correntista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, null);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        DetalheCorrentistaDto result = correntistaServiceImpl.atualizarDados(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("John", result.nome());
        Assertions.assertEquals("Doe", result.sobrenome());
        Assertions.assertEquals("john.doe@example.com", result.email());
        Assertions.assertNull(result.ativo());
        Mockito.verify(correntistaRepository, Mockito.never()).save(Mockito.any(Correntista.class));
    }

    @Test
    @DisplayName("Deve realizar a exclusão lógica do correntista")
    void shouldDeactivateCorrentista() {
        Correntista correntista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));

        Assertions.assertDoesNotThrow(() -> correntistaServiceImpl.desativar(BigInteger.ONE));

        Assertions.assertFalse(correntista.getAtivo());
        Mockito.verify(correntistaRepository, Mockito.times(1)).save(Mockito.any(Correntista.class));
    }
}