package com.br.concurso.api.resource;

import com.br.concurso.api.event.RecursoCriadoEvent;
import com.br.concurso.api.exceptionhandler.ConcursoExceptionHandler;
import com.br.concurso.api.model.Launch;
import com.br.concurso.api.repository.LaunchRepository;
import com.br.concurso.api.resource.filter.LaunchFilter;
import com.br.concurso.api.service.LaunchService;
import com.br.concurso.api.service.exception.NonexistentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/launches")
public class LaunchResource {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublishe;

    @Autowired
    private LaunchService launchService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Launch> search(LaunchFilter launchFilter) {
        return launchRepository.filter(launchFilter);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Optional<Launch>> searchForCode(@PathVariable Long code) {
        Optional<Launch> launch = launchRepository.findById(code);
        return launch.isPresent() ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Launch> create(@Valid @RequestBody Launch launch, HttpServletResponse response) {
        Launch launchSalve = launchService.save(launch);
        applicationEventPublishe.publishEvent(new RecursoCriadoEvent(this, response, launchSalve.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(launchSalve);
    }

    @ExceptionHandler({NonexistentOrInactivePersonException.class})
    public ResponseEntity<Object> handleNonexistentOrInactivePersonException(NonexistentOrInactivePersonException ex) {
        String messageUser = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString();
        List<ConcursoExceptionHandler.Error> erros = Collections.singletonList(new ConcursoExceptionHandler.Error(messageUser, messageDeveloper));
        return ResponseEntity.badRequest().body(erros);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long code){ this.launchRepository.deleteById(code);}

}
