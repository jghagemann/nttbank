package com.hagemann.nttbank.service;

import org.springframework.web.multipart.MultipartFile;

public interface ArquivoService {

    void uploadExcel(MultipartFile arquivo);
}
