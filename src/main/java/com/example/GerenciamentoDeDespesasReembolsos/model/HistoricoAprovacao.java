package com.example.GerenciamentoDeDespesasReembolsos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "historico_aprovacoes")
public class HistoricoAprovacao {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDespesa status;

    @Column(nullable = false, length = 100)
    private String responsavel;

    @Column(length = 255)
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "despesa_id", nullable = false)
    private Despesa despesa;

    protected HistoricoAprovacao() {
    }

    public HistoricoAprovacao(
            LocalDate data,
            StatusDespesa status,
            String responsavel,
            String motivo,
            Despesa despesa
    ) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.status = status;
        this.responsavel = responsavel;
        this.motivo = motivo;
        this.despesa = despesa;
    }

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public StatusDespesa getStatus() {
        return status;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Despesa getDespesa() {
        return despesa;
    }
}
