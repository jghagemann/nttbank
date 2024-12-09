package com.hagemann.nttbank.infra.controller;


import com.hagemann.nttbank.application.usecases.conta.*;
import com.hagemann.nttbank.domain.entities.Conta;
import com.hagemann.nttbank.infra.controller.dto.ContaDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("contas")
public class ContaController {

    private CriarConta criarConta;

    private ListarUmaConta listarUmaConta;

    private ListarContas listarContas;

    private AlterarConta alterarConta;

    private ExcluirConta excluirConta;

    public ContaController(CriarConta criarConta, ListarUmaConta listarUmaConta, ListarContas listarContas,
                           AlterarConta alterarConta, ExcluirConta excluirConta) {
        this.criarConta = criarConta;
        this.listarUmaConta = listarUmaConta;
        this.listarContas = listarContas;
        this.alterarConta = alterarConta;
        this.excluirConta = excluirConta;
    }

    @PostMapping
    public ResponseEntity<ContaDto> criarConta(@RequestBody ContaDto contaDto) {
        Conta salvo = criarConta.criarConta(new Conta(null, contaDto.usuarioId(), contaDto.agencia(), contaDto.numero(),
                contaDto.saldo(), contaDto.tipoConta(), contaDto.bloqueada()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new ContaDto(salvo.getId(), salvo.getUsuarioId(), salvo.getAgencia(), salvo.getNumero(),
                salvo.getSaldo(), salvo.getTipoConta(), salvo.getBloqueada()));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ContaDto>> listarContas() {
        return ResponseEntity.ok(listarContas.listarContas().stream()
                .map(conta -> new ContaDto(conta.getId(), conta.getUsuarioId(), conta.getAgencia(),
                        conta.getNumero(), conta.getSaldo(), conta.getTipoConta(), conta.getBloqueada())).toList());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ContaDto> listarUm(@PathVariable BigInteger id) {
        Conta buscaConta = listarUmaConta.listar(id);

        if (buscaConta == null) {
            return ResponseEntity.notFound().build();
        }
        ContaDto found = new ContaDto(buscaConta.getId(), buscaConta.getUsuarioId(), buscaConta.getAgencia(), buscaConta.getNumero(),
        buscaConta.getSaldo(), buscaConta.getTipoConta(), buscaConta.getBloqueada());

        return ResponseEntity.ok(found);
    }

    @PutMapping
    public ResponseEntity<ContaDto> alterarConta(@RequestBody @Valid ContaDto dto) {
        Conta salvo = alterarConta.alterarConta(new Conta(dto.id(), dto.usuarioId(), dto.agencia(), dto.numero(),
                dto.saldo(), dto.tipoConta(), dto.bloqueada()));
        return ResponseEntity.ok(new ContaDto(salvo.getId(), salvo.getUsuarioId(), salvo.getAgencia(), salvo.getNumero(),
                salvo.getSaldo(), salvo.getTipoConta(), salvo.getBloqueada()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerConta(@PathVariable BigInteger id) {
        excluirConta.removerConta(id);
        return ResponseEntity.noContent().build();
    }
}
