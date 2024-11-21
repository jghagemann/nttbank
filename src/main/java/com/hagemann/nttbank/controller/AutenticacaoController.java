package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.domain.usuario.AutenticacaoDto;
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

    public AutenticacaoController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody AutenticacaoDto autenticacaoDto) {
        Authentication token = new UsernamePasswordAuthenticationToken(autenticacaoDto.login(), autenticacaoDto.senha());
        authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
