import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Product } from '../models/product.model';
import {catchError, throwError} from 'rxjs';
import { environment } from '../../environments/environment.development';
import {Category} from '../models/category.model';

@Injectable({
  providedIn: 'root'
})

export class ProductsService {
  products = signal<Product[]>([]);
  isFetching = signal(false);
  error = signal('');

  private httpClient = inject(HttpClient);

  constructor() {}

  public loadProducts(): void {
    this.isFetching.set(true);

    this.httpClient.get<Category[]>(`${environment.apiUrl}/categories`).pipe(
      catchError((error) => {
        this.error.set('Something went wrong');
        return throwError(() => new Error('Something went wrong'));
      })
    ).subscribe({
      next: (categories) => {
        const allProducts = this.flattenProducts(categories);
        this.products.set(allProducts);
        this.isFetching.set(false);
      },
      error: (error: Error) => {
        this.error.set(error.message);
        this.isFetching.set(false);
      },
    });
  }

  private flattenProducts(categories: Category[]): Product[] {
    return categories.flatMap((category) =>
      category.products.map((product) => ({
        id: product.id,
        name: product.name,
        description: product.description,
        price: product.price,
        imageurl: product.imageurl ?? product.imageurl, // fallback
        version: product.version,
        amountInStock: product.amountInStock,
        available: product.available,
        orderProducts: product.orderProducts ?? [],
        quantity: 1,
        categoryId: category.id,
      }))
    );
  }

  // public loadProducts() {
  //   this.isFetching.set(true);
  //   this.httpClient.get<Product[]>(environment.apiUrl + '/products').pipe(
  //     catchError((error) => {
  //       this.error.set('Something went wrong');
  //       return throwError(() => new Error('Something went wrong'));
  //     })
  //   ).subscribe({
  //     next: (products) => {
  //       this.products.set(products);
  //       this.isFetching.set(false);
  //     },
  //     error: (error: Error) => {
  //       this.error.set(error.message);
  //       this.isFetching.set(false);
  //     }
  //   });
  // }

  get productsList() {
    return computed(() => this.products());
  }

  public getProductByIdFromCache(productId: number): Product {
    const product = this.products().find((p) => p.id === productId);
    if (!product) {
      throw new Error(`Product met ID ${productId} niet gevonden in cache.`);
    }
    return product;
  }

  public getProductsByCategory(categoryId: number): Product[] {
    return this.products().filter((p) => p.categoryId === categoryId);
  }


}
