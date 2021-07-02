package com.sda.store.sdastore.service.implementation;

import com.sda.store.sdastore.exception.ResourceNotFoundInDatabase;
import com.sda.store.sdastore.model.Category;
import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.ProductType;
import com.sda.store.sdastore.repository.CategoryRepository;
import com.sda.store.sdastore.repository.ProductRepository;
import com.sda.store.sdastore.repository.ProductSpecification;
import com.sda.store.sdastore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product) {
      return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> optionalProduct =  productRepository.findById(id);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            throw new ResourceNotFoundInDatabase(String.format("Product with id %d was not found", id));
        }
    }

    @Override
    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setThumbnail(product.getThumbnail());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setProductType(product.getProductType());
        existingProduct.setUser(product.getUser());

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    //
    //{"name":"some-name"},
    //{"productType":"some-product-type"}
    //

    @Override
    public Page<Product> searchProducts(Map<String, String> params) {
        Specification<Product> specification = new ProductSpecification();

        if(params.get("page") == null || params.get("pageSize") == null) {
            throw new RuntimeException("Page and Page size must be valued.");
        }

        Integer page = Integer.valueOf(params.get("page"));
        Integer pageSize = Integer.valueOf(params.get("pageSize"));

        for(String parameterName : params.keySet()){
            if(parameterName.equals("categoryId")){
                List<Long> categoryIds = getCategoryIds(Long.valueOf(params.get(parameterName)));
                specification =
                        specification
                                .and(ProductSpecification.getSpecificationByParameterWithMultipleValues(parameterName, categoryIds));
            } else {
                specification =
                        specification
                                .and(ProductSpecification.getSpecificationByParameter(parameterName, params.get(parameterName)));
            }
        }

        return productRepository.findAll(specification, PageRequest.of(page, pageSize));
    }

    private List<Long> getCategoryIds(Long parentCategoryId){
        Category category = categoryRepository.findById(parentCategoryId).get();
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(category.getId());

        if(category.getSubCategories() != null){
            List<Category> subcategories = category.getSubCategories();
            subcategories.forEach(subcategory -> categoryIds.addAll(getCategoryIds(subcategory.getId())));
        }

        return categoryIds;
    }

    @Override
    public List<ProductType> getProductTypes() {
        List<ProductType> productTypes = Arrays.asList(ProductType.values().clone());
        return productTypes;
    }
}
