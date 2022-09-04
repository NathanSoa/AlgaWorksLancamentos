package br.com.algaworks.api.lancamentosapi.Controller;

import br.com.algaworks.api.lancamentosapi.Model.Categoria;
import br.com.algaworks.api.lancamentosapi.Repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaRepository iCategoriaRepository;

    @GetMapping
    public List<Categoria> listarTodasCategorias(){
        return iCategoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrarNovaCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = iCategoriaRepository.save(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(categoriaSalva.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> procurarUmaCategoriaPeloCodigo(@PathVariable Long codigo){
        Categoria categoriaProcurada = iCategoriaRepository.findOne(codigo);
        return (categoriaProcurada != null) ? ResponseEntity.ok().body(categoriaProcurada) : ResponseEntity.notFound().build();
    }
}
