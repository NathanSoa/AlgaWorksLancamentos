package br.com.algaworks.api.lancamentosapi.Repository.Lancamento;

import br.com.algaworks.api.lancamentosapi.Model.Lancamento;
import br.com.algaworks.api.lancamentosapi.Repository.Filter.LancamentoFilter;

import java.util.List;

public interface ILancamentoRepositoryQuery {
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
