export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    imageUrl: string;
    version: number;
    stock: number;
    available: boolean;
    orderProducts: Array<null>;
    quantity: number;
}
