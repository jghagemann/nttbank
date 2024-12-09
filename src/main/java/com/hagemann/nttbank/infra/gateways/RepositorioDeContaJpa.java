package com.hagemann.nttbank.infra.gateways;

import com.hagemann.nttbank.application.gateways.RepositorioDeConta;
import com.hagemann.nttbank.domain.entities.Conta;
import com.hagemann.nttbank.infra.gateways.mapper.ContaEntityMapper;
import com.hagemann.nttbank.infra.persistence.ContaRepository;
import com.hagemann.nttbank.infra.persistence.entities.ContaEntity;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class RepositorioDeContaJpa implements RepositorioDeConta {

    private final ContaRepository repository;

    private final ContaEntityMapper mapper;

    public RepositorioDeContaJpa(ContaRepository repository, ContaEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Conta criarConta(Conta conta) {
        ContaEntity entity = mapper.toEntity(conta);
        ContaEntity contaSalva = repository.save(entity);

        return mapper.toDomain(contaSalva);
    }

    @Override
    public List<Conta> listarContas() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Conta listar(BigInteger id) {

        ContaEntity contaEntity = repository.getReferenceById(id);
        return mapper.toDomain(contaEntity);
    }

    @Override
    public Conta alterarConta(Conta conta) {
        Optional<ContaEntity> contaEntity = repository.findById(conta.getId());

        if (contaEntity.isEmpty()) {
            throw new EntityNotFoundException("Conta não encontrada");
        }

        ContaEntity contaAtualizada = mapper.toEntity(conta);
        repository.save(contaAtualizada);

        return mapper.toDomain(contaAtualizada);
    }

    @Override
    public void removerConta(BigInteger id) {
        ContaEntity contaEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        repository.delete(contaEntity);
    }
}
