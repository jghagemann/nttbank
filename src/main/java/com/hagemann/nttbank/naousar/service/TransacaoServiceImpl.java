package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.domain.conta.Conta;
import com.hagemann.nttbank.naousar.domain.conta.ContaRepository;
import com.hagemann.nttbank.naousar.domain.transacao.*;
import com.hagemann.nttbank.naousar.exceptions.VisualizarListaTransacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
public class TransacaoServiceImpl implements TransacaoService {

    private TransacaoRepository transacaoRepository;

    private ContaRepository contaRepository;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    public DetalheTransacaoDto criarTransacao(TransacaoDto transacaoDto) {

        Transacao transacao = new Transacao();
        LocalDateTime data = LocalDateTime.now();

        transacao.setDataTransacao(data);
        transacao.setValor(transacaoDto.valor());
        transacao.setContaOrigem(transacaoDto.contaOrigem());
        transacao.setContaDestino(transacaoDto.contaDestino());
        transacao.setTipoTransacao(transacaoDto.tipoTransacao());
        transacao.setCategoria(transacaoDto.categoria());

        transacaoRepository.save(transacao);

        return new DetalheTransacaoDto(transacao);
    }

    @Override
    public Page<DetalheTransacaoDto> listar(BigInteger correntistaId, BigInteger contaId, Pageable pageable) {
        Conta conta = contaRepository.getReferenceById(contaId);
        
        if (!Objects.equals(correntistaId, conta.getCorrentista().getId())) {
            throw new VisualizarListaTransacaoException("Correntista não autorizado a visualizar transações dessa conta");
        }

        Page<Transacao> transacoes = transacaoRepository.findAllByContaOrigemCorrentistaIdAndContaDestinoCorrentistaId(correntistaId, contaId, pageable);
        return transacoes.map(DetalheTransacaoDto::new);
    }

    @Override
    public DetalheTransacaoDto listarUm(BigInteger id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        return new DetalheTransacaoDto(transacao.getId(), transacao.getValor(), transacao.getDataTransacao(),
                transacao.getContaOrigem().getId(), transacao.getContaDestino().getId(), transacao.getTipoTransacao(),
                transacao.getCategoria());
    }

    @Override
    public DetalheTransacaoDto atualizarTransacao(AtualizarTransacaoDto atualizarTransacaoDto) {
        Transacao transacao = transacaoRepository.findById(atualizarTransacaoDto.id())
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        transacao.setCategoria(atualizarTransacaoDto.categoria());
        return new DetalheTransacaoDto(transacao);
    }
}
