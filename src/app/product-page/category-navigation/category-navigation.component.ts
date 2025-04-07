import { Component } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-category-navigation',
  imports: [],
  templateUrl: './category-navigation.component.html',
  styleUrl: './category-navigation.component.scss'
})
export class CategoryNavigationComponent {

  constructor(private translate: TranslateService) {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }

}
