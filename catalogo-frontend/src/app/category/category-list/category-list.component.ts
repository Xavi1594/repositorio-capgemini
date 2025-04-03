import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Category } from '../model/Category';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { CategoryService } from '../category.service';
import { MatCardModule } from '@angular/material/card';
import { CategoryEditComponent } from '../category-edit/category-edit.component';
import { DialogconfirmationComponent } from '../../core/dialogconfirmation/dialogconfirmation.component';


@Component({
  selector: 'app-category-list',
  imports: [
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CommonModule,
    MatCardModule,
],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent implements OnInit{

  dataSource = new MatTableDataSource<Category>();

  displayedColumns: string[] = ['id', 'name', 'action'];

  constructor(  private categoryService: CategoryService,
    public dialog:MatDialog,
  ) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(
      categories => this.dataSource.data = categories
  );
  }

  createCategory() {
    const dialogRef = this.dialog.open(CategoryEditComponent, {
      data: {id :0, name:''}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  editCategory(category: Category) {
    const dialogRef = this.dialog.open(CategoryEditComponent, {
      data:  category
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

 deleteCategory(category: Category) {
   const dialogRef = this.dialog.open(DialogconfirmationComponent, {
     data: { title: "Eliminar categoría", description: "Atención si borra la categoría se perderán sus datos.<br> ¿Desea eliminar la categoría?" }
   });

   dialogRef.afterClosed().subscribe(result => {
     if (result) {
       this.categoryService.deleteCategory(category.id).subscribe(result => {
         this.ngOnInit();
        });
      }     });
  } }
