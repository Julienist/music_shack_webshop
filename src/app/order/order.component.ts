import { Component, inject } from '@angular/core';
import { OrderService } from '../services/order.service';
import { Order } from '../models/order.model';

@Component({
  selector: 'app-order',
  imports: [],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent {
  private orderService = inject(OrderService);
  orders: Order[] = [];

  constructor() {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getOrders().subscribe({
      next: (orders: Order[]) => {
        this.orders = orders;
        console.log('Orders geladen:', orders);
      },
      error: (err: Error) => {
        console.error('❌ Fout bij ophalen orders:', err);
      }
    });
  }

  getImage(order: Order): string | null {
    return this.orderService.getRandomProductImage(order);
  }

  getNames(order: Order): string[] {
    return this.orderService.getProductNames(order);
  }

  getQuantities(order: Order): number[] {
    return this.orderService.getProductQuantities(order);
  }

  getDate(order: Order): Date {
    return this.orderService.getOrderDate(order);
  }

  getStatus(order: Order): string {
    return this.orderService.getOrderStatus(order);
  }

  getPrice(order: Order): number {
    return this.orderService.getTotalPrice(order);
  }
}
