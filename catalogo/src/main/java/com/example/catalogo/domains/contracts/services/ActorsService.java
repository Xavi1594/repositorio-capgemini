package com.example.catalogo.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.catalogo.domains.core.contracts.services.DomainService;
import com.example.catalogo.domains.entities.Actor;

public interface ActorsService extends DomainService<Actor, Integer> {
    List<Actor> NewsDTO(Timestamp lastUpdate);
}
