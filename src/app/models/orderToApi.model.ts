import {OrderStatus} from './order.model';

export interface ApiOrder {
  orderDate: Date;
  orderStatus: OrderStatus;
  totalPrice: number;
  orderProducts: orderProducts[];
}

export interface orderProducts {
  productId: number;
  quantity: number;
  totalPrice: number;
}
