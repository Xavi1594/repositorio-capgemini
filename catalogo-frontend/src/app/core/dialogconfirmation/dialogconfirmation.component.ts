import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dialogconfirmation',
  imports: [MatButtonModule],
  templateUrl: './dialogconfirmation.component.html',
  styleUrl: './dialogconfirmation.component.css'
})
export class DialogconfirmationComponent {
  title!: string;
  description!: string;

  constructor(
      public dialogRef: MatDialogRef<DialogconfirmationComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
      this.title = this.data.title;
      this.description = this.data.description;
  }

  onClose(value = false) {

      this.dialogRef.close(value);
  }
}


