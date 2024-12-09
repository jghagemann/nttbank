package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.client.ConversaoDtoRequest;
import com.hagemann.nttbank.naousar.client.ExternalApiClient;
import com.hagemann.nttbank.naousar.domain.correntista.Correntista;
import com.hagemann.nttbank.naousar.domain.correntista.CorrentistaRepository;
import com.hagemann.nttbank.naousar.domain.transacao.Transacao;
import com.hagemann.nttbank.naousar.domain.transacao.TransacaoRepository;
import com.hagemann.nttbank.naousar.exceptions.ArquivoUploadException;
import com.hagemann.nttbank.naousar.exceptions.CorrentistaNaoEncontradoException;
import com.hagemann.nttbank.naousar.exceptions.CotacaoException;
import com.hagemann.nttbank.naousar.helper.ExcelHelper;
import com.hagemann.nttbank.naousar.helper.GraficoHelper;
import com.hagemann.nttbank.naousar.helper.PDFHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class ArquivoServiceImpl implements ArquivoService {

    private CorrentistaRepository correntistaRepository;

    private TransacaoRepository transacaoRepository;

    private ExternalApiClient externalApiClient;

    public ArquivoServiceImpl(CorrentistaRepository correntistaRepository, TransacaoRepository transacaoRepository, ExternalApiClient externalApiClient) {
        this.correntistaRepository = correntistaRepository;
        this.transacaoRepository = transacaoRepository;
        this.externalApiClient = externalApiClient;
    }

    @Override
    public void uploadExcel(MultipartFile arquivo) {

        try {
            List<Correntista> correntistasList = ExcelHelper.uploadExcel(arquivo.getInputStream());
            correntistaRepository.saveAll(correntistasList);
        } catch (IOException e) {
            throw new ArquivoUploadException("Falha ao persistir Correntistas no Banco de Dados");
        }
    }

    @Override
    public ByteArrayOutputStream gerarPdfTransacoes(BigInteger correntistaId) {
        Correntista correntista = correntistaRepository.findById(correntistaId)
                .orElseThrow(() -> new CorrentistaNaoEncontradoException("Correntista não encontrado"));

        List<Transacao> transacoes = transacaoRepository.findAllByContaOrigemId(correntistaId);
        return PDFHelper.gerarPdfTransacoes(correntista, transacoes, BigDecimal.ZERO);
    }


    public ByteArrayOutputStream gerarPdfTransacoesComTaxa(BigInteger correntistaId) {
        Correntista correntista = correntistaRepository.findById(correntistaId)
                .orElseThrow(() -> new CorrentistaNaoEncontradoException("Correntista não encontrado"));

        List<Transacao> transacoes = transacaoRepository.findAllByContaOrigemId(correntistaId);

        // Get exchange rate from the API
        return externalApiClient.get(new ConversaoDtoRequest("BRL"))
                .map(response -> {
                    BigDecimal exchangeRate = response.rates().get("BRL");
                    if (exchangeRate == null) {
                        throw new CotacaoException("Falha ao buscar cotação para BRL");
                    }
                    return PDFHelper.gerarPdfTransacoes(correntista, transacoes, exchangeRate);
                })
                .block();
    }

    @Override
    public ByteArrayOutputStream gerarGraficoDespesas(BigInteger correntistaId) {

        List<Transacao> transacoes = transacaoRepository.findAllByContaOrigemId(correntistaId);
        return GraficoHelper.gerarGraficoDespesas(transacoes);
    }


}
