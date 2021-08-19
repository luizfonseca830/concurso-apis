package com.br.concurso.api.envent.listener;

import com.br.concurso.api.envent.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {
    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Long code = recursoCriadoEvent.getCode();

        addHeaderLocation(response, code);
    }

    private void addHeaderLocation(HttpServletResponse response, Long code) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(code).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
