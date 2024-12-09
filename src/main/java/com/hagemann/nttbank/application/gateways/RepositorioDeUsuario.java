package com.hagemann.nttbank.application.gateways;

import com.hagemann.nttbank.domain.entities.Usuario;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public interface RepositorioDeUsuario {

    Usuario cadastrarUsuario(Usuario usuario);

    void excluirUsuario(BigInteger id);

    List<Usuario> listarTodos();

    Usuario listar(BigInteger id);

    Usuario atualizar(Usuario usuario);
}
