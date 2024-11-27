package com.hagemann.nttbank.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

public interface ArquivoService {

    void uploadExcel(MultipartFile arquivo);

    ByteArrayOutputStream gerarPdfTransacoes(BigInteger correntistaId);

    ByteArrayOutputStream gerarPdfTransacoesComTaxa(BigInteger correntistaId);

    ByteArrayOutputStream gerarGraficoDespesas(BigInteger correntistaId);
}
