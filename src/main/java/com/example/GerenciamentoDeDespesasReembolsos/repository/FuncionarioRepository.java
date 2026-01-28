package com.example.GerenciamentoDeDespesasReembolsos.repository;

import com.example.GerenciamentoDeDespesasReembolsos.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {
}
