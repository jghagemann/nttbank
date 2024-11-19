package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.transacao.AtualizarTransacaoDto;
import com.hagemann.nttbank.domain.transacao.DetalheTransacaoDto;
import com.hagemann.nttbank.domain.transacao.TransacaoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

public interface TransacaoService {

    DetalheTransacaoDto criarTransacao(TransacaoDto transacaoDto);
    
    Page<DetalheTransacaoDto> listar(BigInteger correntistaId, BigInteger contaId, Pageable pageable);

    DetalheTransacaoDto listarUm(BigInteger id);

    DetalheTransacaoDto atualizarTransacao(AtualizarTransacaoDto atualizarTransacaoDto);
}
