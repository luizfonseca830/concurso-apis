package com.br.concurso.api.repository;

import com.br.concurso.api.model.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Long> {

}
