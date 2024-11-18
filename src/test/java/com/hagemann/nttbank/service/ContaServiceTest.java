package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.*;
import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.correntista.CorrentistaDto;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private CorrentistaRepository correntistaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarContaCorrente() {

        ContaDto contaCorrenteDto = new ContaDto(BigDecimal.TEN, "1234", BigInteger.ONE, TipoConta.CORRENTE);

        Conta result = contaService.criarConta(contaCorrenteDto);

        assertNotNull(result);
        assertEquals("1234", result.getNumero());
        assertEquals(TipoConta.CORRENTE, result.getTipoConta());
        assertEquals(BigDecimal.TEN, result.getSaldo());
    }

    void criarContaPoupanca() {

        ContaDto contaCorrenteDto = new ContaDto(BigDecimal.TEN, "1234", BigInteger.ONE, TipoConta.POUPANCA);

        Conta result = contaService.criarConta(contaCorrenteDto);

        assertNotNull(result);
        assertEquals("1234", result.getNumero());
        assertEquals(TipoConta.POUPANCA, result.getTipoConta());
        assertEquals(BigDecimal.TEN, result.getSaldo());
    }

    @Test
    void listarContas() {


    }

    @Test
    void listar() {

    }

    @Test
    void atualizar() {
    }

    @Test
    void excluir() {
    }
}