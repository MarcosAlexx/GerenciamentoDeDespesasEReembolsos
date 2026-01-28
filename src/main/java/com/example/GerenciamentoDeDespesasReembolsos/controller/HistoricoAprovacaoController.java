package com.example.GerenciamentoDeDespesasReembolsos.controller;

import com.example.GerenciamentoDeDespesasReembolsos.model.HistoricoAprovacao;
import com.example.GerenciamentoDeDespesasReembolsos.repository.HistoricoAprovacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico-aprovacoes")
public class HistoricoAprovacaoController {

    private final HistoricoAprovacaoRepository historicoAprovacaoRepository;

    public HistoricoAprovacaoController(HistoricoAprovacaoRepository historicoAprovacaoRepository) {
        this.historicoAprovacaoRepository = historicoAprovacaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<HistoricoAprovacao>> listar() {
        return ResponseEntity.ok(historicoAprovacaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoAprovacao> buscarPorId(@PathVariable String id) {
        return historicoAprovacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/despesa/{despesaId}")
    public ResponseEntity<List<HistoricoAprovacao>> buscarPorDespesa(@PathVariable String despesaId) {
        // Você precisará adicionar este método no repository
        // List<HistoricoAprovacao> findByDespesaId(String despesaId);
        return ResponseEntity.ok(historicoAprovacaoRepository.findAll()); // Temporário
    }
}