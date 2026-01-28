package com.example.GerenciamentoDeDespesasReembolsos.service;

import com.example.GerenciamentoDeDespesasReembolsos.model.CentroDeCusto;
import com.example.GerenciamentoDeDespesasReembolsos.model.Despesa;
import com.example.GerenciamentoDeDespesasReembolsos.repository.CentroDeCustoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FinanceiroService {

    private final CentroDeCustoRepository centroDeCustoRepository;

    public FinanceiroService(CentroDeCustoRepository centroDeCustoRepository) {
        this.centroDeCustoRepository = centroDeCustoRepository;
    }

    public CentroDeCusto criarCentroDeCusto(CentroDeCusto centroDeCusto) {
        if (centroDeCusto.getSaldoDisponivel() == 0) {
            centroDeCusto.setSaldoDisponivel(centroDeCusto.getOrcamentoMensal());
        }
        return centroDeCustoRepository.save(centroDeCusto);
    }

    public void aplicarImpactoFinanceiro(Despesa despesa) {
        CentroDeCusto centro = despesa.getCentroDeCusto();

        if (centro == null) {
            throw new IllegalStateException("Despesa sem Centro de Custo vinculado");
        }

        // garante que está pegando do banco (e não só o objeto “solto”)
        CentroDeCusto centroBanco = centroDeCustoRepository.findById(centro.getId())
                .orElseThrow(() -> new IllegalStateException("Centro de Custo não encontrado"));

        if (centroBanco.getSaldoDisponivel() < despesa.getValor()) {
            throw new IllegalStateException("Saldo insuficiente no Centro de Custo");
        }

        centroBanco.setSaldoDisponivel(centroBanco.getSaldoDisponivel() - despesa.getValor());
        centroDeCustoRepository.save(centroBanco);
    }
}
