package com.sda.store.sdastore.repository;

import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

//    Product findByName(String description);
//    Product findByPrice(Double price);
//    Product findByProductType(ProductType productType);
}
