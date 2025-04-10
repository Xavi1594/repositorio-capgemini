import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pageable } from '../core/model/Pageable';

import { FilmPage } from '../core/model/FilmPage';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Film } from './model/Film';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  constructor(private http: HttpClient) { }
  private baseUrl = 'http://localhost:8080/films/v1'


  getFilms(pageable: Pageable): Observable<FilmPage> {
    let params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
      .set('size', pageable.pageSize.toString())

    return this.http.get<FilmPage>(this.baseUrl, { params });
  }
  getOneFilm(id: number): Observable<Film> {
    return this.http.get<Film>(`${this.baseUrl}/${id}`);
  }

  saveFilms(film: Film): Observable<Film> {
    const id = film.id;
    let result: Observable<Film>;

    if (id) {
      const url = `${this.baseUrl}/${id}`;
      result = this.http.put<Film>(url, film);
    } else {
      result = this.http.post<Film>(this.baseUrl, film);
    }
    return result;
  }
}


