package com.example.GerenciamentoDeDespesasReembolsos.controller;

import com.example.GerenciamentoDeDespesasReembolsos.model.CentroDeCusto;
import com.example.GerenciamentoDeDespesasReembolsos.service.FinanceiroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/centros-de-custo")
public class CentroDeCustoController {

    private final FinanceiroService financeiroService;

    public CentroDeCustoController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    @PostMapping
    public ResponseEntity<CentroDeCusto> criar(@RequestBody CentroDeCusto centroDeCusto) {
        CentroDeCusto salvo = financeiroService.criarCentroDeCusto(centroDeCusto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
