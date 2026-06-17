import { inject, Injectable, signal, effect } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category.model';
import { catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { DataLoaderService } from './data-loader.service';
import { TranslateService, TranslatePipe } from '@ngx-translate/core';
import { LanguageService } from '../services/language.service';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  public categories = signal<Category[]>([]);
  public isFetching = signal(false);
  public error = signal('');

  private dataLoaderService = inject(DataLoaderService);
  private translateService = inject(TranslateService);
  private languageService = inject(LanguageService);

  constructor() {
    // Luister naar taalwijzigingen
    effect(() => {
      const lang = this.languageService.onLanguageChange(); // Detecteer taalwijziging
      this.translateService.use(lang); // Stel de taal in
      this.loadCategories(); // Herlaad categorieën
    });
  }

  public loadCategories(): void {
    this.isFetching.set(true);

    this.dataLoaderService
      .loadDataWithFallback<Category[]>(
        `${environment.apiUrl}/categories`,
        'assets/test_data/categorie_en_product_data.json'
      )
      .subscribe({
        next: (categories) => {
          const translatedCategories = categories.map((category) => ({
            ...category,
            name: this.translateService.instant(`CATEGORY_${category.id}`),
            products: category.products.map((product) => ({
              ...product,
              name: this.translateService.instant(`PRODUCT_${product.id}`),
              description: this.translateService.instant(`DESCRIPTION_${product.id}`),
            })),
          }));
          this.categories.set(translatedCategories);
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
