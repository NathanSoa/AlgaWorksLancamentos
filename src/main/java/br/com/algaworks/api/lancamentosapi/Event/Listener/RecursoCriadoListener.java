package br.com.algaworks.api.lancamentosapi.Event.Listener;

import br.com.algaworks.api.lancamentosapi.Event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriado) {
        HttpServletResponse response = recursoCriado.getResponse();
        Long codigo = recursoCriado.getCodigo();

        adiconarHeaderLocation(response, codigo);
    }

    private void adiconarHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
