import { Component, inject} from '@angular/core';
import { CartService } from '../services/cart.service';
import { NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-shopping-cart',
  imports: [
    NgIf,
    RouterLink,
    MatTableModule,
    FormsModule,
    MatButtonModule
  ],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.scss'
})
export class ShoppingCartComponent {

  private cartService = inject(CartService);
  cartItems = this.cartService.getCartItems(); // ✅ Signal ophalen
  displayedColumns: string[] = ['image', 'name','price', 'quantity', 'remove', 'total'];

  constructor(private translate: TranslateService) {
    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }

  removeItem(productId: number) {
    this.cartService.removeItem(productId)
  }

  clearCart() {
    this.cartService.clearCart();
  }

  totalPrice(): number {
    return this.cartService.getTotalPrice();
  }

}
