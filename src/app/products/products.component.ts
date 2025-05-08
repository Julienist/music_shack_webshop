import { Component, inject, Input, Signal, ChangeDetectionStrategy } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { Product } from '../models/product.model';
import { RouterLink } from '@angular/router';
import { ProductsService } from '../services/products.service';
import { CartService } from '../services/cart.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [RouterLink, MatCardModule, MatButtonModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductComponent {
  @Input() products: Product[] = [];


  // private route = inject(ActivatedRoute);
  private productsService = inject(ProductsService);
  private cartService = inject(CartService);

  constructor(private translate: TranslateService) {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }
  productsSignal: Signal<Product[]> = this.productsService.products;

  addProductToCart(product: Product) {
    if (!product) {
      return;
    }

    this.cartService.addToCart(product);
  }
}
