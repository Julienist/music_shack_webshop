import { Component, inject, OnInit } from '@angular/core';
import { CartService } from '../services/cart.service';
import { Product } from '../models/product.model';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-shopping-cart',
  imports: [NgIf],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.scss'
})
export class ShoppingCartComponent {

  private cartService = inject(CartService);
  cartItems = this.cartService.getCartItems(); // ✅ Signal ophalen

  removeItem(productId: number) {
    this.cartService.deleteItem(productId);
  }

  clearCart() {
    this.cartService.clearCart();
  }

}
