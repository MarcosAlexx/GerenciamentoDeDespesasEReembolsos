package com.example.GerenciamentoDeDespesasReembolsos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "centros_de_custo")
public class CentroDeCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private double saldoDisponivel;

    @Column(nullable = false)
    private double orcamentoMensal;

    protected CentroDeCusto() {
    }

    public CentroDeCusto(String nome, double saldoDisponivel, double orcamentoMensal) {
        this.nome = nome;
        this.saldoDisponivel = saldoDisponivel;
        this.orcamentoMensal = orcamentoMensal;
    }

    public CentroDeCusto(String string, String ti, double orcamentoMensal, double v) {
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(double saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public double getOrcamentoMensal() {
        return orcamentoMensal;
    }

    public void setOrcamentoMensal(double orcamentoMensal) {
        this.orcamentoMensal = orcamentoMensal;
    }
}
