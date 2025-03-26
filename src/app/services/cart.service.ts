import { Injectable, Signal, signal } from '@angular/core';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  // private cartItems = signal<Product[]>(this.loadCartFromStorage());
  private cartItems = signal<Product[]>([]);

  constructor() {
    const savedCart = JSON.parse(localStorage.getItem("cart") || "[]");
    this.cartItems.set(savedCart);
  }

  // V1 addToCart()
  /** ✅ Product toevoegen aan winkelmandje */
  // addToCart(product: Product) {
  //   const currentCart = this.cartItems();
  //   const existingItem = currentCart.find(p => p.id === product.id);

  //   if (existingItem) {
  //     existingItem.quantity = (existingItem.quantity || 1) + 1;
  //   } else {
  //     this.cartItems.set([...currentCart, { ...product, quantity: 1 }]);
  //   }

  //   this.saveCart();
  // }

  // V2 addToCart()
  // addToCart(product: Product) {
    // const updatedCart = [...this.cartItems()];
    // const existingItem = updatedCart.find(item => item.id === product.id);

  //   if (existingItem) {
  //     existingItem.quantity! += 1;
  //   } else {
  //     updatedCart.push({ ...product, quantity: 1 }); // ✅ Product wordt correct opgeslagen
  //   }

  //   this.cartItems.set(updatedCart);
  //   localStorage.setItem('cart', JSON.stringify(updatedCart)); // ✅ Opslaan in localStorage
  // }

  addToCart(product: Product) {
    const existingProduct = this.cartItems().find(p => p.id === product.id);
    
    if (existingProduct) {
      existingProduct.quantity++;
    } else {
      this.cartItems.set([...this.cartItems(), { ...product, quantity: 1 }]);
    }
    
    console.log("winkelmandje bijgewerkt: ", this.cartItems());
    localStorage.setItem("cart", JSON.stringify(this.cartItems()));
  }
  


  /** ❌ Product verwijderen */
  // deleteItem(productId: number) {
  //   this.updateCart(this.cartItems().filter(p => p.id !== productId));
  // }
  removeItem(productId: number) {
    const updatedCart = this.cartItems().filter(item => item.id !== productId);
    this.cartItems.set(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
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
  // getCartItems() {
  //   return this.cartItems;
  // }
  public getCartItems(): Signal<Product[]> {
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

  public checkIfCartHasItems(): boolean {
    return this.cartItems().length > 0;
  }
  

  public getAmountOfCartItems(): number {
    if (!this.checkIfCartHasItems()) return 0;
    
    return this.cartItems().reduce((total, item) => total + item.quantity, 0);
  }
  

  public getTotalPrice(): number {
    if (!this.checkIfCartHasItems()) return 0;
    
    return this.cartItems().reduce((total, item) => total + item.price * item.quantity, 0);
  }
  

  // evt. cart to db -> vergt her-bouwing van ERD en db,
  // zorg eerst voor orders.
}
