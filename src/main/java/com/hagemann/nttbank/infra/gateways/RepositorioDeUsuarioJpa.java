package com.hagemann.nttbank.infra.gateways;

import com.hagemann.nttbank.application.gateways.RepositorioDeUsuario;
import com.hagemann.nttbank.domain.entities.Usuario;
import com.hagemann.nttbank.infra.gateways.mapper.UsuarioEntityMapper;
import com.hagemann.nttbank.infra.persistence.entities.UsuarioEntity;
import com.hagemann.nttbank.infra.persistence.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigInteger;
import java.util.List;

public class RepositorioDeUsuarioJpa implements RepositorioDeUsuario, UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioEntityMapper mapper;

    public RepositorioDeUsuarioJpa(UsuarioRepository usuarioRepository, UsuarioEntityMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        UsuarioEntity usuarioSalvo = usuarioRepository.save(entity);
        return mapper.toDomain(usuarioSalvo);

    }

    @Override
    public void excluirUsuario(BigInteger id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(usuarioEntity);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Usuario listar(BigInteger id) {
        UsuarioEntity usuarioListado = usuarioRepository.getReferenceById(id);
        return mapper.toDomain(usuarioListado);

    }

    @Override
    public Usuario atualizar(Usuario usuario) {

        UsuarioEntity usuarioEntity = usuarioRepository.findByCpf(usuario.getCpf());

        if (usuarioEntity == null) {
            throw new EntityNotFoundException("Usuário com CPF " + usuario.getCpf() + " não encontrado");
        }

        UsuarioEntity updatedUsuario = mapper.toEntity(usuario);
        updatedUsuario.setId(usuarioEntity.getId());
        usuarioRepository.save(updatedUsuario);

        return mapper.toDomain(updatedUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);

    }
}
