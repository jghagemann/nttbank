package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import com.hagemann.nttbank.helper.ExcelHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ArquivoServiceImpl implements ArquivoService {

    CorrentistaRepository correntistaRepository;

    public ArquivoServiceImpl(CorrentistaRepository correntistaRepository) {
        this.correntistaRepository = correntistaRepository;
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
}
