package com.example.GerenciamentoDeDespesasReembolsos.repository;

import com.example.GerenciamentoDeDespesasReembolsos.model.HistoricoAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoAprovacaoRepository extends JpaRepository<HistoricoAprovacao, String> {

    List<HistoricoAprovacao> findByDespesaId(String despesaId);
}
