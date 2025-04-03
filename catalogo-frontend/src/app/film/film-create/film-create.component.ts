import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FilmService } from '../film.service';
import { Router } from '@angular/router';
import { Language } from '../../language/model/Language';

@Component({
  selector: 'app-film-create',
  templateUrl: './film-create.component.html',
  styleUrls: ['./film-create.component.css'],
  imports: [ReactiveFormsModule],
})
export class FilmCreateComponent implements OnInit {
  public miForm: FormGroup;

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
      sancion: ['', Validators.required],
      idiomaId: ['', Validators.required],  // Campo para el ID del idioma
      actores: ['', Validators.required],
      coste:['19.90',Validators.required]

    });
  }

  ngOnInit(): void { }

  onSave(): void {
    if (this.miForm.valid) {
      const formValue = this.miForm.value;


      const idioma = {
        languageId: 1,

      };


      const film = {
        ...formValue,
        idioma: idioma
      };


      delete film.idiomaId;


      this.filmService.saveFilms(film).subscribe(
        (result) => {
          console.log('Película guardada exitosamente:', result);
          this.router.navigate(['/films']);
        },
        (error) => {
          console.error('Error al guardar la película:', error);
        }
      );
    }
  }
}
