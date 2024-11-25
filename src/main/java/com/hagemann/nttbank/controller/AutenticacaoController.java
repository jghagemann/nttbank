package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.domain.usuario.AutenticacaoDto;
import com.hagemann.nttbank.domain.usuario.Usuario;
import com.hagemann.nttbank.infra.security.DadosTokenJwtDto;
import com.hagemann.nttbank.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DadosTokenJwtDto> login(@RequestBody AutenticacaoDto autenticacaoDto) {

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(autenticacaoDto.login(), autenticacaoDto.senha());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String tokenJwt = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJwtDto(tokenJwt));
    }
}
