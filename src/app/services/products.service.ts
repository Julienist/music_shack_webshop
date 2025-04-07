import { HttpClient } from '@angular/common/http';
import { computed, DestroyRef, inject, Injectable, OnInit, signal } from '@angular/core';
import { Product } from '../models/product.model';
import {catchError, map, Observable, Subscription, throwError} from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})

export class ProductsService {
  products = signal<Product[]>([]);
  isFetching = signal(false);
  error = signal('');
  private httpClient = inject(HttpClient);

  constructor() {}

  // loadProducts() {
  //   return this.fetchProducts(
  //     environment.productsUrl,
  //   'Something went wrong')
  // }

  public loadProducts() {
    this.isFetching.set(true);
    this.httpClient.get<Product[]>(environment.apiUrl + '/products').pipe(
      catchError((error) => {
        this.error.set('Something went wrong');
        return throwError(() => new Error('Something went wrong'));
      })
    ).subscribe({
      next: (products) => {
        this.products.set(products);
        this.isFetching.set(false);
      },
      error: (error: Error) => {
        this.error.set(error.message);
        this.isFetching.set(false);
      }
    });
  }

  get productsList() {
    return computed(() => this.products());
  }

  getProductByIdFromCache(productId: number): Product | undefined {
    return this.products().find(p => p.id === productId);
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

  // private fetchProducts(url: string, errorMessage: string) {
  //   return this.httpClient.get<Product[]>(url).pipe(
  //     catchError((error) => {
  //       return throwError(() => new Error(errorMessage));
  //     })
  //   );

    // return this.httpClient.get<Product[]>(url).pipe(
      // map(resData) => resData.products),
      // catchError((error) => {
        // return throwError(() => new Error(errorMessage));
      // })
      // ;
  // }
}
