package com.br.concurso.api.repository.launch;

import com.br.concurso.api.model.Launch;
import com.br.concurso.api.resource.filter.LaunchFilter;

import java.util.List;

public interface LaunchRepositoryQuery {
    List<Launch> filter(LaunchFilter launchFilter);
}
