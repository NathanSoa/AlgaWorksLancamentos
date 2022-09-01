package br.com.algaworks.api.lancamentosapi.Controller;

import br.com.algaworks.api.lancamentosapi.Model.Categoria;
import br.com.algaworks.api.lancamentosapi.Repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarNovaCategoria(@RequestBody Categoria categoria){
        iCategoriaRepository.save(categoria);
    }
}
