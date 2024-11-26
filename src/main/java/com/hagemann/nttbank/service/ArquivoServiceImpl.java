package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import com.hagemann.nttbank.domain.transacao.Transacao;
import com.hagemann.nttbank.domain.transacao.TransacaoRepository;
import com.hagemann.nttbank.helper.ExcelHelper;
import com.hagemann.nttbank.helper.GraficoHelper;
import com.hagemann.nttbank.helper.PDFHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class ArquivoServiceImpl implements ArquivoService {

    private CorrentistaRepository correntistaRepository;

    private TransacaoRepository transacaoRepository;

    public ArquivoServiceImpl(CorrentistaRepository correntistaRepository, TransacaoRepository transacaoRepository) {
        this.correntistaRepository = correntistaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void uploadExcel(MultipartFile arquivo) {

        try {
            List<Correntista> correntistasList = ExcelHelper.uploadExcel(arquivo.getInputStream());
            correntistaRepository.saveAll(correntistasList);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao persistir Correntistas no Banco de Dados");
        }
    }

    @Override
    public ByteArrayOutputStream gerarPdfTransacoes(BigInteger correntistaId) {
        Correntista correntista = correntistaRepository.findById(correntistaId)
                .orElseThrow(() -> new RuntimeException("Correntista não encontrado"));

        List<Transacao> transacoes = transacaoRepository.findAllByContaOrigemId(correntistaId);
        return PDFHelper.gerarPdfTransacoes(correntista, transacoes);
    }

    @Override
    public ByteArrayOutputStream gerarGraficoDespesas(BigInteger correntistaId) {

        List<Transacao> transacoes = transacaoRepository.findAllByContaOrigemId(correntistaId);
        return GraficoHelper.gerarGraficoDespesas(transacoes);
    }


}
