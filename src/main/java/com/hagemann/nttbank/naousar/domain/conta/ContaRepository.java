package com.hagemann.nttbank.naousar.domain.conta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Set;


public interface ContaRepository extends JpaRepository<Conta, BigInteger> {

    Set<Conta> findAllByCorrentistaId(BigInteger id);

    Page<Conta> findAllByCorrentistaId(BigInteger id, Pageable pageable);

    Boolean existsByNumero(String numero);
}
