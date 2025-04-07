import { Component, computed, inject, Input, input, Signal, signal } from '@angular/core';

import { Product } from '../models/product.model';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProductsService } from '../services/products.service';
import { CartService } from '../services/cart.service';
import {TranslateService} from '@ngx-translate/core';

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
  @Input() products: Product[] = [];
  // @Input() product!: Product;


  // private route = inject(ActivatedRoute);
  private productsService = inject(ProductsService);
  private cartService = inject(CartService);

  constructor(private translate: TranslateService) {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }
  productsSignal: Signal<Product[]> = this.productsService.products;

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

  addProductToCart(product: Product) {
    if (!product) {
      //logs voor testing
      console.error("Product is niet geladen!");
      return;
    }

    this.cartService.addToCart(product);
    //logs voor testing
    console.log(product, "toegevoegd aan winkelmandje");
  }
}
