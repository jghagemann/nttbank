package com.hagemann.nttbank.naousar.service;

import com.hagemann.nttbank.naousar.domain.correntista.AtualizarDadosCorrentistaDto;
import com.hagemann.nttbank.naousar.domain.correntista.CorrentistaDto;
import com.hagemann.nttbank.naousar.domain.correntista.DetalheCorrentistaDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;

public interface CorrentistaService {

    DetalheCorrentistaDto cadastrar(CorrentistaDto correntistaDto);

    Page<DetalheCorrentistaDto> listarTodos(Pageable pageable);

    DetalheCorrentistaDto listar(BigInteger id);

    DetalheCorrentistaDto atualizarDados(@RequestBody @Valid AtualizarDadosCorrentistaDto atualizarDadosCorrentistaDto);

    void desativar(BigInteger id);
}
