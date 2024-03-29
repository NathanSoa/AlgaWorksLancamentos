package br.com.algaworks.api.lancamentosapi.ExceptionHandler;

import br.com.algaworks.api.lancamentosapi.Service.Exception.PessoaInexistenteOuInativaException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class LancamentoApplicatonExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;
    private String mensagemParaDesenvolvedor = "";
    private String mensagemParaUsuario ="";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Erro> erros;
        mensagemParaUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        mensagemParaDesenvolvedor = (ex.getCause() != null) ? ex.getCause().toString() : ex.toString();
        erros = Arrays.asList(new Erro(mensagemParaUsuario, mensagemParaDesenvolvedor));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Erro> erros;
        erros = criaListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityVolationException(DataIntegrityViolationException ex, WebRequest request){
        List<Erro> erros;
        mensagemParaUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
        mensagemParaDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
        erros = Arrays.asList(new Erro(mensagemParaUsuario, mensagemParaDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
        List<Erro> erros;
        mensagemParaUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
        mensagemParaDesenvolvedor = ex.toString();
        erros = Arrays.asList(new Erro(mensagemParaUsuario, mensagemParaDesenvolvedor));
       return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex, WebRequest request){
        List<LancamentoApplicatonExceptionHandler.Erro> erros;
        mensagemParaUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        mensagemParaDesenvolvedor = ex.toString();
        erros = Arrays.asList(new LancamentoApplicatonExceptionHandler.Erro(mensagemParaUsuario, mensagemParaDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> criaListaDeErros(BindingResult bindingResult){
        List<Erro> erros = new ArrayList<>();
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            mensagemParaUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            mensagemParaDesenvolvedor = fieldError.toString();
            erros.add(new Erro(mensagemParaUsuario, mensagemParaDesenvolvedor));
        }
        return erros;
    }

    private static class Erro {
        private String mensagemUsuario;
        private String mensagemDesenvolvedor;

        public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemDesenvolvedor = mensagemDesenvolvedor;
        }

        public String getMensagemUsuario() {
            return mensagemUsuario;
        }

        public String getMensagemDesenvolvedor() {
            return mensagemDesenvolvedor;
        }
    }
}
