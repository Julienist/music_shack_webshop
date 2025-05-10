import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatFormField } from '@angular/material/input';
import { MatLabel } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatOption } from '@angular/material/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';
import { MatInput } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';


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
    ReactiveFormsModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  private translate =  inject(TranslateService);

  ngOnInit(): void {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }

  useLanguage(language: string): void {
    this.translate.use(language);
  }

  searchControl= new FormControl('');
  filteredOptions: Observable<string[]> = of([]);


}
