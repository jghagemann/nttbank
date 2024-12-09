package com.hagemann.nttbank.config;

import com.hagemann.nttbank.application.gateways.RepositorioDeUsuario;
import com.hagemann.nttbank.application.usecases.usuario.*;
import com.hagemann.nttbank.infra.gateways.RepositorioDeUsuarioJpa;
import com.hagemann.nttbank.infra.gateways.mapper.UsuarioEntityMapper;
import com.hagemann.nttbank.infra.persistence.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    CriarUsuario criarUsuario(RepositorioDeUsuario repositorioDeUsuario) {
        return new CriarUsuario(repositorioDeUsuario);
    }

    @Bean
    ListarUsuarios listarUsuarios(RepositorioDeUsuario repositorioDeUsuario) {
        return new ListarUsuarios(repositorioDeUsuario);
    }

    @Bean
    ListarUmUsuario listarUmUsuario(RepositorioDeUsuario repositorioDeUsuario) {
        return new ListarUmUsuario(repositorioDeUsuario);
    }

    @Bean
    AtualizarUsuario atualizarUsuario(RepositorioDeUsuario repositorioDeUsuario) {
        return new AtualizarUsuario(repositorioDeUsuario);
    }

    @Bean
    ExcluirUsuario excluirUsuario(RepositorioDeUsuario repositorioDeUsuario) {
        return new ExcluirUsuario(repositorioDeUsuario);
    }

    @Bean
    RepositorioDeUsuarioJpa criarRepositorioJpa(UsuarioRepository repository, UsuarioEntityMapper mapper) {
        return new RepositorioDeUsuarioJpa(repository, mapper);
    }

    @Bean
    UsuarioEntityMapper retornaMapper() {
        return new UsuarioEntityMapper();
    }

}
