package com.sda.store.sdastore.controller.dto;

import com.sda.store.sdastore.controller.dto.shoppingCart.ShoppingCartOrderLineDto;
import com.sda.store.sdastore.model.OrderDetails;

import java.util.List;

public class OrderRequestDto {

    private List<ShoppingCartOrderLineDto> shoppingCartOrderLineDtoList;

    private PaymentDetailsDto paymentDetailsDto;

    private OrderDetailsDto orderDetailsDto;

    public List<ShoppingCartOrderLineDto> getShoppingCartOrderLineDtoList() {
        return shoppingCartOrderLineDtoList;
    }

    public void setShoppingCartOrderLineDtoList(List<ShoppingCartOrderLineDto> shoppingCartOrderLineDtoList) {
        this.shoppingCartOrderLineDtoList = shoppingCartOrderLineDtoList;
    }

    public PaymentDetailsDto getPaymentDetailsDto() {
        return paymentDetailsDto;
    }

    public void setPaymentDetailsDto(PaymentDetailsDto paymentDetailsDto) {
        this.paymentDetailsDto = paymentDetailsDto;
    }

    public OrderDetailsDto getOrderDetailsDto() {
        return orderDetailsDto;
    }

    public void setOrderDetailsDto(OrderDetailsDto orderDetailsDto) {
        this.orderDetailsDto = orderDetailsDto;
    }
}
