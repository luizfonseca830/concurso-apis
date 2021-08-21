package com.br.concurso.api.service;

import com.br.concurso.api.model.People;
import com.br.concurso.api.repository.PeopleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public People update(Long code, People people){
        People peopleSave = getPeopleForCode(code);
        BeanUtils.copyProperties(people, peopleSave, "code");
        return peopleRepository.save(peopleSave);
    }

    public void updatePropertyActive(Long code, boolean active){
        People peopleSave = getPeopleForCode(code);
        peopleSave.setActive(active);
        peopleRepository.save(peopleSave);
    }

    public People getPeopleForCode(Long code) {
        People peopleSave = peopleRepository.findById(code)
        .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return this.peopleRepository.save(peopleSave);
    }
}
