import {OrderProduct} from './order.model';

export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    imageurl: string;
    version: number;
    amountInStock: number;
    available: boolean;
    orderProducts: Array<null | OrderProduct>;
    quantity: number;
    categoryId: number;
}
