package br.com.algaworks.api.lancamentosapi.Service;

import br.com.algaworks.api.lancamentosapi.Model.Lancamento;
import br.com.algaworks.api.lancamentosapi.Model.Pessoa;
import br.com.algaworks.api.lancamentosapi.Repository.ILancamentoRepository;
import br.com.algaworks.api.lancamentosapi.Repository.IPessoaRepository;
import br.com.algaworks.api.lancamentosapi.Service.Exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private IPessoaRepository iPessoaRepository;

    @Autowired
    private ILancamentoRepository iLancamentoRepository;

    public Lancamento save(Lancamento lancamento) {
        Pessoa pessoa = iPessoaRepository.findOne(lancamento.getPessoa().getCodigo());
        if(pessoa == null || pessoa.estaInativa()){
            throw new PessoaInexistenteOuInativaException();
        }
        return iLancamentoRepository.save(lancamento);
    }
}
