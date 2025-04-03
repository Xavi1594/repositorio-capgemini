import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FilmService } from '../film.service';
import { Router } from '@angular/router';
import { from } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-film-create',
  templateUrl: './film-create.component.html',
  styleUrls: ['./film-create.component.css'],
  imports: [ReactiveFormsModule,  CommonModule],
})
export class FilmCreateComponent implements OnInit {
  public miForm: FormGroup;
  public idiomas = [
    { languageId: 1, idioma: "English" },
    { languageId: 2, idioma: "Italian" },
    { languageId: 3, idioma: "Japanese" },
    { languageId: 4, idioma: "Mandarin" },
    { languageId: 5, idioma: "French" },
    { languageId: 6, idioma: "German" }
  ];

  constructor(
    private fb: FormBuilder,
    private filmService: FilmService,
    private router: Router
  ) {
    this.miForm = this.fb.group({
      titulo: ['', Validators.required],
      descripcion: ['', Validators.required],
      periodo: ['', Validators.required],
      precio: ['', Validators.required],
      sancion: ['5.0', Validators.required],
      idiomaId: ['', Validators.required],  // Campo para el ID del idioma
      coste: ['19.90', Validators.required]
    });
  }

  ngOnInit(): void {

    console.log(this.idiomas);
  }

  onSave(): void {
    if (this.miForm.valid) {
      const formValue = this.miForm.value;


      const idiomaId = Number(formValue.idiomaId);


      if (isNaN(idiomaId)) {
        console.error('El ID del idioma no es un número válido');
        return;
      }


      const selectedIdioma = this.idiomas.find(idioma => idioma.languageId === idiomaId);


      const film = {
        ...formValue,
        idioma: selectedIdioma
      };


      delete film.idiomaId;

      console.log('Formulario:', film);

      this.filmService.saveFilms(film).subscribe(
        (result) => {
          this.router.navigate(['/films']);
        },
        (error) => {
          console.error('Error al guardar la película:', error);
        }
      );
    }
  }
}
