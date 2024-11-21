package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.ContaRepository;
import com.hagemann.nttbank.domain.correntista.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@Transactional
public class CorrentistaServiceImpl implements CorrentistaService {

    private final CorrentistaRepository correntistaRepository;
    private final ContaRepository contaRepository;

    public CorrentistaServiceImpl(CorrentistaRepository correntistaRepository, ContaRepository contaRepository) {
        this.correntistaRepository = correntistaRepository;
        this.contaRepository = contaRepository;
    }

    @Override
    public DetalheCorrentistaDto cadastrar(@Valid CorrentistaDto correntistaDto) {

        Boolean emailExists = correntistaRepository.existsByEmail(correntistaDto.email());

        if (Boolean.TRUE.equals(emailExists)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Correntista correntista = new Correntista();
        correntista.setNome(correntistaDto.nome());
        correntista.setSobrenome(correntistaDto.sobrenome());
        correntista.setEmail(correntistaDto.email());
        correntista.setAtivo(true);

        correntistaRepository.save(correntista);
        return new DetalheCorrentistaDto(correntista);
    }

    @Override
    public Page<DetalheCorrentistaDto> listarTodos(Pageable pageable) {
        return correntistaRepository.findAll(pageable).map(DetalheCorrentistaDto::new);

    }

    @Override
    public DetalheCorrentistaDto listar(BigInteger id) {
        Correntista correntista = correntistaRepository.getReferenceById(id);
        contaRepository.findAllByCorrentistaId(id);
        return new DetalheCorrentistaDto(correntista);
    }

    @Override
    public DetalheCorrentistaDto atualizarDados(@Valid AtualizarDadosCorrentistaDto atualizarDadosCorrentistaDto) {

        Correntista correntista = correntistaRepository.findById(atualizarDadosCorrentistaDto.id())
                .orElseThrow(() -> new EntityNotFoundException("Correntista não encontrado"));

        if (!atualizarDadosCorrentistaDto.nome().isBlank()) {
            correntista.setNome(atualizarDadosCorrentistaDto.nome());
        }
        if (!atualizarDadosCorrentistaDto.sobrenome().isBlank()) {
            correntista.setSobrenome(atualizarDadosCorrentistaDto.sobrenome());
        }
        if (!atualizarDadosCorrentistaDto.email().isBlank()) {
            correntista.setEmail(atualizarDadosCorrentistaDto.email());
        }

        if (atualizarDadosCorrentistaDto.ativo() != null) {
            correntista.setAtivo(atualizarDadosCorrentistaDto.ativo());
        }
        return new DetalheCorrentistaDto(correntista);
    }

    @Override
    public void desativar(BigInteger id) {
        Correntista correntista = correntistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Correntista não encontrado"));
        correntista.setAtivo(false);
    }
}
