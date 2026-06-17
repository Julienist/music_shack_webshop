import {OrderProduct} from './order.model';

export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    imageurl: string;
    version: number;
    amountInStock: number;
    orderProducts: Array<null | OrderProduct>;
    available: boolean;
    quantity: number;
    categoryId: number;
}
