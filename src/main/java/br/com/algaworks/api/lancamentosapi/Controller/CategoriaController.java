package br.com.algaworks.api.lancamentosapi.Controller;

import br.com.algaworks.api.lancamentosapi.Event.RecursoCriadoEvent;
import br.com.algaworks.api.lancamentosapi.Model.Categoria;
import br.com.algaworks.api.lancamentosapi.Repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaRepository iCategoriaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Categoria> listarTodasCategorias(){
        return iCategoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrarNovaCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = iCategoriaRepository.save(categoria);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> procurarUmaCategoriaPeloCodigo(@PathVariable Long codigo){
        Categoria categoriaProcurada = iCategoriaRepository.findOne(codigo);
        return (categoriaProcurada != null) ? ResponseEntity.ok().body(categoriaProcurada) : ResponseEntity.notFound().build();
    }
}
