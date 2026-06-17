import { Product } from "./product.model";

export enum OrderStatus {
  PENDING = 'PENDING',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  PAID = 'PAID'
}

export interface Order {
  // email: string;
  orderDate: Date;
  orderStatus: OrderStatus;
  totalPrice: number;
  orderProducts: OrderProduct[];
}

export interface OrderProduct {
  product: Product;
  quantity: number;
  totalPrice: number;
}
