import { Product } from "./product.model";

export enum OrderStatus {
  PENDING = 'PENDING',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  PAID = 'PAID'
}

export interface Order {
  customUserId: number;
  orderDate: Date;
  orderStatus: OrderStatus;
  totalPrice: number;
  orderDetails: OrderDetail[];
}

export interface OrderDetail {
  orderId: number;
  // productId: number;
  product: Product;
  quantity: number;
  totalPrice: number;
}
