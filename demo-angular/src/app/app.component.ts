import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DemosComponent } from './demos/demos.component';
import { NotificationModalComponent } from './main/Index';
// import { LogerService } from '@my/core';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule,NotificationModalComponent,DemosComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Xavi';
}

