package com.br.concurso.api.resource;

import com.br.concurso.api.envent.RecursoCriadoEvent;
import com.br.concurso.api.model.Schooling;
import com.br.concurso.api.repository.SchoolingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/{schooling}")
public class SchoolingResource {

    @Autowired
    SchoolingRepository schoolingRepository;

    //    Disparando um evento no spring, evento que criei
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Schooling> list() {
        return schoolingRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Schooling> create(@Valid @RequestBody Schooling schooling, HttpServletResponse response) {
        Schooling schoolingSave = schoolingRepository.save(schooling);

        //publicando o evento criado
        publisher.publishEvent(new RecursoCriadoEvent(this, response, schoolingSave.getCode()));

        /*Devolver a escolaridade que foi criada*/
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolingSave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Optional<Schooling>> searchForCode(@PathVariable Long code) {
        Optional<Schooling> schooling = schoolingRepository.findById(code);
        return schooling != null ? ResponseEntity.ok(schooling) : ResponseEntity.notFound().build();
    }
}
