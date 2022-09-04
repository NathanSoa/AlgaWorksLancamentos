package br.com.algaworks.api.lancamentosapi.Controller;

import br.com.algaworks.api.lancamentosapi.Model.Pessoa;
import br.com.algaworks.api.lancamentosapi.Repository.IPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @GetMapping
    public List<Pessoa> listaTodasAsPessoas(){
        return iPessoaRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> listarPessoaPeloCodigo(@PathVariable long codigo){
        Pessoa pessoaBuscada = iPessoaRepository.findOne(codigo);
        return (pessoaBuscada != null) ? ResponseEntity.ok().body(pessoaBuscada) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrarNovaPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        Pessoa pessoaCadastrada = iPessoaRepository.save(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(pessoaCadastrada.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(pessoaCadastrada);
    }
}
