package br.com.algaworks.api.lancamentosapi.Repository.Lancamento;

import br.com.algaworks.api.lancamentosapi.Model.Lancamento;
import br.com.algaworks.api.lancamentosapi.Model.Lancamento_;
import br.com.algaworks.api.lancamentosapi.Repository.Filter.LancamentoFilter;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(descricaoNaoEstaNula(lancamentoFilter)){
            predicates.add(builder.like(
                    builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }

        if(dataVencimentoDeNaoEstaNula(lancamentoFilter)){
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
        }

        if(dataVencimentoAteNaoEstaNula(lancamentoFilter)){
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private Boolean descricaoNaoEstaNula(LancamentoFilter lancamentoFilter){
        return !ObjectUtils.isEmpty(lancamentoFilter.getDescricao());
    }

    private Boolean dataVencimentoDeNaoEstaNula(LancamentoFilter lancamentoFilter){
        return lancamentoFilter.getDataVencimentoDe() != null;
    }

    private Boolean dataVencimentoAteNaoEstaNula(LancamentoFilter lancamentoFilter){
        return lancamentoFilter.getDataVencimentoAte() != null;
    }
}
