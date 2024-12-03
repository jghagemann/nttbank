package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.*;
import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import com.hagemann.nttbank.exceptions.ContaJaCadastradaException;
import com.hagemann.nttbank.exceptions.ListaVaziaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Objects;

@Service
@Transactional
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;
    private final CorrentistaRepository correntistaRepository;

    public ContaServiceImpl(ContaRepository contaRepository, CorrentistaRepository correntistaRepository) {
        this.contaRepository = contaRepository;
        this.correntistaRepository = correntistaRepository;
    }

    @Override
    public DetalheContaDto criarConta(@Valid ContaDto contaDto) {

        Correntista correntista = correntistaRepository.getReferenceById(contaDto.correntistaId());

        Conta conta = new Conta();
        conta.setTipoConta(contaDto.tipoConta());
        conta.setCorrentista(correntista);
        conta.setNumero(contaDto.numero());
        conta.setSaldo(contaDto.saldo());
        contaRepository.save(conta);
        return new DetalheContaDto(conta);
    }
    @Override
    public Page<DetalheContaDto> listarContas(@NotNull BigInteger id, Pageable pageable) {
        Page<Conta> contas = contaRepository.findAllByCorrentistaId(id, pageable);

        if (contas.getContent().isEmpty()) {
            throw new ListaVaziaException("Não foram encontradas contas");
        }
        return contas.map(DetalheContaDto::new);
    }

    @Override
    public DetalheContaDto listar(@NotNull BigInteger id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        return new DetalheContaDto(conta);
    }

    @Override
    public DetalheContaDto atualizarDadosConta(@Valid AtualizarDadosContaDto atualizarDadosContaDto) {
        Conta conta = contaRepository.findById(atualizarDadosContaDto.id())
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        Boolean isContaNumeroUtilizado = contaRepository.existsByNumero(atualizarDadosContaDto.numero());

        if (Boolean.TRUE.equals(isContaNumeroUtilizado)) {
            throw new ContaJaCadastradaException("Esse número de conta já existe");
        }
        conta.setNumero(atualizarDadosContaDto.numero());
        contaRepository.save(conta);
        return new DetalheContaDto(conta);
    }

    @Override
    public void excluir(@NotNull BigInteger id) {
        Conta conta = contaRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        contaRepository.delete(conta);
    }
}
