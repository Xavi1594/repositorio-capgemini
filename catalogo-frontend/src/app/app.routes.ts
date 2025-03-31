import { Routes } from '@angular/router';
import { CategoryListComponent } from './category/category-list/category-list.component';

export const routes: Routes = [
  {path: '', redirectTo:'/films', pathMatch:'full' },
  {path: 'films', loadComponent:()=>import('../app/film/film-list/film-list.component').then(m=>m.FilmListComponent)},
  {path: 'categories', loadComponent:()=>import('../app/category/category-list/category-list.component').then(m=>m.CategoryListComponent)},
];
