import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment.development';
import { Category } from '../models/category.model';
import { DataLoaderService } from './data-loader.service';

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  public products = signal<Product[]>([]);
  public isFetching = signal(false);
  public error = signal('');
  private dataLoaderService = inject(DataLoaderService);

  constructor() {
    console.log('ProductsService initialized');

    // Ensure products are loaded
    this.loadProducts();

    // Log initial products (will likely be empty initially)
    console.log('Initial products:', this.products());
  }

  public loadProducts(): void {
    this.isFetching.set(true);

    this.dataLoaderService
      .loadDataWithFallback<Category[]>(
        `${environment.apiUrl}/categories`,
        'assets/test_data/categorie_en_product_data.json'
      )
      .subscribe({
        next: (categories) => {
          const allProducts = this.flattenProducts(categories);
          this.products.set(allProducts); // Update the products signal
          console.log('Products loaded:', allProducts);
          this.isFetching.set(false);
        },
        error: (error: Error) => {
          console.error('Error loading products:', error.message);
          this.error.set(error.message);
          this.isFetching.set(false);
        },
      });
  }

  private flattenProducts(categories: Category[]): Product[] {
    console.log('Flattening products:', categories);
    return categories.flatMap((category) =>
      category.products.map((product) => ({
        id: product.id,
        name: product.name,
        description: product.description,
        price: product.price,
        imageurl: product.imageurl,
        version: product.version,
        amountInStock: product.amountInStock,
        orderProducts: product.orderProducts || [],
        available: product.available,
        quantity: 1,
        categoryId: category.id,
      }))
    );
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

  public filterProductsByName(query: string): Product[] {
    const lowerCaseQuery = query.toLowerCase();
    return this.products().filter(product =>
      product.name.toLowerCase().includes(lowerCaseQuery)
    );
  }

  // Get all product names from the products list
  get productnames(): string[] {
    console.log('productnames called'+ this.products().length);
    return this.products().map((product) => product.name); // Dynamically derive product names
  }

  get productsList() {
    return computed(() => {
      console.log('Products signal updated:', this.products());
      return this.products();
    });
  }
}
