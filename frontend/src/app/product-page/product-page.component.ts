import { Component } from '@angular/core';
import { CategoryNavigationComponent } from "./category-navigation/category-navigation.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-product-page',
  standalone: true,
  imports: [CategoryNavigationComponent, RouterOutlet],
  templateUrl: './product-page.component.html',
  styleUrl: './product-page.component.scss'
})
export class ProductPageComponent {
}
