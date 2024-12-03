package com.hagemann.nttbank.service;

import com.hagemann.nttbank.client.ConversaoDtoRequest;
import com.hagemann.nttbank.client.ConversaoDtoResponse;
import com.hagemann.nttbank.client.ExternalApiClient;
import com.hagemann.nttbank.domain.conta.Conta;
import com.hagemann.nttbank.domain.conta.TipoConta;
import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import com.hagemann.nttbank.domain.transacao.Categoria;
import com.hagemann.nttbank.domain.transacao.TipoTransacao;
import com.hagemann.nttbank.domain.transacao.Transacao;
import com.hagemann.nttbank.domain.transacao.TransacaoRepository;
import com.hagemann.nttbank.exceptions.ArquivoException;
import com.hagemann.nttbank.exceptions.ArquivoUploadException;
import com.hagemann.nttbank.helper.ExcelHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArquivoServiceImplTest {

    @InjectMocks
    private ArquivoServiceImpl arquivoServiceImpl;

    @Mock
    private CorrentistaRepository correntistaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ExternalApiClient externalApiClient;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Deve jogar ArquivoException quando upload Excel falha")
    void shouldThrowArquivoExceptionWhenUploadingExcelFails() throws Exception {
        Mockito.when(multipartFile.getInputStream()).thenThrow(new IOException("Error"));

        ArquivoUploadException exception = assertThrows(ArquivoUploadException.class, () -> arquivoServiceImpl.uploadExcel(multipartFile));

        assertEquals("Falha ao persistir Correntistas no Banco de Dados", exception.getMessage());
    }

    @Test
    @DisplayName("Deve gerar um PDF com transações")
    void shouldGeneratePdfWithTransactionsSuccessfully() {
        Correntista correntista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);

        Conta conta1 = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        Conta conta2 = new Conta(BigInteger.TWO, BigDecimal.valueOf(100), "0002-1", correntista, new HashSet<>(), TipoConta.POUPANCA);
        Transacao transacao = new Transacao(BigInteger.ONE, LocalDateTime.now(), BigDecimal.valueOf(100), conta1, conta2, TipoTransacao.DEPOSITO, Categoria.MORADIA);
        List<Transacao> transacoes = List.of(transacao);

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));
        Mockito.when(transacaoRepository.findAllByContaOrigemId(Mockito.any(BigInteger.class))).thenReturn(transacoes);

        ByteArrayOutputStream result = arquivoServiceImpl.gerarPdfTransacoes(BigInteger.ONE);

        assertNotNull(result);
        Mockito.verify(correntistaRepository, Mockito.times(1)).findById(Mockito.any(BigInteger.class));
        Mockito.verify(transacaoRepository, Mockito.times(1)).findAllByContaOrigemId(Mockito.any(BigInteger.class));
    }

    @Test
    @DisplayName("Deve gerar um PDF com conversão de moedas")
    void shouldGeneratePdfWithTransactionsAndExchangeRateSuccessfully() {
        Correntista correntista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);
        Conta conta1 = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        Conta conta2 = new Conta(BigInteger.TWO, BigDecimal.valueOf(100), "0002-1", correntista, new HashSet<>(), TipoConta.POUPANCA);
        Transacao transacao = new Transacao(BigInteger.ONE, LocalDateTime.now(), BigDecimal.valueOf(100), conta1, conta2, TipoTransacao.DEPOSITO, Categoria.MORADIA);
        List<Transacao> transacoes = List.of(transacao);

        ConversaoDtoResponse conversaoDtoResponse = new ConversaoDtoResponse(true, 1928370, "BRL",
                "2024-11-29", Map.of("BRL", BigDecimal.valueOf(5.0)));

        Mockito.when(correntistaRepository.findById(Mockito.any(BigInteger.class))).thenReturn(Optional.of(correntista));
        Mockito.when(transacaoRepository.findAllByContaOrigemId(Mockito.any(BigInteger.class))).thenReturn(transacoes);
        Mockito.when(externalApiClient.get(Mockito.any(ConversaoDtoRequest.class))).thenReturn(Mono.just(conversaoDtoResponse));

        ByteArrayOutputStream result = arquivoServiceImpl.gerarPdfTransacoesComTaxa(BigInteger.ONE);

        assertNotNull(result);
        Mockito.verify(correntistaRepository, Mockito.times(1)).findById(Mockito.any(BigInteger.class));
        Mockito.verify(transacaoRepository, Mockito.times(1)).findAllByContaOrigemId(Mockito.any(BigInteger.class));
        Mockito.verify(externalApiClient, Mockito.times(1)).get(Mockito.any(ConversaoDtoRequest.class));
    }

    @Test
    @DisplayName("Deve gerar um gráfico")
    void shouldGenerateGraphOfExpensesSuccessfully() {
        Correntista correntista = new Correntista(BigInteger.ONE, "John", "Doe", "john.doe@example.com", null, true);
        Conta conta1 = new Conta(BigInteger.ONE, BigDecimal.TEN, "0001-1", correntista, new HashSet<>(), TipoConta.CORRENTE);
        Conta conta2 = new Conta(BigInteger.TWO, BigDecimal.valueOf(100), "0002-1", correntista, new HashSet<>(), TipoConta.POUPANCA);
        Transacao transacao = new Transacao(BigInteger.ONE, LocalDateTime.now(), BigDecimal.valueOf(100), conta1, conta2, TipoTransacao.DEPOSITO, Categoria.MORADIA);        List<Transacao> transacoes = List.of(transacao);

        Mockito.when(transacaoRepository.findAllByContaOrigemId(Mockito.any(BigInteger.class))).thenReturn(transacoes);

        ByteArrayOutputStream result = arquivoServiceImpl.gerarGraficoDespesas(BigInteger.ONE);

        assertNotNull(result);
        Mockito.verify(transacaoRepository, Mockito.times(1)).findAllByContaOrigemId(Mockito.any(BigInteger.class));
    }
}