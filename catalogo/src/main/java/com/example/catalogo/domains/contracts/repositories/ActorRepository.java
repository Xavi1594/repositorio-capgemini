package com.example.catalogo.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogo.entities.Actor;

public interface ActorRepository extends JpaRepository <Actor, Integer>{

}
