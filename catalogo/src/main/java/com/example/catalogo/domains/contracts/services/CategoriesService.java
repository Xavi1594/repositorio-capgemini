package com.example.catalogo.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.catalogo.domains.core.contracts.services.DomainService;
import com.example.catalogo.domains.entities.Category;

public interface CategoriesService extends DomainService<Category, Integer> {
    List<Category> newsDTO(Timestamp lastUpdate);
}
