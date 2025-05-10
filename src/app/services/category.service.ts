import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category.model';
import { catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { DataLoaderService } from './data-loader.service';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  public categories = signal<Category[]>([]);
  public isFetching = signal(false);
  public error = signal('');

  private dataLoaderService = inject(DataLoaderService);


  public loadCategories(): void {
    this.isFetching.set(true);

    this.dataLoaderService
      .loadDataWithFallback<Category[]>(
        `${environment.apiUrl}/categories`,
        'assets/test_data/categorie_en_product_data.json'
      )
      .subscribe({
        next: (categories) => {
          this.categories.set(categories);
          this.isFetching.set(false);
        },
        error: (error: Error) => {
          this.error.set(error.message);
          this.isFetching.set(false);
        },
      });
  }

  get categoriesList() {
    return this.categories;
  }
}
