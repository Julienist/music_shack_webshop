import { Injectable, signal } from '@angular/core';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems = signal<Product[]>(this.loadCartFromStorage());

  constructor() {}

  /** ✅ Product toevoegen aan winkelmandje */
  addToCart(product: Product) {
    const currentCart = this.cartItems();
    const existingItem = currentCart.find(p => p.id === product.id);

    if (existingItem) {
      existingItem.quantity = (existingItem.quantity || 1) + 1;
    } else {
      this.cartItems.set([...currentCart, { ...product, quantity: 1 }]);
    }

    this.saveCart();
  }

  /** ❌ Product verwijderen */
  deleteItem(productId: number) {
    this.updateCart(this.cartItems().filter(p => p.id !== productId));
  }

  /** 🛒 Winkelmandje leegmaken */
  clearCart() {
    this.updateCart([]); // ✅ Leegt de winkelwagen
  }

  /** 🔄 Slaat winkelmandje op in localStorage en update Signal */
  private updateCart(newCart: Product[]) {
    this.cartItems.set([...newCart]); // ✅ Signal updaten
    localStorage.setItem('cart', JSON.stringify(newCart)); // ✅ Opslaan in localStorage
  }

  /** 🛒 Alle producten in winkelmandje */
  getCartItems() {
    return this.cartItems;
  }

  /** 🛒 Winkelmandje opslaan in localStorage */
  private saveCart() {
    localStorage.setItem('cart', JSON.stringify(this.cartItems()));
  }

  /** 🔄 Laadt winkelmandje bij opstarten */
  private loadCartFromStorage(): Product[] {
    const savedCart = localStorage.getItem('cart');
    return savedCart ? JSON.parse(savedCart) : [];
  }
}
