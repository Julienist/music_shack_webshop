import { Component, computed, inject, OnInit, Signal, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductsService } from '../../services/products.service';
import { Product } from '../../models/product.model';
import { NgIf } from '@angular/common';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-specific-product',
  imports: [NgIf],
  templateUrl: './specific-product.component.html',
  styleUrl: './specific-product.component.scss'
})
export class SpecificProductComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private productsService = inject(ProductsService);
  private cartService = inject(CartService);

  productId = signal<number | null>(null); // Opslaan van product-ID uit de URL
  product: Signal<Product | undefined>;

  constructor() {
    // Bereken welk product overeenkomt met de productId
    this.product = computed(() => {
      const id = this.productId();
      return this.productsService.products()?.find(p => p.id === id);
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id')); // Haal ID op uit URL
      this.productId.set(id);
    });

    if (!this.productsService.products()) {
      this.productsService.loadProducts(); // Zorg dat de producten geladen worden als ze er nog niet zijn
    }
  }

  addProductToCart() {
    if (this.product()) {
      this.cartService.addToCart(this.product()!);
      console.log(this.product + " toegevoegd aan winkelmandje");
    }
  }

}
