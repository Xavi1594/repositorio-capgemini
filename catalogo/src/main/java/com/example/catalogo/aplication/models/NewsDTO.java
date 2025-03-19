package com.example.catalogo.aplication.models;

import java.util.List;

import com.example.catalogo.domains.entities.Category;
import com.example.catalogo.domains.entities.Language;
import com.example.catalogo.domains.entities.models.ActorDTO;
import com.example.catalogo.domains.entities.models.FilmShortDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {
    private List<FilmShortDTO> films;
    private List<ActorDTO> actors;
    private List<Category> categories;
    private List<Language> languages;
}
