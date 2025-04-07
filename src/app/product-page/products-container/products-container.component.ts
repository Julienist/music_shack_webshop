import { Component, computed, DestroyRef, inject, input, Signal, signal } from '@angular/core';
import { ProductComponent } from "../../products/products.component";
import { Product } from '../../models/product.model';
import { ProductsService } from '../../services/products.service';
import { CommonModule } from '@angular/common';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-products-container',
  standalone: true,
  imports: [ProductComponent, CommonModule, TranslatePipe],
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {
  // title = input.required<string>();
  title = 'Products';

  // public receivedProducts: Product[] = [];
  private productsService = inject(ProductsService);

  isFetching = computed(() => this.productsService.isFetching());
  error = computed(() => this.productsService.error());
  productList: Signal<Product[]> = computed(() => this.productsService.products());

  ngOnInit() {
    this.productsService.loadProducts();
  }

  constructor(private translate: TranslateService) {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }

  // ngOnInit() {
  //   this.isFetching.set(true);
  //   const subscription =
  //   this.productsService.loadProducts().subscribe({
  //     next: (products) => {
  //       console.log(products);
  //       this.receivedProducts = products;
  //     },
  //     error: (error: Error) => {
  //       this.error.set(error.message);
  //     },
  //     complete: () => {
  //       console.log(this.receivedProducts)
  //       this.isFetching.set(false);
  //     }
  //   });

  //   this.destroyRef.onDestroy(() => {
  //     subscription.unsubscribe();
  //   });
  // }


}
