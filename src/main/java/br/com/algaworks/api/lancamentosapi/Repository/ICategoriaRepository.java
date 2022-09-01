package br.com.algaworks.api.lancamentosapi.Repository;

import br.com.algaworks.api.lancamentosapi.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

}
