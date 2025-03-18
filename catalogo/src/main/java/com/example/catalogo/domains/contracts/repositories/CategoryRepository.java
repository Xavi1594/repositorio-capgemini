package com.example.catalogo.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.catalogo.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    List<Category> findAllByOrderByName();

    List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp lastUpdate);
}
