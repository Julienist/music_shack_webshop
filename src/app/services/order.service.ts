import { computed, Inject, inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CartService } from './cart.service';
import {forkJoin, Observable, of, tap} from 'rxjs';
import { environment } from '../../environments/environment.development';
import {Order, OrderProduct} from '../models/order.model';
import {Product} from '../models/product.model';
import {ProductsService} from './products.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private orderUrl = environment.apiUrl + '/orders';
  private httpClient = inject(HttpClient);
  private productsService = inject(ProductsService);
  private storageKey = 'pendingOrder';

  // Opslaan van een nieuwe order in een array
  saveOrderToLocalStorage(order: Order) {
    const existingOrders = this.getOrdersFromLocalStorage() || [];
    existingOrders.push(order);
    localStorage.setItem(this.storageKey, JSON.stringify(existingOrders));
  }

  getOrdersFromLocalStorage(): Order[] {
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data) : [];
  }

  // placeOrder(order: Order): Observable<Order> {
  //   return this.httpClient.post<Order>(`${this.orderUrl}`, order, {
  //     headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  //   });
  // }

  // sendLocalOrdersToBackend(): Observable<Order[]> {
  //   const localOrders = this.getOrdersFromLocalStorage();
  //
  //   if (!localOrders.length) return of([]); // Niks te verzenden

    // Map elke order naar een POST request
    // const requests = localOrders.map(order =>
      // this.httpClient.post<Order>(`${this.orderUrl}`, order, {
      //   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      // })
    // );

    // Als alle succesvol zijn: clear local storage
    // return forkJoin(requests).pipe(
    //   tap(() => this.clearLocalOrders())
    // );
  // }

  clearLocalOrders() {
    localStorage.removeItem(this.storageKey);
  }


  getOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.orderUrl}/my_orders`);
  }

  // hieronder de order data, als getters.

  getProductFromOrderProduct(op: OrderProduct): Product | undefined {
    return this.productsService.getProductByIdFromCache(op.productId);
  }

  /** 🎲 Haal een willekeurige productafbeelding uit de order */
  getRandomProductImage(order: Order): string | null {
    if (!order || !order.orderProducts.length) return null;

    const randomIndex = Math.floor(Math.random() * order.orderProducts.length);
    const randomOrderProduct = order.orderProducts[randomIndex];

    const product = this.productsService.getProductByIdFromCache(randomOrderProduct.productId);
    return product?.imageUrl || null;
  }

  /** ✅ Ophalen van alle productnamen */
  getProductNames(order: Order): string[] {
    return order.orderProducts.map(item => {
      const product = this.productsService.getProductByIdFromCache(item.productId);
      return product?.name ?? 'Onbekend product';
    });
  }

  /** ✅ Ophalen van alle productaantallen */
  getProductQuantities(order: Order): number[] {
    return order.orderProducts.map(item => item.quantity);
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

  // getProductNames(order: Order): string[] {
  //   return order.orderDetails.map(item => item.product.name);  // ✅ product.name gebruiken
  // }

  // getRandomProductImage(order: Order): string | null {
  //   if (!order || !order.orderProducts.length) return null;
  //   const randomIndex = Math.floor(Math.random() * order.orderProducts.length);
  //   return order.orderProducts[randomIndex].product.imageUrl || null;
  // }

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

