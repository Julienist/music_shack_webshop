import { computed, Inject, inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CartService } from './cart.service';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Order } from '../models/order.model'; // Importeer de interface

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = environment.apiUrl + '/orders/my_order'; // URL van backend API
  private httpClient = inject(HttpClient);
  private cartService = inject(CartService);
  orders = signal<Order[]>([]);

  // constructor(private http: HttpClient, private cartService: CartService) {}

  public createOrder(userId: string, token: string): Observable<Order> {
    const cartItems = this.cartService.getCartItems();
    if (cartItems.length === 0) {
      return new Observable(observer => observer.error("Winkelmandje is leeg"));
    }

    const totalPrice = computed(() => this.cartService.getTotalPrice());

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

    return this.httpClient.post<Order>(this.apiUrl, order)
    .subscribe(response => {
        console.log('Order geplaatst:', response);
      });
      
  }
}
