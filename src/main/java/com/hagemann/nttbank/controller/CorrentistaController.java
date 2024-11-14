package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.domain.correntista.*;
import com.hagemann.nttbank.service.CorrentistaService;
import jakarta.validation.Valid;
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
@RequestMapping("correntistas")
public class CorrentistaController {

    private final CorrentistaService correntistaService;

    private final CorrentistaRepository correntistaRepository;

    public CorrentistaController(CorrentistaService correntistaService, CorrentistaRepository correntistaRepository) {
        this.correntistaService = correntistaService;
        this.correntistaRepository = correntistaRepository;
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<DetalheCorrentistaDto> cadastrar(@RequestBody @Valid CorrentistaDto correntistaDto,
            UriComponentsBuilder uriComponentsBuilder) {
        Correntista correntista = new Correntista(correntistaDto);
        correntistaRepository.save(correntista);

        URI uri = uriComponentsBuilder.path("/correntistas/{id}").buildAndExpand(correntista.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalheCorrentistaDto(correntista));
    }

    @GetMapping
    public ResponseEntity<Page<DetalheCorrentistaDto>> listarTodos(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        Page<DetalheCorrentistaDto> pagina = correntistaRepository.findAll(paginacao).map(DetalheCorrentistaDto::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalheCorrentistaDto> listar(@PathVariable BigInteger id) {
        DetalheCorrentistaDto correntistaDto = correntistaService.listar(id);
        return ResponseEntity.ok(correntistaDto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalheCorrentistaDto> atualizar(@RequestBody @Valid AtualizarDadosCorrentistaDto atualizarDadosCorrentistaDto) {
        Correntista correntista = correntistaRepository.getReferenceById(atualizarDadosCorrentistaDto.id());
        correntista.atualizarDados(atualizarDadosCorrentistaDto);

        return ResponseEntity.ok(new DetalheCorrentistaDto(correntista));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable BigInteger id) {
        Correntista correntista = correntistaRepository.getReferenceById(id);
        correntista.excluir();

        return ResponseEntity.noContent().build();
    }
}
