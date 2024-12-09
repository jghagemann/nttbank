package com.hagemann.nttbank.service;

import com.hagemann.nttbank.naousar.domain.conta.Conta;
import com.hagemann.nttbank.naousar.domain.conta.ContaRepository;
import com.hagemann.nttbank.naousar.domain.conta.TipoConta;
import com.hagemann.nttbank.naousar.domain.correntista.Correntista;
import com.hagemann.nttbank.naousar.domain.transacao.*;
import com.hagemann.nttbank.naousar.exceptions.VisualizarListaTransacaoException;
import com.hagemann.nttbank.naousar.service.TransacaoService;
import com.hagemann.nttbank.naousar.service.TransacaoServiceImpl;
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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

class TransacaoServiceImplTest {

    @InjectMocks
    private TransacaoServiceImpl transacaoServiceImpl;

    @Mock
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ContaRepository contaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar uma transação com sucesso")
    void criarTransacaoComSucesso() {

        Conta contaOrigem = new Conta(BigInteger.ONE,BigDecimal.TEN, "0001-1", new Correntista(), new HashSet<>(), TipoConta.CORRENTE);
        Conta contaDestino = new Conta(BigInteger.ONE,BigDecimal.TEN, "0001-1", new Correntista(), new HashSet<>(), TipoConta.CORRENTE);

        TransacaoDto transacaoDto = new TransacaoDto(LocalDateTime.now(), BigDecimal.ONE, contaOrigem, contaDestino, TipoTransacao.DEPOSITO, Categoria.SERVICOS);
        Transacao transacao = new Transacao(BigInteger.TEN, LocalDateTime.now(), transacaoDto.valor(), contaOrigem, contaDestino, transacaoDto.tipoTransacao(), transacaoDto.categoria());

        DetalheTransacaoDto detalheTransacaoDto = new DetalheTransacaoDto(BigInteger.ONE,
                BigDecimal.TEN, LocalDateTime.now(), BigInteger.TWO, BigInteger.ONE, TipoTransacao.DEPOSITO, Categoria.MORADIA);

        Mockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
        Mockito.when(transacaoService.criarTransacao(Mockito.any())).thenReturn(detalheTransacaoDto);

        DetalheTransacaoDto result = transacaoServiceImpl.criarTransacao(transacaoDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(BigDecimal.ONE, result.valor());
        Assertions.assertEquals(TipoTransacao.DEPOSITO, result.tipoTransacao());
        Assertions.assertEquals(Categoria.SERVICOS, result.categoria());
    }

    @Test
    @DisplayName("Deve listar transações de uma conta com sucesso")
    void listarTransacoesComSucesso() {

        Correntista correntista = new Correntista(BigInteger.ONE, "Nome", "Sobrenome", "email@email.com", new HashSet<>(), true);
        Conta contaOrigem = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        Conta contaDestino = new Conta(BigInteger.TWO, BigDecimal.valueOf(20), "0002-1", correntista, new HashSet<>(), TipoConta.POUPANCA);

        Conta conta = new Conta(BigInteger.TWO, BigDecimal.TEN, "00001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        Transacao transacao1 = new Transacao(BigInteger.ONE, LocalDateTime.now(), BigDecimal.TEN, contaOrigem, contaDestino, TipoTransacao.TRANSFERENCIA, Categoria.MOVIMENTACAO);
        Transacao transacao2 = new Transacao(BigInteger.TWO, LocalDateTime.now(), BigDecimal.TWO, contaOrigem, contaDestino, TipoTransacao.DEPOSITO, Categoria.EDUCACAO);

        Page<Transacao> transacaoPage = new PageImpl<>(List.of(transacao1, transacao2));

        DetalheTransacaoDto detalheTransacaoDto = new DetalheTransacaoDto(BigInteger.ONE,
                BigDecimal.TEN, LocalDateTime.now(), BigInteger.TWO, BigInteger.ONE, TipoTransacao.DEPOSITO, Categoria.MORADIA);

        Page<DetalheTransacaoDto> detalheTransacaoDtoPage = new PageImpl<>(List.of(detalheTransacaoDto));

        Mockito.when(contaRepository.getReferenceById(BigInteger.TWO)).thenReturn(conta);
        Mockito.when(transacaoRepository.findAllByContaOrigemCorrentistaIdAndContaDestinoCorrentistaId(Mockito.any(BigInteger.class),
                        Mockito.any(BigInteger.class), Mockito.any(Pageable.class)))
                .thenReturn(transacaoPage);

        Mockito.when(transacaoService.listar(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(detalheTransacaoDtoPage);

        Mockito.when(transacaoService.listar(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(detalheTransacaoDtoPage);

        Page<DetalheTransacaoDto> result = transacaoServiceImpl.listar(BigInteger.ONE, BigInteger.TWO, Pageable.unpaged());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("Deve lançar erro ao listar transações de correntista não autorizado")
    void listarTransacoesNaoAutorizado() {
        Conta conta = new Conta(BigInteger.TWO, BigDecimal.TEN, "00001-1", new Correntista(), new HashSet<>(), TipoConta.CORRENTE);

        Mockito.when(contaRepository.getReferenceById(BigInteger.TWO)).thenReturn(conta);

        VisualizarListaTransacaoException exception = Assertions.assertThrows(VisualizarListaTransacaoException.class, () -> {
            transacaoServiceImpl.listar(BigInteger.ONE, BigInteger.TWO, Pageable.unpaged());
        });

        Assertions.assertEquals("Correntista não autorizado a visualizar transações dessa conta", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar detalhes de uma transação para um ID válido")
    void listarUmaTransacaoComSucesso() {
        // Arrange
        Conta contaOrigem = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", null, new HashSet<>(), TipoConta.CORRENTE);
        Conta contaDestino = new Conta(BigInteger.TWO, BigDecimal.valueOf(20), "0002-1", null, new HashSet<>(), TipoConta.POUPANCA);
        Transacao transacao = new Transacao(BigInteger.ONE, LocalDateTime.now(), BigDecimal.valueOf(100), contaOrigem, contaDestino, TipoTransacao.TRANSFERENCIA, Categoria.MOVIMENTACAO);

        Mockito.when(transacaoRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(transacao));

        // Act
        DetalheTransacaoDto resultado = transacaoServiceImpl.listarUm(BigInteger.ONE);

        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(transacao.getId(), resultado.id());
        Assertions.assertEquals(transacao.getValor(), resultado.valor());
        Assertions.assertEquals(transacao.getTipoTransacao(), resultado.tipoTransacao());
        Assertions.assertEquals(transacao.getCategoria(), resultado.categoria());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar listar uma transação inexistente")
    void listarUmaTransacaoNaoEncontrada() {
        // Arrange
        Mockito.when(transacaoRepository.findById(Mockito.any(BigInteger.class)))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class, () -> transacaoServiceImpl.listarUm(BigInteger.ONE)
        );

        Assertions.assertEquals("Transação não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar a categoria de uma transação com sucesso")
    void atualizarTransacaoComSucesso() {
        AtualizarTransacaoDto atualizarTransacaoDto = new AtualizarTransacaoDto(BigInteger.ONE, Categoria.MORADIA);
        Conta contaOrigem = new Conta(BigInteger.ONE,BigDecimal.TEN, "0001-1", new Correntista(), new HashSet<>(), TipoConta.CORRENTE);
        Conta contaDestino = new Conta(BigInteger.ONE,BigDecimal.TEN, "0001-1", new Correntista(), new HashSet<>(), TipoConta.CORRENTE);
        Transacao transacao = new Transacao(BigInteger.ONE, LocalDateTime.now(), BigDecimal.TEN, contaOrigem, contaDestino, TipoTransacao.TRANSFERENCIA, Categoria.LAZER);

        DetalheTransacaoDto detalheTransacaoDto = new DetalheTransacaoDto(BigInteger.ONE,
                BigDecimal.TEN, LocalDateTime.now(), BigInteger.TWO, BigInteger.ONE, TipoTransacao.DEPOSITO, Categoria.MORADIA);

        Mockito.when(transacaoRepository.findById(BigInteger.ONE)).thenReturn(Optional.of(transacao));
        Mockito.when(transacaoService.atualizarTransacao(Mockito.any())).thenReturn(detalheTransacaoDto);

        DetalheTransacaoDto result = transacaoServiceImpl.atualizarTransacao(atualizarTransacaoDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Categoria.MORADIA, result.categoria());
    }

    @Test
    @DisplayName("Deve lançar erro ao atualizar transação inexistente")
    void atualizarTransacaoInexistente() {
        AtualizarTransacaoDto atualizarTransacaoDto = new AtualizarTransacaoDto(BigInteger.ONE, Categoria.MORADIA);

        Mockito.when(transacaoRepository.findById(BigInteger.ONE)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            transacaoServiceImpl.atualizarTransacao(atualizarTransacaoDto);
        });

        Assertions.assertEquals("Transação não encontrada", exception.getMessage());
    }
}