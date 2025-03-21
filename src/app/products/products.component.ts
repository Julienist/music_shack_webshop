import { Component, Input, input } from '@angular/core';

import { Product } from '../models/product.model';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductComponent {
  // products = input.required<Product[]>();
  @Input() public products!: Product[] | undefined;  

}
