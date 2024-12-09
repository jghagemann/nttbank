package com.hagemann.nttbank.infra.controller;

import com.hagemann.nttbank.application.usecases.usuario.*;
import com.hagemann.nttbank.domain.entities.Usuario;
import com.hagemann.nttbank.infra.controller.dto.UsuarioDto;
import com.hagemann.nttbank.infra.controller.dto.UsuarioResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final AtualizarUsuario atualizarUsuario;

    private final CriarUsuario criarUsuario;

    private final ExcluirUsuario excluirUsuario;

    private final ListarUmUsuario listarUmUsuario;

    private final ListarUsuarios listarUsuarios;

    public UsuarioController(AtualizarUsuario atualizarUsuario, CriarUsuario criarUsuario, ExcluirUsuario excluirUsuario,
                             ListarUmUsuario listarUmUsuario, ListarUsuarios listarUsuarios) {
        this.atualizarUsuario = atualizarUsuario;
        this.criarUsuario = criarUsuario;
        this.excluirUsuario = excluirUsuario;
        this.listarUmUsuario = listarUmUsuario;
        this.listarUsuarios = listarUsuarios;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario (@RequestBody @Valid UsuarioDto usuarioDto) {
        Usuario salvo = criarUsuario.cadastrarUsuario(new Usuario(null, usuarioDto.cpf(), usuarioDto.nome(), usuarioDto.sobrenome(),
                usuarioDto.login(), usuarioDto.senha(), usuarioDto.nascimento(), usuarioDto.email()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponseDto(salvo.getCpf(), salvo.getNome(), salvo.getSobrenome(),
                salvo.getLogin(), salvo.getNascimento(), salvo.getEmail()));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios(Pageable pageable) {
        return ResponseEntity.ok(listarUsuarios.listarTodosUsuarios().stream().map(usuario -> new UsuarioResponseDto(usuario.getCpf(), usuario.getNome(),
                usuario.getSobrenome(), usuario.getLogin(), usuario.getNascimento(), usuario.getEmail())).toList());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<UsuarioResponseDto> listarUm(@PathVariable BigInteger id) {
        Usuario buscaUsuario = listarUmUsuario.listar(id);

        if (buscaUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        UsuarioResponseDto found = new UsuarioResponseDto(buscaUsuario.getCpf(), buscaUsuario.getNome(),
                buscaUsuario.getSobrenome(), buscaUsuario.getLogin(), buscaUsuario.getNascimento(), buscaUsuario.getEmail());
        return ResponseEntity.ok(found);
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDto> atualizarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        Usuario salvo = atualizarUsuario.atualizarUsuario(new Usuario(null, usuarioDto.cpf(), usuarioDto.nome(), usuarioDto.sobrenome(),
                usuarioDto.login(), usuarioDto.senha(), usuarioDto.nascimento(), usuarioDto.email()));

        return ResponseEntity.ok(new UsuarioResponseDto(salvo.getCpf(), salvo.getNome(), salvo.getSobrenome(),
                salvo.getLogin(), salvo.getNascimento(), salvo.getEmail()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable BigInteger id) {
        excluirUsuario.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
