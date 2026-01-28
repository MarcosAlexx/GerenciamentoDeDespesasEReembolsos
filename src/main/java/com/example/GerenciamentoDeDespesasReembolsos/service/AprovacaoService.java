package com.example.GerenciamentoDeDespesasReembolsos.service;

import com.example.GerenciamentoDeDespesasReembolsos.model.*;
import com.example.GerenciamentoDeDespesasReembolsos.repository.DespesaRepository;
import com.example.GerenciamentoDeDespesasReembolsos.repository.HistoricoAprovacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AprovacaoService {

    private final PoliticaFinanceiraService politicaFinanceiraService;
    private final FinanceiroService financeiroService;
    private final DespesaRepository despesaRepository;
    private final HistoricoAprovacaoRepository historicoRepository;

    public AprovacaoService(
            PoliticaFinanceiraService politicaFinanceiraService,
            FinanceiroService financeiroService,
            DespesaRepository despesaRepository,
            HistoricoAprovacaoRepository historicoRepository
    ) {
        this.politicaFinanceiraService = politicaFinanceiraService;
        this.financeiroService = financeiroService;
        this.despesaRepository = despesaRepository;
        this.historicoRepository = historicoRepository;
    }

    @Transactional
    public void aprovarDespesa(Despesa despesa, String responsavel) {

        if (despesa.getStatus() != StatusDespesa.PENDENTE) {
            throw new IllegalStateException("Despesa não está pendente");
        }

        politicaFinanceiraService.validarDespesaParaAprovacao(despesa);

        despesa.setStatus(StatusDespesa.APROVADA);
        despesaRepository.save(despesa);

        financeiroService.aplicarImpactoFinanceiro(despesa);

        historicoRepository.save(
                new HistoricoAprovacao(
                        LocalDate.now(),
                        StatusDespesa.APROVADA,
                        responsavel,
                        null,
                        despesa
                )
        );
    }

    @Transactional
    public void reprovarDespesa(Despesa despesa, String responsavel, String motivo) {

        if (despesa.getStatus() != StatusDespesa.PENDENTE) {
            throw new IllegalStateException("Despesa não está pendente");
        }

        despesa.setStatus(StatusDespesa.REJEITADA);
        despesaRepository.save(despesa);

        historicoRepository.save(
                new HistoricoAprovacao(
                        LocalDate.now(),
                        StatusDespesa.REJEITADA,
                        responsavel,
                        motivo,
                        despesa
                )
        );
    }
}
