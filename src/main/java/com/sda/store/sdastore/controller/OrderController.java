package com.sda.store.sdastore.controller;

import com.sda.store.sdastore.controller.dto.order.OrderLineDto;
import com.sda.store.sdastore.controller.dto.order.OrderResponseDto;
import com.sda.store.sdastore.controller.dto.user.AddressDto;
import com.sda.store.sdastore.model.Address;
import com.sda.store.sdastore.model.Order;
import com.sda.store.sdastore.model.OrderLine;
import com.sda.store.sdastore.model.User;
import com.sda.store.sdastore.service.OrderService;
import com.sda.store.sdastore.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    private UserService userService;
    private OrderService orderService;

    public OrderController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping(path = "/orders")
    public List<OrderResponseDto> getOrders(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userService.findByEmail(userDetails.getUsername());
        List<Order> userOrders = orderService.findAllOrdersByUserId(loggedUser.getId());
        List<OrderResponseDto> orderResponseDtos = userOrders.stream()
                .map(order -> mapOrderToOrderResponseDto(order))
                .collect(Collectors.toList());
        return orderResponseDtos;

    }

    public OrderResponseDto mapOrderToOrderResponseDto(Order order){
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());

        List<OrderLineDto> orderLineDtos = order.getOrderLineList().stream().map(orderLine -> mapOrderLineToOrderLineDto(orderLine))
                .collect(Collectors.toList());

        dto.setOrderLines(orderLineDtos);

        Address address = order.getUser().getAddress();
        dto.setAddressDto(AddressDto.mapAddressToAddressDto(address));

        dto.setTotalPrice(order.getTotal());

        return dto;
    }

    public OrderLineDto mapOrderLineToOrderLineDto(OrderLine orderLine){
        OrderLineDto orderLineDto = new OrderLineDto();
        orderLineDto.setProductName(orderLine.getProduct().getName());
        orderLineDto.setPrice(orderLine.getProduct().getPrice());
        orderLineDto.setQuantity(orderLine.getQuantity());
        return orderLineDto;
    }
}
