package com.br.concurso.api.repository;

import com.br.concurso.api.model.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRepository extends JpaRepository<Launch, Long> {
}
