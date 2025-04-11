import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category.model';
import { catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  categories = signal<Category[]>([]);
  isFetching = signal(false);
  error = signal('');

  private httpClient = inject(HttpClient);

  constructor() {}

  public loadCategories(): void {
    this.isFetching.set(true);
    this.httpClient.get<Category[]>(`${environment.apiUrl}/categories`).pipe(
      catchError((error) => {
        this.error.set('Kon categorieën niet ophalen');
        return throwError(() => new Error('Categorie fout'));
      })
    ).subscribe({
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
