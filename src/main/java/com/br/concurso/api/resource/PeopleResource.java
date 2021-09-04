package com.br.concurso.api.resource;

import com.br.concurso.api.event.RecursoCriadoEvent;
import com.br.concurso.api.model.People;
import com.br.concurso.api.repository.PeopleRepository;
import com.br.concurso.api.service.PeopleService;
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
@RequestMapping("/peoples")
public class PeopleResource {

    @Autowired
    private PeopleRepository peopleRepository;

    //Disparando um evento no spring, evento que criei
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    PeopleService peopleService;

    @GetMapping
    public List<People> list() {
        return peopleRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<People> create(@Valid @RequestBody People people, HttpServletResponse response) {
        People peopleSave = peopleRepository.save(people);

        //publicando o evento criado
        publisher.publishEvent(new RecursoCriadoEvent(this, response, peopleSave.getCode()));
        /*Devolver a pessoa que foi criada*/
        return ResponseEntity.status(HttpStatus.CREATED).body(peopleSave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Optional<People>> searchForCode(@PathVariable Long code) {
        Optional<People> people = peopleRepository.findById(code);
        return people.isPresent() ? ResponseEntity.ok(people) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long code) {
        this.peopleRepository.deleteById(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<People> update(@PathVariable Long code, @Valid @RequestBody People people) {
        People peopleSave= peopleService.update(code,people);
        return ResponseEntity.ok(peopleSave);
    }

    @PutMapping("/{code}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePropertyActive(@PathVariable Long code, @RequestBody Boolean active){
        peopleService.updatePropertyActive(code, active);
    }
}
