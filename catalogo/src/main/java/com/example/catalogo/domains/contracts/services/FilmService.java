package com.example.catalogo.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.example.catalogo.domains.core.contracts.services.SpecificationDomainService;
import com.example.catalogo.domains.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer>, SpecificationDomainService<Film, Integer> {
    List<Film> newsDTO(Timestamp lastUpdate);
}
