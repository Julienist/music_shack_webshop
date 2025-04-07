import { Injectable, Signal, signal } from '@angular/core';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems = signal<Product[]>([]);

  constructor() {
    const savedCart = JSON.parse(localStorage.getItem("cart") || "[]");
    this.cartItems.set(savedCart);
  }

  public addToCart(product: Product) {
    const existingProduct = this.cartItems().find(p => p.id === product.id);

    if (existingProduct) {
      existingProduct.quantity++;
    } else {
      this.cartItems.set([...this.cartItems(), { ...product, quantity: 1 }]);
    }

    console.log("winkelmandje bijgewerkt: ", this.cartItems());
    localStorage.setItem("cart", JSON.stringify(this.cartItems()));
  }

  public removeItem(productId: number) {
    const updatedCart = this.cartItems().filter(item => item.id !== productId);
    this.cartItems.set(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
  }

  /** Winkelmandje leegmaken */
  public clearCart() {
    this.updateCart([]);
  }

  /** Slaat winkelmandje op in localStorage en update Signal */
  private updateCart(newCart: Product[]) {
    this.cartItems.set([...newCart]); // Signal updaten
    localStorage.setItem('cart', JSON.stringify(newCart)); // Opslaan in localStorage
  }

  public getCartItems(): Signal<Product[]> {
    return this.cartItems;
  }

  /** Winkelmandje opslaan in localStorage */
  private saveCart() {
    localStorage.setItem('cart', JSON.stringify(this.cartItems()));
  }

  /** Laadt winkelmandje bij opstarten */
  private loadCartFromStorage(): Product[] {
    const savedCart = localStorage.getItem('cart');
    return savedCart ? JSON.parse(savedCart) : [];
  }

  public checkIfCartHasItems(): boolean {
    return this.cartItems().length > 0;
  }

  public getAmountOfCartItems(): number {
    if (!this.checkIfCartHasItems()) return 0;

    return this.cartItems().reduce((total, item) => total + item.quantity, 0);
  }


  public getTotalPrice(): number {
    if (!this.checkIfCartHasItems()) return 0;

    const total = this.cartItems().reduce((sum, item) => sum + item.price * item.quantity, 0);
    return parseFloat(total.toFixed(2));
  }



  // evt. cart to db -> vergt her-bouwing van ERD en db,
  // zorg eerst voor orders.
}
