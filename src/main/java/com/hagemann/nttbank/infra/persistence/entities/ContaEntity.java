package com.hagemann.nttbank.infra.persistence.entities;

import com.hagemann.nttbank.domain.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contas")
public class ContaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    private UsuarioEntity usuario;

    private String agencia;

    @Column(unique = true)
    private String conta;

    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    private Boolean bloqueada;
}
