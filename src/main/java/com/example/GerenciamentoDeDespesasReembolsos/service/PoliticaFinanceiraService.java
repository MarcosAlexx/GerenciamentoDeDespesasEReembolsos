package com.example.GerenciamentoDeDespesasReembolsos.service;

import com.example.GerenciamentoDeDespesasReembolsos.model.Despesa;
import com.example.GerenciamentoDeDespesasReembolsos.model.TipoDespesa;
import org.springframework.stereotype.Service;

@Service
public class PoliticaFinanceiraService {

    private static final double VALOR_MAXIMO_DESPESA = 10_000.00;

    public void validarDespesaParaAprovacao(Despesa despesa) {
        validarValorMaximo(despesa);
        validarTipoDespesa(despesa);
        validarLimiteFuncionario(despesa);
    }

    private void validarValorMaximo(Despesa despesa) {
        if (despesa.getValor() > VALOR_MAXIMO_DESPESA) {
            throw new IllegalStateException("Valor da despesa excede o limite permitido");
        }
    }

    private void validarLimiteFuncionario(Despesa despesa) {
        if (despesa.getValor() > despesa.getFuncionario().getLimiteReembolsoMensal()) {
            throw new IllegalStateException("Limite mensal de reembolso do funcionário excedido");
        }
    }

    private void validarTipoDespesa(Despesa despesa) {
        TipoDespesa tipoDespesa = despesa.getTipo();

        if (tipoDespesa != TipoDespesa.REEMBOLSAVEL &&
                tipoDespesa != TipoDespesa.CARTAO_CORPORATIVO) {

            throw new IllegalStateException("Tipo de despesa não permitido para aprovação");
        }
    }
}
