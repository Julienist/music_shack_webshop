import { Product } from "./product.model";

export enum OrderStatus {
  PENDING = 'PENDING',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  PAID = 'PAID'
}

export interface Order {

  orderDate: Date;
  orderStatus: OrderStatus;
  totalPrice: number;
  orderProducts: OrderProduct[];
}

export interface OrderProduct {
  productId: number;
  quantity: number;
  totalPrice: number;
}
