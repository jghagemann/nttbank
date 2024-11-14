package com.hagemann.nttbank.domain.conta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ContaRepository extends JpaRepository<Conta, BigInteger> {
}
