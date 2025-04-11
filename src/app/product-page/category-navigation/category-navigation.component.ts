import {Component, inject, Signal} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {CategoryService} from '../../services/category.service';
import {Router} from '@angular/router';
import {Category} from '../../models/category.model';

@Component({
  selector: 'app-category-navigation',
  imports: [],
  templateUrl: './category-navigation.component.html',
  styleUrl: './category-navigation.component.scss'
})
export class CategoryNavigationComponent {
  private categoryService = inject(CategoryService);
  private router = inject(Router);

  categories: Signal<Category[]> = this.categoryService.categoriesList;

  constructor() {
    this.categoryService.loadCategories();
  }

  selectCategory(categoryId: number | null) {
    const route = categoryId === null ? ['/producten/AlleProducten'] : ['/producten/categorie', categoryId];
    this.router.navigate(route).catch((err) => {
      console.error('Navigatiefout:', err);
    });
  }



  //hieronder taal-translation regelen
  // constructor(private translate: TranslateService) {
  //   this.translate.addLangs(['nl', 'en']);
  //   this.translate.setDefaultLang('nl');
  //   this.translate.use('en');
  // }

}
