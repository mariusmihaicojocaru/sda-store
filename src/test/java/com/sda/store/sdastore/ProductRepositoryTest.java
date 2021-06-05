package com.sda.store.sdastore;

import com.sda.store.sdastore.model.Category;
import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.ProductType;
import com.sda.store.sdastore.repository.CategoryRepository;
import com.sda.store.sdastore.repository.ProductRepository;
import com.sda.store.sdastore.repository.ProductSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void cleanup() {
        productRepository.findAll().forEach(product -> productRepository.delete(product));
        categoryRepository.findAll().forEach(category -> categoryRepository.delete(category));
    }

    @Test
    public void testSave(){
        Category drinks = new Category();
        drinks.setName("Drinks");
        drinks = categoryRepository.save(drinks);

        Product cocaCola = new Product();
        cocaCola.setName("Coca-cola");
        cocaCola.setPrice(2.0);
        cocaCola.setCategory(drinks);
        cocaCola.setDescription("DaBest");
        cocaCola.setProductType(ProductType.BEVERAGES);

        cocaCola = productRepository.save(cocaCola);

        Assertions.assertNotNull(cocaCola.getId());
        Assertions.assertNotNull(cocaCola.getCategory());
        Assertions.assertNotNull(cocaCola.getName());
    }

    @Test
    public void searchProductsByName(){
        Product tv = new Product();
        tv.setProductType(ProductType.APPLIANCES);
        tv.setName("LG");

        Product samsung = new Product();
        samsung.setName("samsung");

        productRepository.save(tv);
        productRepository.save(samsung);

        Assertions.assertEquals(2, productRepository.count());
        Assertions.assertEquals(1,
                productRepository.findAll(ProductSpecification.withNameLike("LG")).size());
        Assertions.assertEquals(0,
                productRepository.findAll(ProductSpecification.withNameLike("Phillips")).size());
    }

    @Test
    public void searchProductsByProductType(){
        Product applialcce = new Product();
        applialcce.setProductType(ProductType.APPLIANCES);

        Product food = new Product();
        food.setProductType(ProductType.FOOD);

        productRepository.save(applialcce);
        productRepository.save(food);

        Assertions.assertEquals(1, productRepository.findAll(ProductSpecification.ofType(ProductType.FOOD)).size());
        Assertions.assertEquals(1, productRepository.findAll(ProductSpecification.ofType(ProductType.APPLIANCES)).size());
        Assertions.assertEquals(0, productRepository.findAll(ProductSpecification.ofType(ProductType.BEVERAGES)).size());
    }

    @Test
    public void searchProductsByProductTypeAndName(){
        Product samsungPhone = new Product();
        samsungPhone.setName("samsung");
        samsungPhone.setProductType(ProductType.MOBILE_PHONES);

        Product samsungTv = new Product();
        samsungTv.setName("samsung");
        samsungTv.setProductType(ProductType.APPLIANCES);

        productRepository.save(samsungPhone);
        productRepository.save(samsungTv);

        Assertions.assertEquals(2,
                productRepository.findAll(ProductSpecification.withNameLike("samsung")).size());
        Assertions.assertEquals(1,
                productRepository.findAll(ProductSpecification.withNameLike("samsung")
                .and(ProductSpecification.ofType(ProductType.MOBILE_PHONES))).size());
    }

    @Test
    public void searchProductsByPriceRange(){
        Product phone1 = new Product();
        phone1.setPrice(150.0);

        Product phone2 = new Product();
        phone2.setPrice(170.0);

        Product phone3 = new Product();
        phone3.setPrice(300.0);

        productRepository.save(phone1);
        productRepository.save(phone2);
        productRepository.save(phone3);

        Assertions.assertEquals(2,
                productRepository.findAll(ProductSpecification.withPriceInRange(0.0, 200.0)).size());
        Assertions.assertEquals(1,
                productRepository.findAll(ProductSpecification.withPriceInRange(300.0, 500.0)).size());
    }

    @Test
    public void searchProductsByCategoryId() {
        Category applianceCategory = new Category();
        applianceCategory.setName("Appliance_category");

        Category phoneCategory = new Category();
        phoneCategory.setName("Phone_category");

        applianceCategory = categoryRepository.save(applianceCategory);
        phoneCategory = categoryRepository.save(phoneCategory);

        Product appliance1 = new Product();
        appliance1.setName("appliance_1");
        appliance1.setCategory(applianceCategory);

        productRepository.save(appliance1);

        Product appliance2 = new Product();
        appliance2.setName("appliance_2");
        appliance2.setCategory(phoneCategory);

        productRepository.save(appliance2);

        Product appliance3 = new Product();
        appliance3.setName("appliance_3");
        appliance3.setCategory(phoneCategory);

        productRepository.save(appliance3);

        Assertions.assertEquals(1, productRepository.findAll
                (ProductSpecification.withCategoryId(applianceCategory.getId())).size());

        Assertions.assertEquals(2, productRepository.findAll
                (ProductSpecification.withCategoryId(phoneCategory.getId())).size());
    }


}
