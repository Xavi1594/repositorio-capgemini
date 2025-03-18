package com.example.catalogo.aplication.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.catalogo.aplication.contracts.CatalogoService;
import com.example.catalogo.aplication.models.NewsDTO;
import com.example.catalogo.domains.contracts.services.ActorsService;
import com.example.catalogo.domains.contracts.services.CategoriesService;
import com.example.catalogo.domains.contracts.services.FilmService;
import com.example.catalogo.domains.contracts.services.LanguageService;

@Service
public class CatalogoServiceImpl implements CatalogoService {
    @Autowired
    private FilmService filmSrv;
    @Autowired
    private ActorsService actorSrv;
    @Autowired
    private CategoriesService categorySrv;
    @Autowired
    private LanguageService languageSrv;

    @Override
    public NewsDTO newsDTO(Timestamp lastUpdate) {
        // Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
        if (lastUpdate == null)
            lastUpdate = Timestamp.from(Instant.now().minusSeconds(36000));
        return new NewsDTO(
                filmSrv.newsDTO(lastUpdate).stream().map(item -> new FilmShortDTO(item.getFilmId(), item.getTitle()))
                        .toList(),
                actorSrv.NewsDTO(lastUpdate).stream()
                        .map(item -> new ActorShortDTO(item.getActorId(), item.getFirstName(), item.getLastName())),
                categorySrv.NewsDTO(lastUpdate),
                languageSrv.NewsDTO(lastUpdate));
    }

}
