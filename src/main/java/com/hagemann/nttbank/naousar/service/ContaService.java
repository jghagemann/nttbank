package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.domain.conta.AtualizarDadosContaDto;
import com.hagemann.nttbank.naousar.domain.conta.ContaDto;
import com.hagemann.nttbank.naousar.domain.conta.DetalheContaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public interface ContaService {

    DetalheContaDto criarConta(ContaDto contaDto);

    Page<DetalheContaDto> listarContas(BigInteger id, Pageable pageable);

    DetalheContaDto listar(BigInteger id);

    DetalheContaDto atualizarDadosConta(AtualizarDadosContaDto atualizarDadosContaDto);

    void excluir(BigInteger id);
}
