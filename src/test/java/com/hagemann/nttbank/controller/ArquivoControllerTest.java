package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.naousar.controller.ArquivoController;
import com.hagemann.nttbank.naousar.exceptions.ArquivoException;
import com.hagemann.nttbank.naousar.service.ArquivoServiceImpl;
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
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

class ArquivoControllerTest {

    @InjectMocks
    private ArquivoController arquivoController;

    @Mock
    ArquivoServiceImpl arquivoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Deve retornar 200 ao realizar upload de arquivo excel")
    void shouldReturn200UploadExcel() {
        byte[] inputArray = "testString".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("testFile", inputArray);

        Mockito.doNothing().when(arquivoService).uploadExcel(mockMultipartFile);

        ResponseEntity<String> result = arquivoController.uploadFile(mockMultipartFile);

        Assertions.assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar 417 ao falhar upload de arquivo excel")
    void shouldReturn417UploadExcel() {
        byte[] inputArray = "testString".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("testFile", inputArray);

        Mockito.doThrow(ArquivoException.class).when(arquivoService).uploadExcel(mockMultipartFile);

        ResponseEntity<String> result = arquivoController.uploadFile(mockMultipartFile);

        Assertions.assertEquals(HttpStatus.valueOf(417), result.getStatusCode());
        Assertions.assertEquals(result.getBody(), "Falha ao efetuar upload do arquivo: " + mockMultipartFile.getOriginalFilename() + "!");
    }

    @Test
    @DisplayName("Deve retornar 200 ao gerar PDF com cotação")
    void shouldReturn200DownloadPdfComCotacao() {
        Mockito.when(arquivoService.gerarPdfTransacoesComTaxa(Mockito.any())).thenReturn(new ByteArrayOutputStream(58));

        ResponseEntity<byte[]> result = arquivoController.generateTransacaoReport(BigInteger.ONE, true);

        Assertions.assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar 200 ao gerar PDF sem cotação")
    void shouldReturn200DownloadPdfSemCotacao() {
        Mockito.when(arquivoService.gerarPdfTransacoes(Mockito.any())).thenReturn(new ByteArrayOutputStream(58));

        ResponseEntity<byte[]> result = arquivoController.generateTransacaoReport(BigInteger.ONE, false);

        Assertions.assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar 200 ao gerar gráfico de despesas")
    void shouldReturn200GraficoDespesas() {
        Mockito.when(arquivoService.gerarGraficoDespesas(Mockito.any())).thenReturn(new ByteArrayOutputStream(58));

        ResponseEntity<byte[]> result = arquivoController.gerarGraficoDespesas(BigInteger.ONE);

        Assertions.assertEquals(HttpStatus.valueOf(200), result.getStatusCode());
    }

}