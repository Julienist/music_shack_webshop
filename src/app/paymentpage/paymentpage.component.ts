import { Component, inject, signal } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import { merge } from 'rxjs';
import { CartService } from '../services/cart.service';
import { OrderService } from '../services/order.service';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Order, OrderStatus } from '../models/order.model';
import { Product } from '../models/product.model';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-paymentpage',
  imports: [MatButtonModule, MatDividerModule, MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule],
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

  constructor(private translate: TranslateService) {
    merge(this.pincode.statusChanges, this.pincode.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateErrorMessage());

    this.translate.addLangs(['nl', 'en']);
    this.translate.setDefaultLang('nl');
    this.translate.use('en');
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

  async onPay() {
    if (this.isProcessing()) return;
    this.isProcessing.set(true);

    const order = this.createOrder();
    this.storeOrderLocally(order);

    try {
      await this.router.navigate(['/orders']);
      console.log('✅ Navigatie succesvol');
    } catch (err) {
      console.error('❌ Navigatie mislukt:', err);
    }
    // this.submitOrder(order);

  }


  private createOrder(): Order {
    return {
      orderDate: new Date(),
      orderStatus: OrderStatus.PAID,
      totalPrice: this.cartService.getTotalPrice(),
      orderProducts: this.cartService.getCartItems()().map((item: Product) => ({
        productId: item.id,
        quantity: item.quantity,
        totalPrice: item.price * item.quantity
      }))
    };
  }

  private storeOrderLocally(order: Order) {
    this.orderService.saveOrderToLocalStorage(order);
    console.log("Order opgeslagen in LocalStorage:", order);
  }

  // private submitOrder(order: Order) {
  //   this.orderService.sendLocalOrdersToBackend().subscribe({
  //     next: response => {
  //       console.log('✅ Order succesvol verstuurd:', response);
  //       this.cartService.clearCart();
  //       // this.router.navigate(['/orders']);
  //     },
  //     error: err => {
  //       console.error('❌ Fout bij order plaatsen:', err);
  //     },
  //     complete: () => {
  //       this.isProcessing.set(false);
  //     }
  //   });
  // }




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
