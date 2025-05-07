import { inject, Injectable} from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
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

  clearLocalOrders() {
    localStorage.removeItem(this.storageKey);
  }

  getOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.orderUrl}/my_orders`);
  }

  public saveOrderToApi(order: Order): Observable<Order> {
    return this.httpClient.post<Order>(this.orderUrl, order);
  }

  // hieronder de order data, als getters.

  getProductFromOrderProduct(op: OrderProduct): Product | null {
    return this.productsService.getProductByIdFromCache(op.product.id);
  }

  /** 🎲 Haal een willekeurige productafbeelding uit de order */
  getRandomProductImage(order: Order): string | null {
    if (!order || !order.orderProducts.length) return null;

    const randomIndex = Math.floor(Math.random() * order.orderProducts.length);
    const randomOrderProduct = order.orderProducts[randomIndex];

    const product = this.productsService.getProductByIdFromCache(randomOrderProduct.product.id);
    return product?.imageurl || null;
  }

  /** ✅ Ophalen van alle productnamen */
  getProductNames(order: Order): string[] {
    return order.orderProducts.map(item => {
      const product = this.productsService.getProductByIdFromCache(item.product.id);
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

}

