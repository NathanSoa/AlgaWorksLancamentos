package br.com.algaworks.api.lancamentosapi.Controller;

import br.com.algaworks.api.lancamentosapi.Event.RecursoCriadoEvent;
import br.com.algaworks.api.lancamentosapi.ExceptionHandler.LancamentoApplicatonExceptionHandler;
import br.com.algaworks.api.lancamentosapi.Model.Lancamento;
import br.com.algaworks.api.lancamentosapi.Repository.Filter.LancamentoFilter;
import br.com.algaworks.api.lancamentosapi.Repository.ILancamentoRepository;
import br.com.algaworks.api.lancamentosapi.Service.Exception.PessoaInexistenteOuInativaException;
import br.com.algaworks.api.lancamentosapi.Service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoController {

    @Autowired
    private ILancamentoRepository iLancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Lancamento> pesquisarLancamentos(LancamentoFilter lancamentoFilter){
        return iLancamentoRepository.filtrar(lancamentoFilter);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscaLancamentoPeloCodigo(@PathVariable Long codigo){
        Lancamento lancamento = iLancamentoRepository.findOne(codigo);

        return (lancamento != null) ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> salvarNovaPessoa(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = lancamentoService.save(lancamento);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
    }
}
