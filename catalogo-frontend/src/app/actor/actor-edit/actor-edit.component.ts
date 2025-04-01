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

  actor:Actor|undefined;

  constructor(
    public dialogRef: MatDialogRef<ActorEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private actorService: ActorService
) {}
ngOnInit(): void {
    this.actor =this.data.actor ? Object.assign({}, this.data.actor): new Actor();
}
onSave() {
  console.log('Actor before save:', this.actor);

  this.actorService.saveActors(this.actor!).subscribe(() => {
    this.dialogRef.close();
  });
}
onClose() {
  this.dialogRef.close();
}
}
