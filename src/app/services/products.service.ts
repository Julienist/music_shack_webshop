import { HttpClient } from '@angular/common/http';
import { DestroyRef, inject, Injectable, OnInit, signal } from '@angular/core';
import { Product } from '../models/product.model';
import { catchError, map, Subscription, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  products = signal<Product[] | undefined>(undefined);
  isFetching = signal(false);
  error = signal('')
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);

  constructor() {}

  loadProducts() {
    return this.fetchProducts(
      'http://localhost:8080/api/products',
    'Something went wrong')
  }

  // ngOnInit() {
  //   this.isFetching.set(true);
  //   const subscription = 
  //   this.productsService.loadProducts()
  //   .subscribe({
  //     next: (products) => {
  //       // console.log(responseData.products);
  //       this.products.set(products);
  //     },
  //     error: (error: Error) => {
  //       this.error.set(error.message);
  //     },
  //     complete: () => {
  //       this.isFetching.set(false);
  //     }
  //   });

  //   this.destroyRef.onDestroy(() => {
  //     subscription.unsubscribe();
  //   });
  // }

  private fetchProducts(url: string, errorMessage: string) {
    return this.httpClient.get<Product[]>(url).pipe(
      catchError((error) => {
        return throwError(() => new Error(errorMessage));
      })
    );

    // return this.httpClient.get<Product[]>(url).pipe(
      // map(resData) => resData.products),
      // catchError((error) => {
        // return throwError(() => new Error(errorMessage));
      // })
      // ;
  // }
}
}