import {Component, computed, inject, signal, Signal} from '@angular/core';
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

  title = 'Products';

  private productsService = inject(ProductsService);
  private route = inject(ActivatedRoute);

  // Category from URL (of null)
  categoryId = signal<number | null>(null);
  // UI state
  isFetching = computed(() => this.productsService.isFetching());
  error = computed(() => this.productsService.error());

  // Dynamisch producten-filter op basis van categorie
  products = computed(() => {
    const id = this.categoryId();
    return id === null
      ? this.productsService.products()
      : this.productsService.getProductsByCategory(id);
  });


  ngOnInit(): void {
    // Altijd producten ophalen
    this.productsService.loadProducts();

    // Houd categorie uit route bij
    this.route.paramMap.subscribe((params: ParamMap) => {
      const id = params.get('id');
      this.categoryId.set(id ? Number(id) : null);
    });
  }

  // taal translate gedeelte
  constructor(private translate: TranslateService) {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }


}
