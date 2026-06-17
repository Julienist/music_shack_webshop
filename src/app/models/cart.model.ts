import { Product } from "./product.model";

export interface Cart {
    cartProducts: CartProduct[];
    isActive: boolean;
}

export interface CartProduct {
    cart: Cart;
    product: Product;
    quantity: number;
    totalPrice: number;
}
