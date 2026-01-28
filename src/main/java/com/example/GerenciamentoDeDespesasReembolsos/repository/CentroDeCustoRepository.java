package com.example.GerenciamentoDeDespesasReembolsos.repository;

import com.example.GerenciamentoDeDespesasReembolsos.model.CentroDeCusto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto, String> {
    boolean existsByNome(String nome);

    Optional<CentroDeCusto> findByNome(String rh);
}
