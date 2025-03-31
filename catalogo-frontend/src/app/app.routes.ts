import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: '', redirectTo:'/films', pathMatch:'full' },
  {path: 'films', loadComponent:()=>import('../app/film/film-list/film-list.component').then(m=>m.FilmListComponent)}
];
