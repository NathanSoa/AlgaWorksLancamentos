package br.com.algaworks.api.lancamentosapi.Controller;

import br.com.algaworks.api.lancamentosapi.Model.Lancamento;
import br.com.algaworks.api.lancamentosapi.Repository.ILancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoController {

    @Autowired
    private ILancamentoRepository iLancamentoRepository;

    @GetMapping
    public List<Lancamento> listaTodosOsLancamentos(){
        return iLancamentoRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscaLancamentoPeloCodigo(@PathVariable Long codigo){
        Lancamento lancamento = iLancamentoRepository.findOne(codigo);

        return (lancamento != null) ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }
}
