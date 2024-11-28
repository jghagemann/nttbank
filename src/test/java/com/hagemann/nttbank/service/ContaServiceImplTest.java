package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.*;
import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

class ContaServiceImplTest {

    @InjectMocks
    ContaServiceImpl contaServiceImpl;

    @Mock
    ContaService contaService;

    @Mock
    ContaRepository contaRepository;

    @Mock
    CorrentistaRepository correntistaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar uma conta corrente")
    void criarContaCorrente() {
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Nome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);

        ContaDto contaDto = new ContaDto(BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);

        Mockito.when(correntistaRepository.getReferenceById(Mockito.any())).thenReturn(correntista);
        Mockito.when(contaService.criarConta(Mockito.any())).thenReturn(new DetalheContaDto(conta));

        DetalheContaDto result = contaServiceImpl.criarConta(contaDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TipoConta.CORRENTE, result.tipoConta());
    }

    @Test
    @DisplayName("Deve criar uma conta poupança")
    void criarContaPoupanca() {
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Nome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.POUPANCA);

        ContaDto contaDto = new ContaDto(BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.POUPANCA);

        Mockito.when(correntistaRepository.getReferenceById(Mockito.any())).thenReturn(correntista);
        Mockito.when(contaService.criarConta(Mockito.any())).thenReturn(new DetalheContaDto(conta));

        DetalheContaDto result = contaServiceImpl.criarConta(contaDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TipoConta.POUPANCA, result.tipoConta());
    }

    @Test
    @DisplayName("Deve listar contas com sucesso para um ID válido")
    void listarContasComSucesso() {
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        Conta conta2 = new Conta(BigInteger.TWO, BigDecimal.valueOf(100), "0002-1", correntista, new HashSet<>(), TipoConta.POUPANCA);

        Page<Conta> contasPage = new PageImpl<>(List.of(conta, conta2));

        Mockito.when(contaRepository.findAllByCorrentistaId(Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(contasPage);

        Page<DetalheContaDto> result = contaServiceImpl.listarContas(BigInteger.ONE, Pageable.unpaged());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("0001-1", result.getContent().get(0).numero());
        Assertions.assertEquals("0002-1", result.getContent().get(1).numero());
    }

    @Test
    @DisplayName("Deve lançar erro ao listar contas quando o resultado for nulo")
    void listarContasResultadoNulo() {
        // Arrange
        Mockito.when(contaRepository.findAllByCorrentistaId(Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(null);

        // Act & Assert
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.listarContas(BigInteger.ONE, Pageable.unpaged());
        });

        Assertions.assertEquals("Não foram encontradas contas", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar página vazia ao listar contas quando não há contas")
    void listarContasPaginaVazia() {
        // Arrange
        Page<Conta> contasPage = Page.empty();
        Mockito.when(contaRepository.findAllByCorrentistaId(Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(contasPage);

        // Act
        Page<DetalheContaDto> result = contaServiceImpl.listarContas(BigInteger.ONE, Pageable.unpaged());

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(0, result.getContent().size());
    }

    @Test
    @DisplayName("Deve listar uma conta específica")
    void listarConta() {
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Nome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);

        DetalheContaDto detalheContaDto = new DetalheContaDto(conta);
        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(conta));
        Mockito.when(contaService.listar(Mockito.any())).thenReturn(detalheContaDto);

        DetalheContaDto result = contaServiceImpl.listar(BigInteger.ONE);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("0001-1", result.numero());
        Assertions.assertEquals(BigDecimal.TEN, result.saldo());
    }

    @Test
    @DisplayName("Deve atualizar os dados de uma conta")
    void atualizarDadosConta() {

        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Nome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");
        Conta contaAtualizada = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-2", correntista, new HashSet<>(), TipoConta.CORRENTE);
        DetalheContaDto detalheContaDto = new DetalheContaDto(contaAtualizada);


        Mockito.when(contaService.atualizarDadosConta(Mockito.any())).thenReturn(detalheContaDto);
        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(conta));
        Mockito.when(contaRepository.existsByNumero(Mockito.any())).thenReturn(false);

        DetalheContaDto result = contaServiceImpl.atualizarDadosConta(atualizarDadosContaDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("0001-2", result.numero());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar atualizar para um número de conta já existente")
    void atualizarDadosContaComNumeroDuplicado() {
        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Nome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-2", correntista, new HashSet<>(), TipoConta.CORRENTE);

        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(conta));
        Mockito.when(contaRepository.existsByNumero("0001-2")).thenReturn(true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contaServiceImpl.atualizarDadosConta(atualizarDadosContaDto);
        });

        Assertions.assertEquals("Esse número de conta já existe", exception.getMessage());
    }

    @Test
    @DisplayName("Deve excluir uma conta com sucesso")
    void excluirConta() {
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", null, new HashSet<>(), TipoConta.CORRENTE);

        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(conta));

        Assertions.assertDoesNotThrow(() -> contaServiceImpl.excluir(BigInteger.ONE));

        Mockito.verify(contaRepository, Mockito.times(1)).delete(conta);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar excluir uma conta inexistente")
    void excluirContaInexistente() {
        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.excluir(BigInteger.ONE);
        });

        Assertions.assertEquals("Conta não encontrada", exception.getMessage());
    }

}