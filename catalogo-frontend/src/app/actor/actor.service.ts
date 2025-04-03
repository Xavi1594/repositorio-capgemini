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
  constructor(private http: HttpClient) { }
  private baseUrl = 'http://localhost:8080/actors/v1';

  getActors(pageable: Pageable): Observable<ActorPage> {
    let params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
      .set('size', pageable.pageSize.toString())

    return this.http.get<ActorPage>(this.baseUrl, { params });
  }

  saveActors(actor: Actor): Observable<Actor> {
    const id = actor.id;
    let result: Observable<Actor>;

    if (id) {
      const url = `${this.baseUrl}/${id}`;
      result = this.http.put<Actor>(url, actor);
    } else {
      result = this.http.post<Actor>(this.baseUrl, actor);
    }

    return result;
  }

  getOneActor(id: number): Observable<Actor> {
    return this.http.get<Actor>(`${this.baseUrl}/${id}`);
  }
  deleteActor(idActor: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${idActor}`);
  }

}
