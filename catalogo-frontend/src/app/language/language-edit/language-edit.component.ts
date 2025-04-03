import { Component, Inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Language } from '../model/Language';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoryService } from '../../category/category.service';
import { Category } from '../../category/model/Category';
import { LanguageService } from '../language.service';

@Component({
  selector: 'app-language-edit',
  imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule ],

  templateUrl: './language-edit.component.html',
  styleUrl: './language-edit.component.css'
})
export class LanguageEditComponent implements OnInit {

  language?:Language;

  constructor(
    public dialogRef: MatDialogRef<LanguageEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Language,
    private languageService: LanguageService
  ) {}

  ngOnInit(): void {

    if (this.data && this.data.languageId !== undefined) {
      this.language = { ...this.data };
    } else {

      this.language = { languageId: 0, idioma: '' };
    }
  }
  onSave(): void {
    if (this.language) {
      this.languageService.saveLanguage(this.language).subscribe(
        (result) => {
          console.log('Idioma con éxito:', result);
          this.dialogRef.close(true);
        },
        (error) => {
          console.error('Error al guardar el idioma:', error);
        }
      );
    } else {
      console.error('Idioma is undefined');
    }
  }

  onClose(): void {
    this.dialogRef.close();
  }
  }
