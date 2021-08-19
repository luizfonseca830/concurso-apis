package com.br.concurso.api.resource;

import com.br.concurso.api.envent.RecursoCriadoEvent;
import com.br.concurso.api.model.Responsibility;
import com.br.concurso.api.repository.ResponsibilityRepository;
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
@RequestMapping("/responsibilities")
public class ResponsibilityResource {

    @Autowired
    ResponsibilityRepository responsibilityRepository;

    //    Disparando um evento no spring, evento que criei
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Responsibility> list() {
        return responsibilityRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Responsibility> create(@Valid @RequestBody Responsibility responsibility, HttpServletResponse response) {
        Responsibility responsibilitySave = responsibilityRepository.save(responsibility);

        //publicando o evento criado
        publisher.publishEvent(new RecursoCriadoEvent(this, response, responsibilitySave.getCode()));

        /*Devolver a responsabilidade que foi criada*/
        return ResponseEntity.status(HttpStatus.CREATED).body(responsibilitySave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Optional<Responsibility>> searchForCode(@PathVariable Long code) {
        Optional<Responsibility> responsibility = responsibilityRepository.findById(code);
        return responsibility != null ? ResponseEntity.ok(responsibility) : ResponseEntity.notFound().build();

    }


}
