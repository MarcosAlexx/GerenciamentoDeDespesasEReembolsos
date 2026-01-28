package com.example.GerenciamentoDeDespesasReembolsos.service;

import com.example.GerenciamentoDeDespesasReembolsos.model.Funcionario;
import com.example.GerenciamentoDeDespesasReembolsos.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public Funcionario criarFuncionario(Funcionario funcionario) {

        validarDadosFuncionario(funcionario);

        if (funcionario.getSaldoReembolsoDisponivel() == 0) {
            funcionario.setSaldoReembolsoDisponivel(funcionario.getLimiteReembolsoMensal());
        }

        return funcionarioRepository.save(funcionario);
    }

    public Optional<Funcionario> buscarFuncionarioPorId(String id) {
        return funcionarioRepository.findById(id);
    }


    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }


    @Transactional
    public Optional<Funcionario> atualizarFuncionario(String id, Funcionario funcionarioAtualizado) {
        return funcionarioRepository.findById(id).map(funcionario -> {

            if (funcionarioAtualizado.getNome() != null && !funcionarioAtualizado.getNome().isBlank()) {
                funcionario.setNome(funcionarioAtualizado.getNome());
            }
            if (funcionarioAtualizado.getCargo() != null && !funcionarioAtualizado.getCargo().isBlank()) {
                funcionario.setCargo(funcionarioAtualizado.getCargo());
            }
            if (funcionarioAtualizado.getLimiteReembolsoMensal() > 0) {
                funcionario.setLimiteReembolsoMensal(funcionarioAtualizado.getLimiteReembolsoMensal());
            }

            return funcionarioRepository.save(funcionario);
        });
    }

    @Transactional
    public boolean deletarFuncionario(String id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Funcionario atualizarSaldoReembolso(String id, double novoSaldo) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (novoSaldo < 0) {
            throw new IllegalArgumentException("Saldo não pode ser negativo");
        }
        if (novoSaldo > funcionario.getLimiteReembolsoMensal()) {
            throw new IllegalArgumentException("Saldo não pode ser maior que o limite mensal");
        }

        funcionario.setSaldoReembolsoDisponivel(novoSaldo);
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public Funcionario debitarSaldo(String funcionarioId, double valor) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }

        double novoSaldo = funcionario.getSaldoReembolsoDisponivel() - valor;

        if (novoSaldo < 0) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente. Disponível: R$ " + funcionario.getSaldoReembolsoDisponivel()
            );
        }

        funcionario.setSaldoReembolsoDisponivel(novoSaldo);
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public Funcionario creditarSaldo(String funcionarioId, double valor) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }

        double novoSaldo = funcionario.getSaldoReembolsoDisponivel() + valor;

        if (novoSaldo > funcionario.getLimiteReembolsoMensal()) {
            novoSaldo = funcionario.getLimiteReembolsoMensal();
        }

        funcionario.setSaldoReembolsoDisponivel(novoSaldo);
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void resetarSaldosMensais() {
        List<Funcionario> todos = funcionarioRepository.findAll();

        for (Funcionario funcionario : todos) {
            funcionario.setSaldoReembolsoDisponivel(funcionario.getLimiteReembolsoMensal());
            funcionarioRepository.save(funcionario);
        }
    }

    private void validarDadosFuncionario(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().isBlank()) {
            throw new IllegalArgumentException("Cargo é obrigatório");
        }
        if (funcionario.getLimiteReembolsoMensal() <= 0) {
            throw new IllegalArgumentException("Limite de reembolso deve ser maior que zero");
        }
    }
}