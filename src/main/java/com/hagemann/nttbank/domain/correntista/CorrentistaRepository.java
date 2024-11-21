package com.hagemann.nttbank.domain.correntista;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CorrentistaRepository extends JpaRepository<Correntista, BigInteger> {

    Boolean existsByEmail(String email);

}
