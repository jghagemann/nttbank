package com.hagemann.nttbank.infra.persistence;

import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, BigInteger> {

    UserDetails findByLogin(String login);

    UsuarioEntity findByCpf(String cpf);

}
