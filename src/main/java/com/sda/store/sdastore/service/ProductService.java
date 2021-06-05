package com.sda.store.sdastore.service;

import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.ProductType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Product createProduct(Product product);
    Product findById (Long id);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    Page<Product> searchProducts(Map<String, String> params);
    List<ProductType> getProductTypes();
}
