import { Component, computed, inject, Input, input, Signal, signal } from '@angular/core';

import { Product } from '../models/product.model';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProductsService } from '../services/products.service';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductComponent {
  // products = input.required<Product[]>();
  // @Input() public products!: Product[];
  @Input() product!: Product;


  // private route = inject(ActivatedRoute);
  private productsService = inject(ProductsService);
  private cartService = inject(CartService);

  products: Signal<Product[]> = this.productsService.products;
  
  // productId = signal<number | null>(null); // Opslaan van product-ID uit de URL
  // product: Signal<Product | undefined>;
  // constructor() {
    // Bereken welk product overeenkomt met de productId
    // this.product = computed(() => {
      // const id = this.productId();
      // return this.productsService.products()?.find(p => p.id === id);
    // });
  // }

  // ngOnInit() {
  //   this.route.paramMap.subscribe(params => {
  //     const id = Number(params.get('id')); // Haal ID op uit URL
  //     this.productId.set(id);
  //   });

  //   if (!this.productsService.products()) {
  //     this.productsService.loadProducts(); // Zorg dat de producten geladen worden als ze er nog niet zijn
  //   }
  // }

  addProductToCart() {
    this.cartService.addToCart(this.product);
  }
}
