import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Category } from './model/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }
  private baseUrl = 'http://localhost:8080/categories/v1';
  getCategories(): Observable<Category[]> {

    return this.http.get<Category[]>(this.baseUrl);
  }

  saveCategory(category: Category): Observable<Category | null> {
  const id = category.id;
     let result: Observable<Category>;

     if (id) {
       const url = `${this.baseUrl}/${id}`;
       result = this.http.put<Category>(url, category);
     } else {
       result = this.http.post<Category>(this.baseUrl, category);
     }

     return result;
  }

  deleteCategory(idCategory: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${idCategory}`);
  }

}
