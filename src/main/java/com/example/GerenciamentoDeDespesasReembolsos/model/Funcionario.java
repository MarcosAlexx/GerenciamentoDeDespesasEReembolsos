package com.example.GerenciamentoDeDespesasReembolsos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private String id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String cargo;

    @Column(nullable = false)
    private double limiteReembolsoMensal;

    @Column(nullable = false)
    private double saldoReembolsoDisponivel;

    // ✅ Construtor vazio obrigatório para JPA
    protected Funcionario() {
    }

    // ✅ REMOVIDO: this.id = UUID.randomUUID().toString();
    public Funcionario(
            String nome,
            String cargo,
            double limiteReembolsoMensal,
            double saldoReembolsoDisponivel
    ) {
        this.nome = nome;
        this.cargo = cargo;
        this.limiteReembolsoMensal = limiteReembolsoMensal;
        this.saldoReembolsoDisponivel = saldoReembolsoDisponivel;
    }

    // ✅ Getters (SEM setter para ID!)
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getLimiteReembolsoMensal() {
        return limiteReembolsoMensal;
    }

    public void setLimiteReembolsoMensal(double limiteReembolsoMensal) {
        this.limiteReembolsoMensal = limiteReembolsoMensal;
    }

    public double getSaldoReembolsoDisponivel() {
        return saldoReembolsoDisponivel;
    }

    public void setSaldoReembolsoDisponivel(double saldoReembolsoDisponivel) {
        this.saldoReembolsoDisponivel = saldoReembolsoDisponivel;
    }
}