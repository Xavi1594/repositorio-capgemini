import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pageable } from '../core/model/Pageable';
import { Actor } from './model/Actor';
import { ActorPage } from '../core/model/ActorPage';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ActorService {
  constructor(private http: HttpClient) {}
  private baseUrl = 'http://localhost:8080/actors/v1';

  getActors(pageable: Pageable): Observable<ActorPage> {
    let params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
      .set('size', pageable.pageSize.toString())

    return this.http.get<ActorPage>(this.baseUrl, { params });
  }

  saveActors(actor: Actor): Observable<Actor> {
    const  id  = actor.id;
    console.log('actor' + id)
    const url = id ? `${this.baseUrl}/${id}` : this.baseUrl;
    console.log(`Saving actor:`, actor);
    console.log(`URL:`, url);
    let result = this.http.post<Actor>(url, actor);
    console.log(`Actor saved: ${actor.nombre}, ID: ${actor.id}`);
    return result;
  }
}
