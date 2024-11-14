package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.Conta;
import com.hagemann.nttbank.domain.conta.ContaDto;
import com.hagemann.nttbank.domain.conta.ContaRepository;
import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final CorrentistaRepository correntistaRepository;

    public ContaService(ContaRepository contaRepository, CorrentistaRepository correntistaRepository) {
        this.contaRepository = contaRepository;
        this.correntistaRepository = correntistaRepository;
    }

    public Conta criarConta(ContaDto contaDto) {

        Correntista correntista = correntistaRepository.getReferenceById(contaDto.correntistaId());

        Conta conta = new Conta(contaDto.saldo(), contaDto.numero(), correntista, contaDto.tipoConta());
        contaRepository.save(conta);
        return conta;
    }
}
