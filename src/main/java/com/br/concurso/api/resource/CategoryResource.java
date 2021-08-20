package com.br.concurso.api.resource;

import com.br.concurso.api.event.RecursoCriadoEvent;
import com.br.concurso.api.model.Category;
import com.br.concurso.api.repository.CategoryRepository;
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
@RequestMapping("/categorys")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    //Disparando um evento no spring, evento que criei
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response) {
        Category categorySave = categoryRepository.save(category);
        //publicando o evento criado
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categorySave.getCode()));
        /*Devolver a categoria que foi criada*/
        return ResponseEntity.status(HttpStatus.CREATED).body(categorySave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Optional<Category>> searchForCode(@PathVariable Long code) {
        Optional<Category> category = categoryRepository.findById(code);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();

    }

}
