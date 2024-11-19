package com.hagemann.nttbank.domain.transacao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, BigInteger> {

    Page<Transacao> findAllByContaOrigemCorrentistaIdAndContaDestinoCorrentistaId(BigInteger correntistaId, BigInteger contaId, Pageable pageable);
}
