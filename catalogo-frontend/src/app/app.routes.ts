import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: '', redirectTo:'/films', pathMatch:'full' },
  {path: 'films', loadComponent:()=>import('../app/film/film-list/film-list.component').then(m=>m.FilmListComponent)},
  {path: 'films/new', loadComponent: ()=>import('../app/film/film-create/film-create.component').then(m=>m.FilmCreateComponent)},
  {path: 'categories', loadComponent:()=>import('../app/category/category-list/category-list.component').then(m=>m.CategoryListComponent)},
  {path: 'actors', loadComponent:()=>import('../app/actor/actor-list/actor-list.component').then(m=>m.ActorListComponent)},
  {path: 'actors/:id', loadComponent:()=>import ('../app/actor/actor-detail/actor-detail.component').then(m=>m.ActorDetailComponent)},
  {path: 'films/:id', loadComponent:()=>import('../app/film/film-detail/film-detail.component').then(m=>m.FilmDetailComponent)},
  {path:'language', loadComponent:()=>import('../app/language/language-list/language-list.component').then(m=>m.LanguageListComponent)}

];
