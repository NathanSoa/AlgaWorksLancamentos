package br.com.algaworks.api.lancamentosapi.Event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class RecursoCriadoEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private HttpServletResponse response;
    private long codigo;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
        super(source);
        this.codigo = codigo;
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public long getCodigo() {
        return codigo;
    }
}
