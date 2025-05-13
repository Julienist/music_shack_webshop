import { inject, Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Order, OrderProduct, OrderStatus } from '../models/order.model';
import { ApiOrder } from '../models/orderToApi.model';
import { Product} from '../models/product.model';
import { ProductsService } from './products.service';
import { orderFromApi } from '../models/orderFromApi.model';
import { map } from 'rxjs/operators';

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

  public getOrdersFromAPI(): Observable<Order[]> {
    return this.httpClient.get<orderFromApi[]>(`${this.orderUrl}/my_orders`).pipe(
      map((ordersFromApi: orderFromApi[]) => {
        return ordersFromApi.map(orderFromApi => ({
          orderDate: new Date(orderFromApi.orderDate),
          orderStatus: orderFromApi.orderStatus as OrderStatus,
          totalPrice: orderFromApi.totalPrice,
          orderProducts: orderFromApi.orderProducts.map(orderProductFromApi => {
            const product = this.productsService.getProductByIdFromCache(orderProductFromApi.id);
            if (!product) {
              throw new Error(`Product with ID ${orderProductFromApi.id} not found in cache.`);
            }
            return {
              product,
              quantity: orderProductFromApi.quantity,
              totalPrice: orderProductFromApi.totalPrice,
            };
          }),
        }));
      })
    );
  }

  clearLocalOrders() {
    localStorage.removeItem(this.storageKey);
  }

  public saveOrderToApi(order: ApiOrder): Observable<string> {
    return this.httpClient.post<string>(this.orderUrl, order, { responseType: 'text' as 'json' });
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

