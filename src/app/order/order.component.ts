import { Component, inject } from '@angular/core';
import { OrderService } from '../services/order.service';
import { Order } from '../models/order.model';
import {DatePipe, DecimalPipe, NgFor, NgIf} from '@angular/common';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [
    NgIf,
    DatePipe,
    DecimalPipe,
    NgFor
  ],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent {
  private orderService = inject(OrderService);
  orders: Order[] = [];

  constructor(private translate: TranslateService) {
    this.loadOrders();

    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
  }


  loadOrders(): void {
    this.loadLocalOrders()
  }

  // loadOrders() {
  //   this.orderService.getOrders().subscribe({
  //     next: (orders: Order[]) => {
  //       // this.orders = [...orders]; // Vanuit backend
  //       this.loadLocalOrders(); // Voeg lokale orders toe
  //     },
  //     error: (err: Error) => {
  //       //comment/debug: testing debug
  //       console.error('❌ Fout bij ophalen orders:', err);
  //       this.loadLocalOrders(); // Fallback naar lokale data
  //     }
  //   });
  // }


  loadLocalOrders() {
    const localOrders = this.orderService.getOrdersFromLocalStorage();
    if (localOrders.length > 0) {
      this.orders.push(...localOrders);
      //comment/debug: testing debug
      console.log('📦 Lokale orders geladen:', localOrders);
    }
  }

  getRandomImage(order: Order): string | null {
    return this.orderService.getRandomProductImage(order);
  }

  getProductNames(order: Order): string[] {
    return this.orderService.getProductNames(order);
  }

  getProductQuantities(order: Order): number[] {
    return this.orderService.getProductQuantities(order);
  }

  getOrderDate(order: Order): Date {
    return this.orderService.getOrderDate(order);
  }

  getOrderStatus(order: Order): string {
    return this.orderService.getOrderStatus(order);
  }

  getTotalPrice(order: Order): number {
    return this.orderService.getTotalPrice(order);
  }
}
