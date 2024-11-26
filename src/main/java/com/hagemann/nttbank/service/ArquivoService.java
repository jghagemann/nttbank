package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.transacao.Transacao;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;

public interface ArquivoService {

    void uploadExcel(MultipartFile arquivo);

    ByteArrayOutputStream gerarPdfTransacoes(BigInteger correntistaId);

    ByteArrayOutputStream gerarGraficoDespesas(BigInteger correntistaId);
}
