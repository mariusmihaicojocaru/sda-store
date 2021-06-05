package com.sda.store.sdastore.repository;

import com.sda.store.sdastore.model.Category;
import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductSpecification implements Specification<Product> {

    private CategoryRepository categoryRepository;

    public ProductSpecification(){}

    public ProductSpecification(@Autowired CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public static Specification<Product> withNameLike(String productName){
        return(root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + productName + "%");
    }

    public static Specification<Product> ofType (ProductType productType){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("productType"), productType);
    }

    public static Specification<Product> withPriceInRange(Double lowerInterval, Double higherInterval){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), lowerInterval, higherInterval);
    }

    public static Specification<Product> withCategoryId(Long categoryId){
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }

    public static Specification<Product> withCategoriIdsIn(List<Long> categoryIds){
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return categoryJoin.get("id").in(categoryIds);
        };
    }

    public static Specification<Product> getSpecificationByParameterWithMultipleValues(String parameterName, List<Long> ids){
        switch(parameterName){
            case "categoryId": return withCategoriIdsIn(ids);
            default: return new ProductSpecification();
        }
    }

    public static Specification<Product> getSpecificationByParameter(String parameterName, String parameterValue){
        switch(parameterName){
            case "name" : return withNameLike(parameterValue);
            case "productType" : return ofType(ProductType.valueOf(parameterValue));
            case "categoryId" : return withCategoryId(Long.valueOf(parameterValue));
            // din frontend price=100:200
            //parameterName = price
            //parameterValue = 100:200
            // "100:200".split(":") rezulta ["100","200"}
            //"100:"
            case "price" :
                String price = parameterValue;
                Double lowPrice = 0.0;
                Double highPrice = Double.MAX_VALUE;
                if (price.contains(",")) {
                    List<String> prices = Arrays.asList(price.split(",")); // lowPrice:50 | highPrice:100
                    for (String iteration: prices) {
                        if (iteration.contains("lowPrice")) {
                            String lowPriceInStringFormat = iteration.replaceAll("lowPrice:", ""); // lowPrice:50  ===> 50;
                            lowPrice = Double.valueOf(lowPriceInStringFormat);
                        } else if (iteration.contains("highPrice")) {{
                            String highPriceInStringFormat = iteration.replaceAll("highPrice:", ""); // highPrice:100 ==> 100;
                            highPrice = Double.valueOf(highPriceInStringFormat);
                        }}
                    }
                }
                return withPriceInRange(lowPrice, highPrice);

            default: return  new ProductSpecification();
        }
    }

    @Override
    public Specification<Product> and(Specification<Product> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Product> or(Specification<Product> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
