package com.hagemann.nttbank.application.gateways;

import com.hagemann.nttbank.domain.entities.Transacao;

import java.util.List;

public interface RepositorioDeTransacao {

    Transacao criarTransacao(Transacao transacao);

    List<Transacao> listarTransacoes();

    Transacao alterarTransacao(Transacao transacao);

    void removerTransacao(Long id);

}
