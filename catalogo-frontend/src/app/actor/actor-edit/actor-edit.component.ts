import { Component, Inject, OnInit } from '@angular/core';
import { ActorService } from '../actor.service';
import { Actor } from '../model/Actor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-actor-edit',
  imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule ],
  templateUrl: './actor-edit.component.html',
  styleUrl: './actor-edit.component.css'
})
export class ActorEditComponent implements OnInit {

  actor!:Actor;

  constructor(
    public dialogRef: MatDialogRef<ActorEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Actor,
    private actorService: ActorService
) {}
ngOnInit(): void {

  if (this.data && this.data.id !== undefined) {
    this.actor = { ...this.data };
  } else {

    this.actor = { id: 0, nombre: '', apellidos: '' };
  }
}
onSave(): void {
  this.actorService.saveActors(this.actor).subscribe(
    (result) => {
      console.log('Actor guardado con éxito:', result);
      this.dialogRef.close(true);
    },
    (error) => {
      console.error('Error al guardar el actor:', error);
    }
  );
}
onClose() {
  this.dialogRef.close();
}
}
