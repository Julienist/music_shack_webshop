import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [
    MatIconModule,
    RouterModule,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
}
