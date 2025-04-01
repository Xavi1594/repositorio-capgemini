import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';  // Importar ActivatedRoute
import { ActorService } from '../actor.service';
import { Actor } from '../model/Actor';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-actor-detail',
  imports: [MatButtonModule, MatIconModule, MatTableModule, CommonModule, MatPaginatorModule, MatCardModule, MatButtonModule, RouterModule],
  templateUrl: './actor-detail.component.html',
  styleUrls: ['./actor-detail.component.css']
})
export class ActorDetailComponent implements OnInit {
  dataSource = new MatTableDataSource<Actor>();
  displayedColumns: string[] = ['nombre', 'apellidos'];

  actorId: number = 0;

  constructor(
    private actorService: ActorService,
    private route: ActivatedRoute,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {

    this.route.params.subscribe(params => {

      this.actorId = +params['id'];
      console.log('Actor ID:', this.actorId);

      if (this.actorId) {
        this.loadPage();
      } else {
        console.error('ID de actor no encontrado en la URL');
      }
    });
  }

  loadPage(): void {

    this.actorService.getOneActor(this.actorId).subscribe(data => {
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
