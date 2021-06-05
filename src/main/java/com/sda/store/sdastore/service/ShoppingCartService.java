package com.sda.store.sdastore.service;

import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart addProductToCart(Product product, ShoppingCart shoppingCart);
    ShoppingCart removeProductFromCart(Product product, ShoppingCart shoppingCart);
    void clearShoppingCart(Long shoppingCartId);
}
