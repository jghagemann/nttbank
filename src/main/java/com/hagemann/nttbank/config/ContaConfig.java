package com.hagemann.nttbank.config;

import com.hagemann.nttbank.application.gateways.RepositorioDeConta;
import com.hagemann.nttbank.application.usecases.conta.*;
import com.hagemann.nttbank.infra.gateways.RepositorioDeContaJpa;
import com.hagemann.nttbank.infra.gateways.mapper.ContaEntityMapper;
import com.hagemann.nttbank.infra.persistence.ContaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContaConfig {

    @Bean
    CriarConta criarConta(RepositorioDeConta repositorio) {
        return new CriarConta(repositorio);
    }

    @Bean
    ListarContas listarContas(RepositorioDeConta repositorio) {
        return new ListarContas(repositorio);
    }

    @Bean
    ListarUmaConta listarUmaConta(RepositorioDeConta repositorio) {
        return new ListarUmaConta(repositorio);
    }

    @Bean
    AlterarConta alterarConta(RepositorioDeConta repositorio) {
        return new AlterarConta(repositorio);
    }

    @Bean
    ExcluirConta excluirConta(RepositorioDeConta repositorio) {
        return new ExcluirConta(repositorio);
    }

    @Bean
    RepositorioDeContaJpa criarRepositorioJpa(ContaRepository repository, ContaEntityMapper mapper) {
        return new RepositorioDeContaJpa(repository, mapper);
    }

    @Bean
    ContaEntityMapper retornaMapper() {
        return new ContaEntityMapper();
    }

}
