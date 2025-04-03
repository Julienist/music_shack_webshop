import { computed, Inject, inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CartService } from './cart.service';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Order } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = environment.apiUrl + '/orders';
  private httpClient = inject(HttpClient);
  private storageKey = 'pendingOrder';

  saveOrderToLocalStorage(order: Order) {
    localStorage.setItem(this.storageKey, JSON.stringify(order));
  }

  getOrderFromLocalStorage(): Order | null {
    const orderData = localStorage.getItem(this.storageKey);
    return orderData ? JSON.parse(orderData) : null;
  }

  placeOrder(order: Order): Observable<Order> {
    return this.httpClient.post<Order>(`${this.apiUrl}`, order);
  }

  getOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.apiUrl}/my_orders`);
  }

  // hieronder de order data, als getters.

  /** 🎲 Haal een willekeurige productafbeelding uit de order */
  getRandomProductImage(order: Order): string | null {
    if (!order || !order.orderDetails.length) return null;
    const randomIndex = Math.floor(Math.random() * order.orderDetails.length);
    return order.orderDetails[randomIndex].product.imageUrl || null;  // ✅ product.imageUrl gebruiken
  }

  /** ✅ Ophalen van alle productnamen */
  getProductNames(order: Order): string[] {
    return order.orderDetails.map(item => item.product.name);  // ✅ product.name gebruiken
  }

  /** ✅ Ophalen van alle productaantallen */
  getProductQuantities(order: Order): number[] {
    return order.orderDetails.map(item => item.quantity);
  }

  /** ✅ Ophalen van orderdatum */
  getOrderDate(order: Order): Date {
    return order.orderDate;
  }

  /** ✅ Ophalen van orderstatus */
  getOrderStatus(order: Order): string {
    return order.orderStatus;
  }

  /** ✅ Ophalen van totale prijs */
  getTotalPrice(order: Order): number {
    return order.totalPrice;
  }



  //----------------------------------
  // hieronder alle eerder geschreven code.



  // orders = signal<Order[]>([]);

  // constructor(private http: HttpClient, private cartService: CartService) {}

  // public createOrder(userId: string, token: string): Observable<Order> {
  //   const cartItems = this.cartService.getCartItems();
  //   if (cartItems.length === 0) {
  //     return new Observable(observer => observer.error("Winkelmandje is leeg"));
  //   }

    // const totalPrice = computed(() => this.cartService.getTotalPrice());

    // const order: Order = {
    //   customUserId: userId,
    //   orderDate: new Date(),
    //   orderStatus: 'Pending',
    //   totalPrice: totalPrice(),
    //   //Testen met map, is ENG. DUS hieronder kunnen problemen zitten.
    //   orderDetails: cartItems.map(item => ({
    //     productId: item.id,
    //     quantity: item.quantity,
    //     totalPrice: item.price * item.quantity
    //   }))
    // };

    // Headers met Authorization Bearer Token
    // const headers = new HttpHeaders({
    //   'Content-Type': 'application/json',
    //   'Authorization': `Bearer ${token}`
    // });

    // return this.httpClient.post<Order>(this.apiUrl, order) 
    // .subscribe(response => {
    //     console.log('Order geplaatst:', response);
    //   });
      
}

