package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.usuario.DetalheUsuarioDto;
import com.hagemann.nttbank.domain.usuario.UsuarioDto;

import java.math.BigInteger;

public interface UsuarioService {

    DetalheUsuarioDto criarUsuario(UsuarioDto usuarioDto);

    void excluirUsuario(BigInteger id);
}
