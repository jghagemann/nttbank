package com.hagemann.nttbank.naousar.controller;

import com.hagemann.nttbank.naousar.domain.usuario.DetalheUsuarioDto;
import com.hagemann.nttbank.naousar.domain.usuario.UsuarioDto;
import com.hagemann.nttbank.naousar.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;

@RestController
@RequestMapping("/administrador")
public class UsuarioAdminController {

    private UsuarioService usuarioService;

    public UsuarioAdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<DetalheUsuarioDto> criarUsuario(@RequestBody UsuarioDto usuarioDto, UriComponentsBuilder uriComponentsBuilder) {

        DetalheUsuarioDto usuario = usuarioService.criarUsuario(usuarioDto);

        URI uri = uriComponentsBuilder.path("/administrador/{id}").buildAndExpand(usuario.id()).toUri();

        return ResponseEntity.created(uri).body(usuario);
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable BigInteger id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
