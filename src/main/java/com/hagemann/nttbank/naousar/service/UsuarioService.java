package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.domain.usuario.DetalheUsuarioDto;
import com.hagemann.nttbank.naousar.domain.usuario.UsuarioDto;

import java.math.BigInteger;

public interface UsuarioService {

    DetalheUsuarioDto criarUsuario(UsuarioDto usuarioDto);

    void excluirUsuario(BigInteger id);
}
