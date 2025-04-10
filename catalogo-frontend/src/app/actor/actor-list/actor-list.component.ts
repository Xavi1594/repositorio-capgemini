import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ActorService } from '../actor.service';
import { Actor } from '../model/Actor';
import { MatPaginatorModule } from '@angular/material/paginator';
import { Pageable } from '../../core/model/Pageable';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { ActorEditComponent } from '../actor-edit/actor-edit.component';
import { RouterModule } from '@angular/router';
import { DialogconfirmationComponent } from '../../core/dialogconfirmation/dialogconfirmation.component';

@Component({
  selector: 'app-actor-list',
  imports: [MatButtonModule, MatIconModule, MatTableModule, CommonModule, MatPaginatorModule, MatCardModule, MatButtonModule, RouterModule],
  templateUrl: './actor-list.component.html',
  styleUrl: './actor-list.component.css'
})
export class ActorListComponent implements OnInit {
  pageNumber: number = 0;
  pageSize: number = 20;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Actor>();
  displayedColumns: string[] = ['id','nombre' , 'apellidos', 'action']

  constructor(private actorService: ActorService, public dialog: MatDialog){}

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

    this.actorService.getActors(pageable).subscribe(data => {
      if (data && data.pageable) {
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
      } else {
        console.error('Datos recibidos son undefined');
      }
    }, error => {
      console.error('Error al obtener los actores:', error);
    });
  }

createActor(){
  const dialogRef = this.dialog.open(ActorEditComponent, {
    data: { id: 0, nombre: '', apellidos: '' }
  });

  dialogRef.afterClosed().subscribe(result => {
    this.ngOnInit();
  });
}
editActor(actor: Actor) {
  const dialogRef = this.dialog.open(ActorEditComponent, {
    data: actor
  });

  dialogRef.afterClosed().subscribe(result => {
    this.ngOnInit();
  });
}



 deleteActor(actor: Actor) {
  const dialogRef = this.dialog.open(DialogconfirmationComponent, {
    data: { title: "Eliminar Actor", description: "Atención, si borra el actor se perderán los datos. ¿Desea eliminar el actor?" }
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result && actor.id) { // Asegúrate de que actor.id esté definido
      this.actorService.deleteActor(actor.id).subscribe(() => {
        this.ngOnInit();  // Refresca la lista de actores después de la eliminación
      });
    } else {
      console.error("El actor no tiene un ID válido.");
    }
  });
}
}
