import { Routes } from '@angular/router';
import { CategoryListComponent } from './category/category-list/category-list.component';
import { ActorListComponent } from './actor/actor-list/actor-list.component';

export const routes: Routes = [
  {path: '', redirectTo:'/films', pathMatch:'full' },
  {path: 'films', loadComponent:()=>import('../app/film/film-list/film-list.component').then(m=>m.FilmListComponent)},
  {path: 'categories', loadComponent:()=>import('../app/category/category-list/category-list.component').then(m=>m.CategoryListComponent)},
  {path: 'actors', loadComponent:()=>import('../app/actor/actor-list/actor-list.component').then(m=>m.ActorListComponent)},
  {path: 'actors/:id', loadComponent:()=>import ('../app/actor/actor-detail/actor-detail.component').then(m=>m.ActorDetailComponent)},
  {path: 'films/:id', loadComponent:()=>import('../app/film/film-detail/film-detail.component').then(m=>m.FilmDetailComponent)},
  {path: 'films/new', loadComponent: ()=>import('../app/film/film-create/film-create.component').then(m=>m.FilmCreateComponent)}

];
