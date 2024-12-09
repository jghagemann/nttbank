package com.hagemann.nttbank.naousar.domain.transacao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, BigInteger> {


    @Query("""
    SELECT t
    FROM Transacao t
    WHERE t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId
    """)
    Page<Transacao> findAllByContaOrigemCorrentistaIdAndContaDestinoCorrentistaId(BigInteger correntistaId, BigInteger contaId, Pageable pageable);

    List<Transacao> findAllByContaOrigemId(BigInteger contaOrigemId);

    List<Transacao> findAllByContaDestinoId(BigInteger contaDestinoId);
}
