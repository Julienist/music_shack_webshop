import { Component, inject, signal } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { merge } from 'rxjs';
import { CartService } from '../services/cart.service';
import { OrderService } from '../services/order.service';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Order, OrderStatus } from '../models/order.model';
import { ApiOrder } from '../models/orderToApi.model';
import { Product } from '../models/product.model';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-paymentpage',
  imports: [
    MatButtonModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    TranslatePipe
  ],
  templateUrl: './paymentpage.component.html',
  styleUrl: './paymentpage.component.scss'
})
export class PaymentpageComponent {
  readonly pincode = new FormControl('', [Validators.required, Validators.minLength(4)]);
  // private hasPayed: boolean = false;
  private cartService = inject(CartService);
  private orderService = inject(OrderService);
  private userService = inject(UserService);
  private router = inject(Router);

  cartItems = this.cartService.getCartItems();
  totalPrice = this.cartService.getTotalPrice();

  errorMessage = signal('');
  isProcessing = signal(false);

  constructor() {
    merge(this.pincode.statusChanges, this.pincode.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateErrorMessage());
  }

  updateErrorMessage() {
    if (this.pincode.hasError('required')) {
      this.errorMessage.set("Je moet minimaal 4 cijfers invullen. De pincode word NIET opgeslagen");
    } else if (this.pincode.hasError('pincode')) {
      this.errorMessage.set('Not a valid pin');
    } else {
      this.errorMessage.set('');
    }
  }

  // Onderstaande kan beter in payment-service worden geplaatst.
  // HIERVOOR IS NIET GEKOZEN, wegens dat payments niet worden gekozen.

  async onPay(): Promise<void> {
    if (this.isProcessing()) return;
    this.isProcessing.set(true);

    const order = this.createOrder();
    // deactivated because of loading orders from API
    // this.storeOrderLocally(order);
    this.submitOrder(order);
    this.orderService.getOrdersFromAPI().subscribe(orders => {})
    // this.placeOrder(order);
    try {
      await this.router.navigate(['/orders']);
      console.log('✅ Navigatie succesvol');
    } catch (err) {
      console.error('❌ Navigatie mislukt:', err);
    }


  }


  private createOrder(): Order {
    return {
      orderDate: new Date(),
      orderStatus: OrderStatus.PAID,
      totalPrice: this.cartService.getTotalPrice(),
      orderProducts: this.cartService.getCartItems()().map((item: Product) => ({
        product: item,
        quantity: item.quantity,
        totalPrice: item.price * item.quantity
      }))
    };
  }

  // deactivated because of loading orders from API
  // private storeOrderLocally(order: Order) {
    // this.orderService.saveOrderToLocalStorage(order);
    // console.log("Order opgeslagen in LocalStorage:", order);
  // }

  private submitOrder(order: Order) {
    const transformedOrder = this.transformOrderForApi(order);

    this.orderService.saveOrderToApi(transformedOrder).subscribe({
      next: response => {
        console.log('✅ Order succesvol verstuurd:', response); // Response is nu een tekst
        this.cartService.clearCart();
        this.router.navigate(['/orders']);
      },
      error: err => {
        console.error('❌ Fout bij order plaatsen:', err);
      },
      complete: () => {
        this.isProcessing.set(false);
      }
    });
  }

  private transformOrderForApi(order: Order): ApiOrder {
    return {
      orderDate: order.orderDate,
      orderStatus: order.orderStatus,
      totalPrice: order.totalPrice,
      orderProducts: order.orderProducts.map(op => ({
        productId: op.product.id, // Use only the product ID
        quantity: op.quantity,
        totalPrice: op.totalPrice
      }))
    };
  }




    // if (this.hasPayed === true) {
    //   const order = this.createOrder();
    //   this.placeOrder(order);
    // }


  /** Maak order-object aan */
  // private createOrder() {
  //   return {
  //     userId: this.userService.getUserId(),
  //     orderDate: new Date(),
  //     orderStatus: 'Pending',
  //     totalPrice: this.cartService.getTotalPrice(),
  //     orderDetails: this.cartItems().map(item => ({
  //       productId: item.id,
  //       quantity: item.quantity,
  //       totalPrice: item.price * item.quantity
  //     }))
  //   };
  // }

  /** 📦 Plaats bestelling via OrderService */
  // private placeOrder(order: Order) {
  //   this.orderService.placeOrder(order)
  //   .subscribe({
  //     next: response => {
  //       console.log('✅ Order succesvol geplaatst:', response);
  //       this.cartService.clearCart(); // 🛒 Winkelmand legen
  //       this.router.navigate(['/order-confirmation']);
  //     },
  //     error: err => {
  //       console.error('❌ Order mislukt:', err);
  //     },
  //     complete: () => {
  //       this.isProcessing.set(false);
  //     }
  //   });
  // }

  // this.orderService.placeOrder({
  //   customUserId: this.userId,
  //   orderDate: new Date(),
  //   orderStatus: OrderStatus.PENDING, // ✅ Enum gebruiken
  //   totalPrice: this.cartService.getTotalPrice(),
  //   orderDetails: this.cartService.getCartItems()
  //     .map(item => ({
  //       orderId: 0, // Wordt door de backend gegenereerd
  //       productId: item.id,
  //       quantity: item.quantity,
  //       totalPrice: item.price * item.quantity
  //     }))
  //   }).subscribe(order => {
  //       console.log('Order geplaatst:', order);
  //       this.cartService.clearCart(); // ✅ Winkelmand legen na bestelling
  //     });

}
