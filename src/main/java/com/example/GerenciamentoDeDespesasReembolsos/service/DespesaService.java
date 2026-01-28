package com.example.GerenciamentoDeDespesasReembolsos.service;

import com.example.GerenciamentoDeDespesasReembolsos.model.*;
import com.example.GerenciamentoDeDespesasReembolsos.repository.CentroDeCustoRepository;
import com.example.GerenciamentoDeDespesasReembolsos.repository.DespesaRepository;
import com.example.GerenciamentoDeDespesasReembolsos.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final CentroDeCustoRepository centroDeCustoRepository;

    public DespesaService(
            DespesaRepository despesaRepository,
            FuncionarioRepository funcionarioRepository,
            CentroDeCustoRepository centroDeCustoRepository
    ) {
        this.despesaRepository = despesaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.centroDeCustoRepository = centroDeCustoRepository;
    }

    // ===== CRIAR DESPESA (método original - mantido) =====
    @Transactional
    public Despesa criarDespesa(
            double valor,
            TipoDespesa tipoDespesa,
            String descricao,
            Funcionario funcionario,
            CentroDeCusto centroDeCusto
    ) {
        validarDadosDespesa(valor, tipoDespesa, descricao, funcionario, centroDeCusto);

        Despesa despesa = new Despesa(
                valor,
                tipoDespesa,
                LocalDate.now(),
                StatusDespesa.PENDENTE,
                descricao,
                funcionario,
                centroDeCusto
        );

        return despesaRepository.save(despesa);
    }

    // ===== CRIAR DESPESA (para API REST - novo método) =====
    @Transactional
    public Despesa criarDespesa(Despesa despesa) {
        // Busca e valida funcionário
        if (despesa.getFuncionario() != null && despesa.getFuncionario().getId() != null) {
            Funcionario funcionario = funcionarioRepository.findById(despesa.getFuncionario().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
            despesa.setFuncionario(funcionario);
        }

        // Busca e valida centro de custo
        if (despesa.getCentroDeCusto() != null && despesa.getCentroDeCusto().getId() != null) {
            CentroDeCusto centro = centroDeCustoRepository.findById(despesa.getCentroDeCusto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Centro de custo não encontrado"));
            despesa.setCentroDeCusto(centro);
        }

        // Valida dados
        validarDadosDespesa(
                despesa.getValor(),
                despesa.getTipo(),
                despesa.getDescricao(),
                despesa.getFuncionario(),
                despesa.getCentroDeCusto()
        );

        // Define valores padrão
        if (despesa.getDataCriacao() == null) {
            despesa.setDataCriacao(LocalDate.now());
        }
        if (despesa.getStatus() == null) {
            despesa.setStatus(StatusDespesa.PENDENTE);
        }

        return despesaRepository.save(despesa);
    }

    // ===== BUSCAR POR ID (retorna Optional agora) =====
    public Optional<Despesa> buscarDespesaPorId(String id) {
        return despesaRepository.findById(id);
    }

    // ===== LISTAR TODAS =====
    public List<Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    // ===== BUSCAR POR FUNCIONÁRIO =====
    public List<Despesa> buscarDespesasPorFuncionario(String funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        return despesaRepository.findByFuncionario(funcionario);
    }


    // ===== ATUALIZAR DESPESA =====
    @Transactional
    public Optional<Despesa> atualizarDespesa(String id, Despesa despesaAtualizada) {
        return despesaRepository.findById(id).map(despesa -> {
            if (despesaAtualizada.getDescricao() != null) {
                despesa.setDescricao(despesaAtualizada.getDescricao());
            }
            if (despesaAtualizada.getValor() > 0) {
                despesa.setValor(despesaAtualizada.getValor());
            }
            if (despesaAtualizada.getTipo() != null) {
                despesa.setTipo(despesaAtualizada.getTipo());
            }

            return despesaRepository.save(despesa);
        });
    }

    // ===== DELETAR DESPESA =====
    @Transactional
    public boolean deletarDespesa(String id) {
        if (despesaRepository.existsById(id)) {
            despesaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ===== VALIDAÇÃO PRIVADA =====
    private void validarDadosDespesa(
            double valor,
            TipoDespesa tipoDespesa,
            String descricao,
            Funcionario funcionario,
            CentroDeCusto centroDeCusto
    ) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor da despesa deve ser maior que zero");
        }
        if (tipoDespesa == null) {
            throw new IllegalArgumentException("Tipo de despesa é obrigatório");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário é obrigatório");
        }
        if (centroDeCusto == null) {
            throw new IllegalArgumentException("Centro de custo é obrigatório");
        }
    }
}