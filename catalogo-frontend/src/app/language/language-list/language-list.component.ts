import { Component, OnInit } from '@angular/core';
import { Language } from '../model/Language';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { LanguageService } from '../language.service';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { LanguageEditComponent } from '../language-edit/language-edit.component';

@Component({
  selector: 'app-language-list',
  imports: [
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CommonModule,
    MatCardModule,
],
  templateUrl: './language-list.component.html',
  styleUrl: './language-list.component.css'
})
export class LanguageListComponent implements OnInit {
  dataSource = new MatTableDataSource<Language>();

  constructor(private languageService:LanguageService,
    public dialog:MatDialog,
  ) { }
  ngOnInit(): void {
  this.languageService.getLanguages().subscribe(
    language=>this.dataSource.data=language
  );

  }
   createLanguage() {
     const dialogRef = this.dialog.open(LanguageEditComponent, {
       data: {languageId :0, idioma:''}
     });

     dialogRef.afterClosed().subscribe(result => {
       this.ngOnInit();
     });
   }





  }

