import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Film } from '../model/Film';
import { FilmService } from '../film.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-film-detail',
  imports:  [MatButtonModule, MatIconModule, MatTableModule, CommonModule, MatPaginatorModule, MatCardModule, MatButtonModule, RouterModule],
  templateUrl: './film-detail.component.html',
  styleUrl: './film-detail.component.css'
})
export class FilmDetailComponent {
  dataSource = new MatTableDataSource<Film>();
  displayedColumns: string[] = ['titulo','descripcion','idioma','rating']

  filmId :number=0

  constructor(
    private filmservice:FilmService,
    private route: ActivatedRoute,
    public dialog: MatDialog
  ) { }
  ngOnInit():void{
    this.route.params.subscribe(params => {

      this.filmId = +params['id']


      if (this.filmId){
        this.loadPage();
      } else {
        console.error('ID de peli  no encontrado')
      }
    });
  }
  loadPage():void {

    this.filmservice.getOneFilm(this.filmId).subscribe(data => {
      if (data) {
        this.dataSource.data = [data];
      } else {
        console.error('Actor no encontrado');
      }
    }, error => {
      console.error('Error al obtener los detalles del actor:', error);
    });
  }
}




