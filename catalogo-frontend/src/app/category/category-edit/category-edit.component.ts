import { Component, Inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Category } from '../model/Category';
import { CategoryService } from '../category.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActorEditComponent } from '../../actor/actor-edit/actor-edit.component';

@Component({
  selector: 'app-category-edit',
  imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule ],
  templateUrl: './category-edit.component.html',
  styleUrl: './category-edit.component.css'
})
export class CategoryEditComponent implements OnInit {

category?:Category;

constructor(
  public dialogRef: MatDialogRef<ActorEditComponent>,
  @Inject(MAT_DIALOG_DATA) public data: Category,
  private categoryService: CategoryService
) {}

ngOnInit(): void {

  if (this.data && this.data.id !== undefined) {
    this.category = { ...this.data };
  } else {

    this.category = { id: 0, name: '' };
  }
}
onSave(): void {
  if (this.category) {
    this.categoryService.saveCategory(this.category).subscribe(
      (result) => {
        console.log('Categoria guardada con éxito:', result);
        this.dialogRef.close(true);
      },
      (error) => {
        console.error('Error al guardar la categoría:', error);
      }
    );
  } else {
    console.error('Category is undefined');
  }
}

onClose(): void {
  this.dialogRef.close();
}
}
