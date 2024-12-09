package com.hagemann.nttbank.naousar.controller;

import com.hagemann.nttbank.naousar.domain.correntista.AtualizarDadosCorrentistaDto;
import com.hagemann.nttbank.naousar.domain.correntista.CorrentistaDto;
import com.hagemann.nttbank.naousar.domain.correntista.DetalheCorrentistaDto;
import com.hagemann.nttbank.naousar.service.CorrentistaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;

@RestController
@RequestMapping("correntistas")
@SecurityRequirement(name = "bearer-key")
public class CorrentistaController {

    private final CorrentistaService correntistaService;

    public CorrentistaController(CorrentistaService correntistaService) {
        this.correntistaService = correntistaService;
    }

    @PostMapping()
    public ResponseEntity<DetalheCorrentistaDto> cadastrar(@RequestBody CorrentistaDto correntistaDto,
            UriComponentsBuilder uriComponentsBuilder) {

        DetalheCorrentistaDto correntista = correntistaService.cadastrar(correntistaDto);

        URI uri = uriComponentsBuilder.path("/correntistas/{id}").buildAndExpand(correntista.id()).toUri();
        return ResponseEntity.created(uri).body(correntista);
    }

    @GetMapping
    public ResponseEntity<Page<DetalheCorrentistaDto>> listarTodos(@PageableDefault(sort = {"nome"}) Pageable pageable) {
        Page<DetalheCorrentistaDto> detalheCorrentistaDtoPage = correntistaService.listarTodos(pageable);
        return ResponseEntity.ok(detalheCorrentistaDtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheCorrentistaDto> listar(@PathVariable BigInteger id) {
        DetalheCorrentistaDto correntistaDto = correntistaService.listar(id);
        return ResponseEntity.ok(correntistaDto);
    }

    @PutMapping
    public ResponseEntity<DetalheCorrentistaDto> atualizar(@RequestBody AtualizarDadosCorrentistaDto atualizarDadosCorrentistaDto) {
        DetalheCorrentistaDto detalheCorrentistaDto = correntistaService.atualizarDados(atualizarDadosCorrentistaDto);

        return ResponseEntity.ok(detalheCorrentistaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable BigInteger id) {
        correntistaService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
