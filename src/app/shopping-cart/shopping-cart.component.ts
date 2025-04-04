import { Component, inject, OnInit } from '@angular/core';
import { CartService } from '../services/cart.service';
import { Product } from '../models/product.model';
import { NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-shopping-cart',
  imports: [NgIf, RouterLink],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.scss'
})
export class ShoppingCartComponent {

  private cartService = inject(CartService);
  cartItems = this.cartService.getCartItems(); // ✅ Signal ophalen

  // onderstaande OnInit weghalen, testing purposes
  ngOnInit() {
    console.log("shopping-cart geladen met: ", this.cartItems())
  }

  removeItem(productId: number) {
    // this.cartService.deleteItem(productId);
    this.cartService.removeItem(productId)
  }

  clearCart() {
    this.cartService.clearCart();
  }

  totalPrice(): number {
    return this.cartService.getTotalPrice();
  }

}
