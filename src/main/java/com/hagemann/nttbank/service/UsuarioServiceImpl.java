package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.usuario.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DetalheUsuarioDto criarUsuario(@Valid UsuarioDto usuarioDto) {

        String senhaEncoded = passwordEncoder.encode(usuarioDto.senha());
        Usuario usuario = new Usuario();
        usuario.setLogin(usuarioDto.login());
        usuario.setSenha(senhaEncoded);

        usuarioRepository.save(usuario);
        return new DetalheUsuarioDto(usuario);
    }

    @Override
    public void excluirUsuario(BigInteger id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
        usuarioRepository.delete(usuario);
    }


}
