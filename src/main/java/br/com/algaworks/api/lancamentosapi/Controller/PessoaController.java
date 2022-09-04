package br.com.algaworks.api.lancamentosapi.Controller;

import br.com.algaworks.api.lancamentosapi.Event.RecursoCriadoEvent;
import br.com.algaworks.api.lancamentosapi.Model.Pessoa;
import br.com.algaworks.api.lancamentosapi.Repository.IPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

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

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaCadastrada.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCadastrada);
    }
}
