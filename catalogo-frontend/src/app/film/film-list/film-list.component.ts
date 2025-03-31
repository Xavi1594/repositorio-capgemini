import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { Pageable } from '../../core/model/Pageable';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Film } from '../model/Film';
import { FilmService } from '../film.service';
@Component({
  selector: 'app-film-list',
  imports: [MatButtonModule, MatIconModule, MatTableModule, CommonModule, MatPaginatorModule],
  templateUrl: './film-list.component.html',
  styleUrl: './film-list.component.css'
})
export class FilmListComponent {
  pageNumber: number = 0;
  pageSize: number = 10;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Film>()
  displayedColumns: string[] = ['id', 'titulo'];

  constructor (private filmService:FilmService,public dialog: MatDialog){}


  ngOnInit(): void {
    this.loadPage();
  }

  loadPage(event?: PageEvent) {
    const pageable: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [{ property: 'id', direction: 'ASC' }]
    };

    if (event) {
      pageable.pageSize = event.pageSize;
      pageable.pageNumber = event.pageIndex;
    }

    this.filmService.getFilms(pageable).subscribe(data => {
      if (data && data.pageable) {
console.log(data)
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
      } else {
        console.error('Datos recibidos son undefined');
      }
    }, error => {
      console.error('Error al obtener las pelis:', error);
    });
  }

}

