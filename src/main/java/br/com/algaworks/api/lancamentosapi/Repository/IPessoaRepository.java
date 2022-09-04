package br.com.algaworks.api.lancamentosapi.Repository;

import br.com.algaworks.api.lancamentosapi.Model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPessoaRepository extends JpaRepository<Pessoa, Long> {

}
