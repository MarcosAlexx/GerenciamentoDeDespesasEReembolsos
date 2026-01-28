package com.example.GerenciamentoDeDespesasReembolsos.runner;

import com.example.GerenciamentoDeDespesasReembolsos.model.CentroDeCusto;
import com.example.GerenciamentoDeDespesasReembolsos.repository.CentroDeCustoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Runner implements CommandLineRunner {

    private final CentroDeCustoRepository centroDeCustoRepository;

    public Runner(CentroDeCustoRepository centroDeCustoRepository) {
        this.centroDeCustoRepository = centroDeCustoRepository;
    }

    @Override
    public void run(String... args) {
        CentroDeCusto rh = centroDeCustoRepository.findByNome("RH")
                .orElseGet(() -> {
                    CentroDeCusto novo = new CentroDeCusto(
                            "RH",
                            10000.0,
                            10000.0
                    );
                    return centroDeCustoRepository.save(novo);
                });

        System.out.println("Centro de custo RH: " + rh.getId());
    }
}
