import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pageable } from '../core/model/Pageable';

import { FilmPage } from '../core/model/FilmPage';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  constructor(private http: HttpClient) { }
  private baseUrl = 'http://localhost:8080/films/v1'


  getFilms(pageable:Pageable): Observable<FilmPage> {
    let params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
       .set('size', pageable.pageSize.toString())

     return this.http.get<FilmPage>(this.baseUrl, { params });
   }
  }

