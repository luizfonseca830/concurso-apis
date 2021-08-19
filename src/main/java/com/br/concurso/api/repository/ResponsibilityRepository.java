package com.br.concurso.api.repository;

import com.br.concurso.api.model.Responsibility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsibilityRepository extends JpaRepository<Responsibility, Long> {
}
