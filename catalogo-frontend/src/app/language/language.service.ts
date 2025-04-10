import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Language } from './model/Language';


@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private baseUrl = 'http://localhost:8080/languages/v1';

  constructor(private http: HttpClient) {}

  getLanguages(): Observable<Language[]> {
    return this.http.get<Language[]>(this.baseUrl);
  }


    saveLanguage(language:Language): Observable<Language | null> {
    const id = language.idioma;
       let result: Observable<Language>;

       if (id) {
         const url = `${this.baseUrl}/${id}`;
         result = this.http.put<Language>(url, language);
       } else {
         result = this.http.post<Language>(this.baseUrl, language);
       }

       return result;
    }

}
