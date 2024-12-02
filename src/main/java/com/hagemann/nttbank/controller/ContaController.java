package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.domain.conta.AtualizarDadosContaDto;
import com.hagemann.nttbank.domain.conta.ContaDto;
import com.hagemann.nttbank.domain.conta.DetalheContaDto;
import com.hagemann.nttbank.service.ContaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;

@RestController
@RequestMapping("/contas")
@SecurityRequirement(name = "bearer-key")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping()
    ResponseEntity<DetalheContaDto> criarConta(@RequestBody ContaDto contaDto,
        UriComponentsBuilder uriComponentsBuilder) {

        DetalheContaDto conta = contaService.criarConta(contaDto);
        URI uri = uriComponentsBuilder.path("/contas/{id}").buildAndExpand(conta.id()).toUri();

        return ResponseEntity.created(uri).body(conta);
    }

    @GetMapping("/correntista/{id}")
    public ResponseEntity<Page<DetalheContaDto>> listarContas(@PathVariable BigInteger id, @PageableDefault(sort = {"tipoConta"}) Pageable paginacao) {
        Page<DetalheContaDto> pagina = contaService.listarContas(id, paginacao);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheContaDto> listar(@PathVariable BigInteger id) {
        DetalheContaDto detalheContaDto = contaService.listar(id);
        return ResponseEntity.ok(detalheContaDto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalheContaDto> atualizarDadosConta(@RequestBody AtualizarDadosContaDto atualizarDadosContaDto) {
        DetalheContaDto contaAtualizada = contaService.atualizarDadosConta(atualizarDadosContaDto);
        return ResponseEntity.ok(contaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable BigInteger id) {
        contaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
