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
        Pessoa pessoaSalva = iPessoaRepository.findOne(codigo);
        if(pessoaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return iPessoaRepository.save(pessoaSalva);
    }
}
