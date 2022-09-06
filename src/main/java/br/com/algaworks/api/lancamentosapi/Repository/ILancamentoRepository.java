package br.com.algaworks.api.lancamentosapi.Repository;

import br.com.algaworks.api.lancamentosapi.Model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILancamentoRepository extends JpaRepository<Lancamento, Long> {

}
