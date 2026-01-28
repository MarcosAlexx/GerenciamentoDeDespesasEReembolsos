package com.example.GerenciamentoDeDespesasReembolsos.controller;

import com.example.GerenciamentoDeDespesasReembolsos.model.Despesa;
import com.example.GerenciamentoDeDespesasReembolsos.service.DespesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    public ResponseEntity<Despesa> criar(@RequestBody Despesa despesa) {
        Despesa salva = despesaService.criarDespesa(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<List<Despesa>> listar() {
        return ResponseEntity.ok(despesaService.listarDespesas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> buscarPorId(@PathVariable String id) {
        return despesaService.buscarDespesaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<Despesa>> buscarPorFuncionario(@PathVariable String funcionarioId) {
        return ResponseEntity.ok(despesaService.buscarDespesasPorFuncionario(funcionarioId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Despesa> atualizar(@PathVariable String id, @RequestBody Despesa despesa) {
        return despesaService.atualizarDespesa(id, despesa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        boolean deletado = despesaService.deletarDespesa(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}