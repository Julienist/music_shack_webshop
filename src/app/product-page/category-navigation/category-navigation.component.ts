import {Component, inject, Signal} from '@angular/core';
import { TranslateService, TranslatePipe } from '@ngx-translate/core';
import { MatTabsModule } from '@angular/material/tabs';
import {CategoryService} from '../../services/category.service';
import {Router} from '@angular/router';
import {Category} from '../../models/category.model';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-category-navigation',
  imports: [MatTabsModule, NgFor, TranslatePipe],
  templateUrl: './category-navigation.component.html',
  styleUrl: './category-navigation.component.scss'
})
export class CategoryNavigationComponent {
  private categoryService = inject(CategoryService);
  private router = inject(Router);

  categories: Signal<Category[]> = this.categoryService.categoriesList;

  ngOnInit() {
    this.categoryService.loadCategories();
  }

  selectCategory(categoryId: number | null) {
    const route = categoryId === null ? ['/producten/AlleProducten'] : ['/producten/categorie', categoryId];
    this.router.navigate(route).catch((err) => {
      console.error('Navigatiefout:', err);
    });
  }

  onTabChange(event: any) {
    const selectedIndex = event.index;
    if (selectedIndex === 0) {
      this.selectCategory(null); // "Alle producten"
    } else {
      const category = this.categories()[selectedIndex - 1]; // Correcte index corrigeren
      this.selectCategory(category.id);
    }
  }

  trackByCategoryId(index: number, category: Category): number {
    return category.id;
  }

}
