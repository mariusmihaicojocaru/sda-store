package com.sda.store.sdastore.controller;

import com.sda.store.sdastore.controller.dto.product.ProductRequestDto;
import com.sda.store.sdastore.controller.dto.product.ProductResponseDto;
import com.sda.store.sdastore.model.Category;
import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.ProductType;
import com.sda.store.sdastore.service.CategoryService;
import com.sda.store.sdastore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService){
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping(value = "/products")
    public ProductRequestDto create(@RequestBody ProductRequestDto productRequestDto){
        Product product = productService.createProduct(mapProductDtoToProduct(productRequestDto));
        return mapProductToProductResponseDto(product);
    }

    @GetMapping(value = "/products/{id}")
    public ProductRequestDto findById(@PathVariable("id") Long id){
        return mapProductToProductResponseDto(productService.findById(id));
    }

    @PutMapping(value = "/products/{id}")
    public ProductRequestDto update(@PathVariable("id") Long id, @RequestBody ProductRequestDto productRequestDto){
        Product updatedProduct = updateProductDtoToProduct(productService.findById(id), productRequestDto);
        return mapProductToProductResponseDto(productService.createProduct(updatedProduct));
    }

    @GetMapping(value = "/products")
    public Page<ProductRequestDto> searchProducts(@RequestParam Map<String, String> params){
        Page<Product> productList = productService.searchProducts(params);

        return new PageImpl<ProductRequestDto>(
                productList.getContent().stream()
                .map(this::mapProductToProductResponseDto)
                .collect(Collectors.toList()),
                productList.getPageable(),
                productList.getTotalElements()
        );

    }

    @GetMapping(value = "/product-types")
    public List<ProductType> getProductTypes(){
        return Arrays.asList(ProductType.values());
    }

    @DeleteMapping(value = "/products/delete/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return HttpStatus.OK;
    }



    private ProductResponseDto mapProductToProductResponseDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setThumbnail(product.getThumbnail());
        productResponseDto.setCategoryId(product.getCategory().getId());
        productResponseDto.setProductType(product.getProductType());
        productResponseDto.setUser(product.getUser());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setId(product.getId());
        productResponseDto.setCategoryName(product.getCategory().getName());
        productResponseDto.setStock(product.getStock());

        return productResponseDto;
    }



    private Product mapProductDtoToProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setThumbnail(productRequestDto.getThumbnail());

        Category category = categoryService.findById(productRequestDto.getCategoryId());
        product.setCategory(category);

        product.setProductType(productRequestDto.getProductType());
        product.setUser(productRequestDto.getUser());
        product.setPrice(productRequestDto.getPrice());
        product.setStock(productRequestDto.getStock());

        return product;
    }

    private Product updateProductDtoToProduct(Product product, ProductRequestDto productRequestDto) {
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setThumbnail(productRequestDto.getThumbnail());

        Category category = categoryService.findById(productRequestDto.getCategoryId());
        product.setCategory(category);

        product.setProductType(productRequestDto.getProductType());
        product.setUser(productRequestDto.getUser());
        product.setStock(productRequestDto.getStock());

        return product;
    }
}
