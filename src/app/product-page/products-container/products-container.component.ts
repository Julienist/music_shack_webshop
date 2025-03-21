import { Component, DestroyRef, inject, input, signal } from '@angular/core';
import { ProductComponent } from "../../products/products.component";
import { Product } from '../../models/product.model';
import { ProductsService } from '../../services/products.service';

@Component({
  selector: 'app-products-container',
  imports: [ProductComponent],
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {
  title = input.required<string>();

  public receivedProducts: Product[] = [];
  isFetching = signal(false);
  error = signal('')
  // private httpClient = inject(HttpClient);
  private productsService = inject(ProductsService);
  private destroyRef = inject(DestroyRef);

  ngOnInit() {
    this.isFetching.set(true);
    const subscription = 
    this.productsService.loadProducts().subscribe({
      next: (products) => {
        console.log(products);
        this.receivedProducts = products;
      },
      error: (error: Error) => {
        this.error.set(error.message);
      },
      complete: () => {
        console.log(this.receivedProducts)
        this.isFetching.set(false);
      }
    });

    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe();
    });
  }
}
