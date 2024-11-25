package com.hagemann.nttbank.controller;

import com.hagemann.nttbank.service.ArquivoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("arquivos")
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
        } catch (Exception e) {
            message = "Falha ao efetuar upload do arquivo: " + arquivo.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


}
