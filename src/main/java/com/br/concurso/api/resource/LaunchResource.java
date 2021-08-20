package com.br.concurso.api.resource;

import com.br.concurso.api.event.RecursoCriadoEvent;
import com.br.concurso.api.model.Launch;
import com.br.concurso.api.repository.LaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/launches")
public class LaunchResource {

    @Autowired
    private LaunchRepository launchRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublishe;

    @GetMapping
    public List<Launch> list(){return launchRepository.findAll();}

    @GetMapping("/{code}")
    public ResponseEntity<Optional<Launch>> searchForCode(@PathVariable Long code){
        Optional<Launch> launch = launchRepository.findById(code);
        return launch != null ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Launch> create(@Valid @RequestBody Launch launch, HttpServletResponse response){
        Launch launchSalve = launchRepository.save(launch);
        applicationEventPublishe.publishEvent(new RecursoCriadoEvent(this,response,launchSalve.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(launchSalve);
    }
}
