package com.example.catalogo.domains.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.catalogo.domains.contracts.repositories.ActorRepository;
import com.example.catalogo.domains.contracts.services.ActorsService;
import com.example.catalogo.entities.Actor;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
public class ActorServiceImplTest {

    @MockBean
    ActorRepository dao;

    @Autowired
    ActorsService service;

    // @Test
    // void testGetAll_isNotEmpty() {
    // List<Actor> list = new ArrayList<>(Arrays.asList(
    // new Actor(1, "Pablo", "Gomez"),
    // new Actor(2, "Juan", "Perez")));
    // when(dao.findAll()).thenReturn(list);
    // var result = service.getAll();
    // assertThat(result.size()).isEqualTo(2);
    // verify(dao, times(1)).findAll();
    // }

}
