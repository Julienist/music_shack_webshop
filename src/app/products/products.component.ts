import { Component, Input, input } from '@angular/core';

import { Product } from '../models/product.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductComponent {
  // products = input.required<Product[]>();
  @Input() public products!: Product[];  

}
