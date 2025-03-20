package com.example.catalogo.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;

import com.example.catalogo.domains.core.entities.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * The persistent class for the language database table.
 * 
 */
@SuppressWarnings("rawtypes")
@Entity
@Table(name = "language")
@NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l")
public class Language extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Partial {
	}

	public static class Complete extends Partial {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Language.Partial.class)
	@Column(name = "language_id", insertable = false, updatable = false, unique = true, nullable = false)
	private int languageId;

	@Column(name = "last_update", insertable = false, updatable = false, nullable = false)
	@PastOrPresent
	@JsonView(Language.Complete.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonProperty("ultimaModificacion")
	private Timestamp lastUpdate;

	public Language(int languageId, String name) {
		this.languageId = languageId;
		this.name = name;
	}

	@Column(nullable = false, length = 20)
	@NotBlank
	@Size(max = 20)
	@JsonProperty("idioma")
	@JsonView(Language.Partial.class)
	@Pattern(regexp = "^[A-ZÁÉÍÓÚÑÜ]*$", message = "El nombre debe estar en mayúsculas")
	private String name;

	// bi-directional many-to-one association to Film
	@OneToMany(mappedBy = "language")
	private List<Film> films;

	// bi-directional many-to-one association to Film
	@OneToMany(mappedBy = "languageVO")
	private List<Film> filmsVO;

	public Language() {
	}

	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilms() {
		return this.films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	public Film addFilm(Film film) {
		getFilms().add(film);
		film.setLanguage(this);

		return film;
	}

	public Film removeFilm(Film film) {
		getFilms().remove(film);
		film.setLanguage(null);

		return film;
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", name=" + name + "]";

	}

}
