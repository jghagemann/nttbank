package com.hagemann.nttbank.infra.gateways.mapper;

import com.hagemann.nttbank.domain.entities.Conta;
import com.hagemann.nttbank.infra.persistence.entities.ContaEntity;

public class ContaEntityMapper {

    public ContaEntity toEntity(Conta conta){
        return new ContaEntity(conta.getId(), null, conta.getAgencia(), conta.getNumero(), conta.getSaldo(), conta.getTipoConta(), conta.getBloqueada());
    }

    public Conta toDomain(ContaEntity entity){
        return new Conta(entity.getId(), entity.getUsuario().getId(), entity.getAgencia(), entity.getConta(), entity.getSaldo(), entity.getTipoConta(), entity.getBloqueada());
    }
}
