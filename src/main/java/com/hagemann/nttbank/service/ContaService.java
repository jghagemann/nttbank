package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.AtualizarDadosContaDto;
import com.hagemann.nttbank.domain.conta.ContaDto;
import com.hagemann.nttbank.domain.conta.DetalheContaDto;
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
