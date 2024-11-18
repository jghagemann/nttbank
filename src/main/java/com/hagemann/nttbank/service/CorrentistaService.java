package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.ContaRepository;
import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import com.hagemann.nttbank.domain.correntista.DetalheCorrentistaDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class CorrentistaService {

    private final CorrentistaRepository correntistaRepository;
    private final ContaRepository contaRepository;

    public CorrentistaService(CorrentistaRepository correntistaRepository, ContaRepository contaRepository) {
        this.correntistaRepository = correntistaRepository;
        this.contaRepository = contaRepository;
    }

    public DetalheCorrentistaDto listar(BigInteger id) {
        Correntista correntista = correntistaRepository.getReferenceById(id);
        contaRepository.findAllByCorrentistaId(id);
        return new DetalheCorrentistaDto(correntista);
    }
}
