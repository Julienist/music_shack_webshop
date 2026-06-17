

export interface orderFromApi {
  id: number;
  orderDate: Date;
  orderStatus: string;
  totalPrice: number;
  orderProducts: orderProductFromApi[];
}

export interface orderProductFromApi {
  id: number;
  quantity: number;
  totalPrice: number;
}
