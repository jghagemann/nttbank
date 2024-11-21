package com.hagemann.nttbank.service;

import com.hagemann.nttbank.domain.conta.*;
import com.hagemann.nttbank.domain.correntista.CorrentistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContaServiceImplTest {

    @InjectMocks
    private ContaServiceImpl contaServiceImpl;

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

        DetalheContaDto result = contaServiceImpl.criarConta(contaCorrenteDto);

        assertNotNull(result);
        assertEquals("1234", result.numero());
        assertEquals(TipoConta.CORRENTE, result.tipoConta());
        assertEquals(BigDecimal.TEN, result.saldo());
    }

    void criarContaPoupanca() {

        ContaDto contaCorrenteDto = new ContaDto(BigDecimal.TEN, "1234", BigInteger.ONE, TipoConta.POUPANCA);

        DetalheContaDto result = contaServiceImpl.criarConta(contaCorrenteDto);

        assertNotNull(result);
        assertEquals("1234", result.numero());
        assertEquals(TipoConta.POUPANCA, result.tipoConta());
        assertEquals(BigDecimal.TEN, result.saldo());
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