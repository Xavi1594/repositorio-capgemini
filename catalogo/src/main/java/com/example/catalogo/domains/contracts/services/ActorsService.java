package com.example.catalogo.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.example.catalogo.domains.core.contracts.services.SpecificationDomainService;
import com.example.catalogo.domains.entities.Actor;

public interface ActorsService
        extends ProjectionDomainService<Actor, Integer>, SpecificationDomainService<Actor, Integer> {
    List<Actor> NewsDTO(Timestamp lastUpdate);

}
