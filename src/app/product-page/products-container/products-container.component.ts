import {Component, computed, inject, signal, WritableSignal} from '@angular/core';
import { ProductComponent } from "../../products/products.component";
import { Product } from '../../models/product.model';
import { ProductsService } from '../../services/products.service';
import { CommonModule } from '@angular/common';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {ActivatedRoute, ParamMap} from '@angular/router';

@Component({
  selector: 'app-products-container',
  standalone: true,
  imports: [
    ProductComponent,
    CommonModule,
    // TranslatePipe
  ],
  templateUrl: './products-container.component.html',
  styleUrl: './products-container.component.scss'
})
export class ProductsContainerComponent {

  private productsService = inject(ProductsService);
  private route = inject(ActivatedRoute);
  private translate = inject(TranslateService);

  // Category from URL (of null)
  categoryId = signal<number | null>(null);

  // Signal for search query
  searchQuery = signal<string>('');

  // Filtered products by category
  productsByCategory = computed(() => {
    const id = this.categoryId();
    return id === null
      ? this.productsService.products()
      : this.productsService.getProductsByCategory(id);
  });

  // Final filtered products by both category and name
  products = computed(() => {
    const query = (this.searchQuery() || '').toLowerCase(); // Default to empty string if query is null
    const filteredByCategory = this.productsByCategory();
    return filteredByCategory.filter(product =>
      product.name.toLowerCase().includes(query)
    );
  });

  // UI state
  isFetching = computed(() => this.productsService.isFetching());
  error = computed(() => this.productsService.error());

  ngOnInit(): void {
    console.log('ProductsContainerComponent initialized');
    
    // Ensure products are loaded
    this.productsService.loadProducts();

    // Log products dynamically when they are updated
    const productsSignal = this.productsService.products();
    console.log('Products signal initialized:', this.products());

    // Houd categorie uit route bij
    this.route.paramMap.subscribe((params: ParamMap) => {
      const id = params.get('id');
      this.categoryId.set(id ? Number(id) : null);
    });

    // taal translate gedeelte
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }

  updateSearchQuery(query: string): void {
    this.searchQuery.set(query); // Update the search query signal
  }

}
