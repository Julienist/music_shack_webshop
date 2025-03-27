export interface Order {
    customUserId: string;
    orderDate: Date;
    orderStatus: 'Pending' | 'Processing' | 'Shipped' | 'Delivered' | 'Cancelled'; // Enum-opties
    totalPrice: number;
    orderDetails: OrderDetail[];
  }
  
  export interface OrderDetail {
    productId: number;
    quantity: number;
    totalPrice: number;
  }
  