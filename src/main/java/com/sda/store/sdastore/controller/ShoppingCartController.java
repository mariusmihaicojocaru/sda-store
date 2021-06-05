package com.sda.store.sdastore.controller;

import com.sda.store.sdastore.controller.dto.shoppingCart.ShoppingCartOrderLineDto;
import com.sda.store.sdastore.controller.dto.shoppingCart.ProductShoppingCartResponseDto;
import com.sda.store.sdastore.controller.dto.shoppingCart.ShoppingCartResponseDto;
import com.sda.store.sdastore.exception.OutOfStockException;
import com.sda.store.sdastore.exception.ResourceNotFoundInDatabase;
import com.sda.store.sdastore.model.*;
import com.sda.store.sdastore.service.OrderService;
import com.sda.store.sdastore.service.ProductService;
import com.sda.store.sdastore.service.ShoppingCartService;
import com.sda.store.sdastore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  ProductService productService,
                                  UserService userService,
                                  OrderService orderService){
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @PutMapping(path = "/shopping-cart")
    public ShoppingCartResponseDto addProductToCart(@RequestBody Long productId){
        UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(loggedUser.getUsername());
        Product product = productService.findById(productId);
        ShoppingCart shoppingCart = shoppingCartService.addProductToCart(product, user.getShoppingCart());
        return mapShoppingCartToResponseDto(shoppingCart);
    }

    @GetMapping(path = "/shopping-cart")
    public ShoppingCartResponseDto getShoppingCartResponse(){
        UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(loggedUser.getUsername());
        return mapShoppingCartToResponseDto(user.getShoppingCart());
    }

    @PostMapping(path = "/order")
    public HttpStatus makeOrder(@RequestBody List<ShoppingCartOrderLineDto> shoppingCartOrderLineDtos) {
        List<OrderLine> orderLines =
                shoppingCartOrderLineDtos
                .stream()
                .map(shoppingCartOrderLineDto -> mapOrderLineDtoToOrderLine(shoppingCartOrderLineDto))
                .collect(Collectors.toList());

        Order dbOrder = orderService.createOrder(orderLines);

        if(dbOrder != null){
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;

    }

    // TODO de vazut cand facem componenta de shopping cart
    @DeleteMapping("shopping-cart/delete/{id}")
    public HttpStatus deleteProduct(@PathVariable("id") Long id){
//        UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userService.findByEmail(loggedUser.getUsername());
//        if(user.getShoppingCart().getProductList().contains(productService.findById(id))){
//            productService.deleteProduct(id);
//        }
        return HttpStatus.OK;
    }

    public OrderLine mapOrderLineDtoToOrderLine(ShoppingCartOrderLineDto shoppingCartOrderLineDto){

        Product product = productService.findById(shoppingCartOrderLineDto.getProductId());
        if(product == null) {
            throw new ResourceNotFoundInDatabase(String.format("Product with id %d was not found", shoppingCartOrderLineDto.getProductId()));
        }

        if((product.getStock() - shoppingCartOrderLineDto.getQuantity() < 0)){
            throw new OutOfStockException(String.format("Purchased quantity exceeds the number of items in stock.\n" +
                    "Number of items in stock: " + product.getStock() + "\n" +
                    "Number of items in cart: " + shoppingCartOrderLineDto.getQuantity()));
        }

        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setQuantity(shoppingCartOrderLineDto.getQuantity());
        return orderLine;
    }

    public ShoppingCartResponseDto mapShoppingCartToResponseDto(ShoppingCart shoppingCart){
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        List<ProductShoppingCartResponseDto> productShoppingCartResponseDtoList = new ArrayList<>();

        for(Product product : shoppingCart.getProductList()){
            ProductShoppingCartResponseDto dto = new ProductShoppingCartResponseDto();
            dto.setId(product.getId());
            dto.setProductName(product.getName());
            productShoppingCartResponseDtoList.add(dto);
        }

        shoppingCartResponseDto.setProductsInCart(productShoppingCartResponseDtoList);
        return shoppingCartResponseDto;
    }
}
