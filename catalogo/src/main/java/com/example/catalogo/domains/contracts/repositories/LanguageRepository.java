package com.example.catalogo.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.catalogo.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language> {
    List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp lastUpdate);
}
