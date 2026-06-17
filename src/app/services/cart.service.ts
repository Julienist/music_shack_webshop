import { inject, Injectable, Signal, signal } from '@angular/core';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment.development';
import { Cart } from '../models/cart.model';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems = signal<Product[]>([]);
  private httpClient = inject(HttpClient);
  private cartUrl = environment.apiUrl + '/carts';
  private cartProducts: Product[] = [];


  constructor() {
    const savedCart = JSON.parse(localStorage.getItem("cart") || "[]");
    this.cartItems.set(savedCart);
  }

  public addToCart(product: Product): void {
    const existingProduct = this.cartItems().find(p => p.id === product.id);

    if (existingProduct) {
      existingProduct.quantity++;
    } else {
      this.cartItems.set([...this.cartItems(), { ...product, quantity: 1 }]);
    }

    console.log("winkelmandje bijgewerkt: ", this.cartItems());
    localStorage.setItem("cart", JSON.stringify(this.cartItems()));
  }

  public removeItem(productId: number): void {
    const updatedCart = this.cartItems().filter(item => item.id !== productId);
    this.cartItems.set(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
  }

  /** Winkelmandje leegmaken */
  public clearCart(): void {
    this.updateCart([]);
  }

  /** Slaat winkelmandje op in localStorage en update Signal */
  private updateCart(newCart: Product[]): void {
    this.cartItems.set([...newCart]); // Signal updaten
    localStorage.setItem('cart', JSON.stringify(newCart)); // Opslaan in localStorage
  }

  public getCartItems(): Signal<Product[]> {
    return this.cartItems;
  }

  /** Winkelmandje opslaan in localStorage */
  private saveCart(): void {
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


  // public getCartFromApi(): Observable<Cart> {
  //   return this.httpClient.get<Cart>(this.cartUrl);
  // }

    /** Haal winkelmandje op van de API */
  //als user is ingelogd, save cart to API.
  // Als user niet is ingelogd, haal cart op van localStorage.
  public saveCartToApi(cart: Cart): Observable<Cart> {
    return this.httpClient.post<Cart>(this.cartUrl, cart);
  }
}


