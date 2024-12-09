package com.hagemann.nttbank.infra.persistence;

import com.hagemann.nttbank.infra.persistence.entities.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ContaRepository extends JpaRepository<ContaEntity, BigInteger> {
}
