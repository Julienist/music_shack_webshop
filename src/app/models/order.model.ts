export interface Order {
    customUserId: string;
    orderDate: Date;
    orderStatus: 'Pending' | 'Shipped'|'Processing' | 'Delivered' | 'Cancelled' | 'PAID'; // Enum-opties
    totalPrice: number;
    // orderDetails: OrderDetail[];
  }
  
  export interface OrderDetail {
    orderId: number;
    productId: number;
    quantity: number;
    totalPrice: number;
  }
  