package com.br.concurso.api.resource;

import com.br.concurso.api.model.Launch;
import com.br.concurso.api.repository.LaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/launches")
public class LaunchResource {

    @Autowired
    private LaunchRepository launchRepository;

    @GetMapping
    public List<Launch> list(){return launchRepository.findAll();}

    @GetMapping("/{code}")
    public ResponseEntity<Optional<Launch>> searchForCode(@PathVariable Long code){
        Optional<Launch> launch = launchRepository.findById(code);
        return launch != null ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build();
    }
}
