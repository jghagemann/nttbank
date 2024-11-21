package com.hagemann.nttbank.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "NTT Bank API",
                description = "Projeto para o programa UpTalent de Java intermediário",
                version = "0.0.1-SNAPSHOT",
                summary = "API com CRUDs básicos, integração com APIs externa e MockAPI e geração de arquivos"
        ),
        servers = {
                @Server(description = "Servidor Local", url = "localhost:8080")
        })
public class OpenApiConfig {}
