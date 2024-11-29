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
        // Arrange
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Nome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);

        ContaDto contaDto = new ContaDto(BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);

        Mockito.when(correntistaRepository.getReferenceById(Mockito.any())).thenReturn(correntista);
        Mockito.when(contaRepository.save(Mockito.any())).thenReturn(conta);

        DetalheContaDto result = contaServiceImpl.criarConta(contaDto);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TipoConta.CORRENTE, result.tipoConta());
    }

    @Test
    @DisplayName("Deve listar contas com sucesso")
    void listarContas() {
        // Arrange
        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", new HashSet<>(), true);
        Conta conta1 = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        Conta conta2 = new Conta(BigInteger.TWO, BigDecimal.valueOf(100), "0002-1", correntista, new HashSet<>(), TipoConta.POUPANCA);

        Page<Conta> contasPage = new PageImpl<>(List.of(conta1, conta2));

        Mockito.when(contaRepository.findAllByCorrentistaId(Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(contasPage);

        Page<DetalheContaDto> result = contaServiceImpl.listarContas(BigInteger.ONE, Pageable.unpaged());

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("0001-1", result.getContent().get(0).numero());
        Assertions.assertEquals("0002-1", result.getContent().get(1).numero());
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
    @DisplayName("Deve lançar exceção ao excluir conta inexistente")
    void excluirContaInexistente() {
        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.excluir(BigInteger.ONE);
        });

        Assertions.assertEquals("Conta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar criar conta para correntista inexistente")
    void criarContaCorrentistaInexistente() {
        ContaDto contaDto = new ContaDto(BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);
        Mockito.when(correntistaRepository.getReferenceById(Mockito.any(BigInteger.class)))
                .thenThrow(new EntityNotFoundException("Correntista não encontrado"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.criarConta(contaDto);
        });

        Assertions.assertEquals("Correntista não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar listar uma conta inexistente")
    void listarContaInexistente() {
        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class)))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.listar(BigInteger.ONE);
        });

        Assertions.assertEquals("Conta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar atualizar dados de uma conta inexistente")
    void atualizarDadosContaInexistente() {
        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");

        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class)))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.atualizarDadosConta(atualizarDadosContaDto);
        });

        Assertions.assertEquals("Conta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar atualizar conta para um número duplicado")
    void atualizarDadosContaNumeroDuplicado() {
        // Arrange
        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");

        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", new HashSet<>(), true);
        Conta conta = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);

        Mockito.when(contaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(conta));
        Mockito.when(contaRepository.existsByNumero(Mockito.any(String.class))).thenReturn(true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contaServiceImpl.atualizarDadosConta(atualizarDadosContaDto);
        });

        Assertions.assertEquals("Esse número de conta já existe", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar listar contas e receber página nula")
    void listarContasResultadoNulo() {
        // Arrange
        Mockito.when(contaRepository.findAllByCorrentistaId(Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(null);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.listarContas(BigInteger.ONE, Pageable.unpaged());
        });

        Assertions.assertEquals("Não foram encontradas contas", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar criar uma conta sem um correntista válido")
    void criarContaSemCorrentistaValido() {
        // Arrange
        ContaDto contaDto = new ContaDto(BigDecimal.TEN, "0001-1", BigInteger.ONE, TipoConta.CORRENTE);

        Mockito.when(correntistaRepository.getReferenceById(Mockito.any()))
                .thenThrow(new EntityNotFoundException("Correntista não encontrado"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.criarConta(contaDto);
        });

        Assertions.assertEquals("Correntista não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando o repositório retorna null ao listar contas")
    void listarContasRetornaNulo() {
        // Arrange
        Mockito.when(contaRepository.findAllByCorrentistaId(Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(null);


        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.listarContas(BigInteger.ONE, Pageable.unpaged());
        });

        Assertions.assertEquals("Não foram encontradas contas", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar página vazia ao listar contas quando não há contas disponíveis")
    void listarContasPaginaVazia() {
        // Arrange
        Page<Conta> contasPage = Page.empty();
        Mockito.when(contaRepository.findAllByCorrentistaId(Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(contasPage);


        Page<DetalheContaDto> result = contaServiceImpl.listarContas(BigInteger.ONE, Pageable.unpaged());

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(0, result.getContent().size());
    }
    @Test
    @DisplayName("Deve atualizar uma conta com sucesso quando todos os dados estão corretos")
    void atualizarContaComSucesso() {

        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Nome", "email@email.com", new HashSet<>(), true);
        Conta contaExistente = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");
        Conta contaAtualizada = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-2", correntista, new HashSet<>(), TipoConta.CORRENTE);

        Mockito.when(contaRepository.findById(Mockito.any())).thenReturn(Optional.of(contaExistente));
        Mockito.when(contaRepository.existsByNumero(Mockito.any())).thenReturn(false);
        Mockito.when(contaRepository.save(Mockito.any(Conta.class))).thenReturn(contaAtualizada);


        DetalheContaDto result = contaServiceImpl.atualizarDadosConta(atualizarDadosContaDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("0001-2", result.numero());
        Mockito.verify(contaRepository, Mockito.times(1)).save(Mockito.any(Conta.class));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar atualizar uma conta inexistente")
    void atualizarContaInexistente() {

        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");

        Mockito.when(contaRepository.findById(BigInteger.ONE)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            contaServiceImpl.atualizarDadosConta(atualizarDadosContaDto);
        });

        Assertions.assertEquals("Conta não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar atualizar uma conta com número duplicado")
    void atualizarContaComNumeroDuplicado() {
        // Arrange
        AtualizarDadosContaDto atualizarDadosContaDto = new AtualizarDadosContaDto(BigInteger.ONE, "0001-2");
        Conta contaExistente = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", null, new HashSet<>(), TipoConta.CORRENTE);

        Mockito.when(contaRepository.findById(BigInteger.ONE)).thenReturn(Optional.of(contaExistente));
        Mockito.when(contaRepository.existsByNumero("0001-2")).thenReturn(true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contaServiceImpl.atualizarDadosConta(atualizarDadosContaDto);
        });

        Assertions.assertEquals("Esse número de conta já existe", exception.getMessage());
    }

}