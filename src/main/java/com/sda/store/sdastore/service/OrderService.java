package com.sda.store.sdastore.service;

import com.sda.store.sdastore.model.Order;
import com.sda.store.sdastore.model.OrderLine;
import com.sda.store.sdastore.model.PaymentDetails;

import java.util.List;

public interface OrderService {

    Order createOrder(List<OrderLine> orderLineList, PaymentDetails paymentDetails);
    List<Order> findAllOrdersByUserId(Long id);
}
