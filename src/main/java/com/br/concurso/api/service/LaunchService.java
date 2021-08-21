package com.br.concurso.api.service;

import com.br.concurso.api.model.Launch;
import com.br.concurso.api.model.People;
import com.br.concurso.api.repository.LaunchRepository;
import com.br.concurso.api.repository.PeopleRepository;
import com.br.concurso.api.service.exception.NonexistentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaunchService {

    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private LaunchRepository launchRepository;

    public Launch save(Launch launch) {
        Optional<People> people = peopleRepository.findById(launch.getPeople().getCode());
        if (!people.isPresent() || people.get().isInactive()) {
            throw new NonexistentOrInactivePersonException();
        }
        return launchRepository.save(launch);
    }
}
