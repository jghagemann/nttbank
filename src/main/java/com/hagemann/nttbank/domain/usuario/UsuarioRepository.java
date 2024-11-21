package com.hagemann.nttbank.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;

public interface UsuarioRepository extends JpaRepository<Usuario, BigInteger> {

    UserDetails findByLogin(String login);
}
