package com.example.catalogo.domains.contracts.services;

import java.sql.Timestamp;

import java.util.List;

import com.example.catalogo.domains.core.contracts.services.DomainService;
import com.example.catalogo.domains.entities.Language;

public interface LanguageService extends DomainService<Language, Integer> {
    List<Language> newsDTO(Timestamp lastUpdate);
}
