package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.domain.usuario.DetalheUsuarioDto;
import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;
import com.hagemann.nttbank.naousar.domain.usuario.UsuarioDto;
import com.hagemann.nttbank.infra.persistence.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setLogin(usuarioDto.login());
        usuarioEntity.setSenha(senhaEncoded);

        usuarioRepository.save(usuarioEntity);
        return new DetalheUsuarioDto(usuarioEntity);
    }

    @Override
    public void excluirUsuario(@NotNull BigInteger id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(usuarioEntity);
    }


}
