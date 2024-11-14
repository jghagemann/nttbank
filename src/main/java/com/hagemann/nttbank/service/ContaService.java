package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.*;
import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;

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

    public Page<DetalheContaDto> listarContas(BigInteger id, @PageableDefault(sort = {"tipoConta"}) Pageable paginacao) {
        Page<Conta> contas = contaRepository.findAllByCorrentistaId(id, paginacao);
        return contas.map(DetalheContaDto::new);
    }

    public DetalheContaDto listar(BigInteger id) {
        Conta conta = contaRepository.getReferenceById(id);
        return new DetalheContaDto(conta);
    }

    public DetalheContaDto atualizar(@RequestBody @Valid AtualizarDadosContaDto atualizarDadosContaDto) {
        Conta conta = contaRepository.getReferenceById(atualizarDadosContaDto.id());
        conta.atualizarDados(atualizarDadosContaDto);
        return new DetalheContaDto(conta);
    }

    public void excluir(BigInteger id) {
        Conta conta = contaRepository.getReferenceById(id);
        contaRepository.delete(conta);
    }
}
