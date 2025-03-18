package com.example.catalogo.aplication.models;

import java.util.List;

import com.example.catalogo.entities.Category;
import com.example.catalogo.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewsDTO {
    private List<FilmShortDTO> films;
    private List<ActorDTO> actors;
    private List<Category> categories;
    private List<Language> languages;
}
