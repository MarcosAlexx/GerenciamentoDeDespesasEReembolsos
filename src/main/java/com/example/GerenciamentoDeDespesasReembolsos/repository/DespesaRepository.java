package com.example.GerenciamentoDeDespesasReembolsos.repository;

import com.example.GerenciamentoDeDespesasReembolsos.model.Despesa;
import com.example.GerenciamentoDeDespesasReembolsos.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, String> {
    List<Despesa> findByFuncionario(Funcionario funcionario);
}
