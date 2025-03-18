package com.example.catalogo.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.catalogo.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
    @Query(value = "SELECT a FROM Actor a WHERE a.actorId > ?1")
    List<Actor> findByActorIdGreaterThan(int id);

    List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp lastUpdate);
}
