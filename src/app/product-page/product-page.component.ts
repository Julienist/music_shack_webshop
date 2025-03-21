import { Component, DestroyRef, inject, input, signal } from '@angular/core';
import { CategoryNavigationComponent } from "./category-navigation/category-navigation.component";
import { ProductsContainerComponent } from "./products-container/products-container.component";
import { ProductComponent } from '../products/products.component';

@Component({
  selector: 'app-product-page',
  standalone: true,
  imports: [CategoryNavigationComponent, ProductsContainerComponent],
  templateUrl: './product-page.component.html',
  styleUrl: './product-page.component.scss'
})
export class ProductPageComponent {
}
