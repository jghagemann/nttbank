package com.hagemann.nttbank.naousar.controller;

import com.hagemann.nttbank.naousar.exceptions.ArquivoException;
import com.hagemann.nttbank.naousar.service.ArquivoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

@RestController
@RequestMapping("arquivos")
@SecurityRequirement(name = "bearer-key")
public class ArquivoController {

    private ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @PostMapping("/excel")
    public ResponseEntity<String> uploadFile(@RequestParam("arquivo") MultipartFile arquivo) {
        String message = "";
        try {
            arquivoService.uploadExcel(arquivo);
            message = "Upload concluído com sucesso: " + arquivo.getOriginalFilename();
            return ResponseEntity.ok(message);
        } catch (ArquivoException e) {
            message = "Falha ao efetuar upload do arquivo: " + arquivo.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/pdf/{correntistaId}")
    public ResponseEntity<byte[]> generateTransacaoReport(
            @PathVariable BigInteger correntistaId,
            @RequestParam(value = "cambio", defaultValue = "false") Boolean cambio) {

        ByteArrayOutputStream baos;
        if (Boolean.TRUE.equals(cambio)) {
            baos = arquivoService.gerarPdfTransacoesComTaxa(correntistaId);
        } else {
            baos = arquivoService.gerarPdfTransacoes(correntistaId);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transacoes_report.pdf")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(baos.toByteArray());
    }

    @GetMapping("/grafico/{correntistaId}")
    public ResponseEntity<byte[]> gerarGraficoDespesas(@PathVariable BigInteger correntistaId) {
        ByteArrayOutputStream graphStream = arquivoService.gerarGraficoDespesas(correntistaId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"grafico_despesas.png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(graphStream.toByteArray());
    }

}
