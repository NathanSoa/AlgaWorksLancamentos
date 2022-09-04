package br.com.algaworks.api.lancamentosapi.Service;

import br.com.algaworks.api.lancamentosapi.Model.Pessoa;
import br.com.algaworks.api.lancamentosapi.Repository.IPessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private IPessoaRepository iPessoaRepository;

    public Pessoa Atualizar(long codigo, Pessoa pessoa){
        Pessoa pessoaSalva = getPessoaPeloCodigo(codigo);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return iPessoaRepository.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(long codigo, Boolean ativo) {
        Pessoa pessoaSalva = getPessoaPeloCodigo(codigo);
        pessoaSalva.setAtivo(ativo);
        iPessoaRepository.save(pessoaSalva);
    }

    private Pessoa getPessoaPeloCodigo(long codigo) {
        Pessoa pessoaSalva = iPessoaRepository.findOne(codigo);
        if(pessoaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }
}
