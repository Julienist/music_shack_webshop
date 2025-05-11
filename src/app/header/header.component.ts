import { Component, inject, EventEmitter, Output } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgFor, CommonModule } from '@angular/common'; // Add CommonModule
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule} from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatFormField } from '@angular/material/input';
import { MatLabel } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInput } from '@angular/material/input';
import { MatOption } from '@angular/material/core';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';
import { map, startWith } from 'rxjs/operators';
import { TranslatePipe, TranslateService} from '@ngx-translate/core';
import { ProductsService } from '../services/products.service';
import { Product } from '../models/product.model';
import { LanguageService } from '../services/language.service';

@Component({
  selector: 'app-header',
  imports: [
    MatIconModule,
    RouterModule,
    MatToolbarModule,
    MatMenuModule,
    MatFormField,
    MatLabel,
    MatAutocompleteModule,
    MatOption,
    MatInput,
    ReactiveFormsModule,
    NgFor,
    CommonModule,
    TranslatePipe
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  private translate = inject(TranslateService);
  private languageService = inject(LanguageService); // Injecteer LanguageService
  private productsService = inject(ProductsService);

  @Output() filteredProducts = new EventEmitter<Product[]>();
  @Output() searchQueryChanged = new EventEmitter<string>();

  searchControl = new FormControl('');
  filteredOptions: Observable<string[]> = of(this.productsService.productnames); // Initialize with all product names

  isSearchVisible = true; // Flag to control visibility of the search-container

  ngOnInit(): void {

    this.translate.setDefaultLang('nl');

    console.log('HeaderComponent initialized');
    console.log('Initial product names:', this.productsService.productnames);

    // Dynamically filter product names from ProductsService
    this.filteredOptions = this.searchControl.valueChanges.pipe(
      startWith(''),
      map(value => {
        const query = (value ?? '').toLowerCase(); // Ensure value is a string
        const productNames = this.productsService.productnames;
        console.log('Filtered product names:', productNames);
        return productNames.filter(name =>
          name.toLowerCase().includes(query)
        );
      })
    );

    this.searchControl.valueChanges.pipe(
      startWith(''),
      map(value => (value ?? '').toString()) // Ensure value is a string, default to ''
    ).subscribe(query => {
      this.searchQueryChanged.emit(query);
    });
  }

  switchLanguage(lang: string): void {
    this.translate.use(lang); // Wijzig de taal in TranslateService
    this.languageService.setLanguage(lang); // Update de taal in LanguageService
  }

  contrastMode(): void {
    const body = document.body;
    body.classList.toggle('dark-mode'); // Toggle the dark-mode class
  }

  hideSearch(): void {
    this.isSearchVisible = false; // Hide the search-container
  }

  showSearch(): void {
    this.isSearchVisible = true; // Show the search-container
  }

  onProductSelected(productName: string): void {
    const filteredProducts = this.productsService.filterProductsByName(productName);
    this.filteredProducts.emit(filteredProducts);
  }
  onSearchInputChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    const query = input.value.toLowerCase();
    this.searchQueryChanged.emit(query);
  }
}
