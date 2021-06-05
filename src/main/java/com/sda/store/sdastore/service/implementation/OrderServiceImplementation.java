package com.sda.store.sdastore.service.implementation;

import com.sda.store.sdastore.model.Order;
import com.sda.store.sdastore.model.OrderLine;
import com.sda.store.sdastore.model.Product;
import com.sda.store.sdastore.model.User;
import com.sda.store.sdastore.repository.OrderRepository;
import com.sda.store.sdastore.repository.ProductRepository;
import com.sda.store.sdastore.repository.UserRepository;
import com.sda.store.sdastore.service.OrderService;
import com.sda.store.sdastore.service.ShoppingCartService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ShoppingCartService shoppingCartService;


    public OrderServiceImplementation(OrderRepository orderRepository,
                                      UserRepository userRepository,
                                      ProductRepository productRepository,
                                      ShoppingCartService shoppingCartService){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public Order createOrder(List<OrderLine> orderLineList) {
        Order order = new Order();
        UserDetails springUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(springUser.getUsername());
        order.setOrderLineList(orderLineList);
        order.setUser(user);
        order.setTotal(getTotal(orderLineList));

        //salvam order
        Order dbOrder = orderRepository.save(order);

        //modificam stockul
        for(OrderLine orderLine: orderLineList){
            Product product = orderLine.getProduct();
            product.setStock(product.getStock() - orderLine.getQuantity());
            productRepository.save(product);
        }

        //facem clear la shopping cart
        shoppingCartService.clearShoppingCart(user.getShoppingCart().getId());

        return dbOrder;
    }

    @Override
    public List<Order> findAllOrdersByUserId(Long id) {
        return orderRepository.findAllByUserId(id);
    }

    private Double getTotal(List<OrderLine> orderLineList){
         return orderLineList
                .stream()
                .mapToDouble(orderLine -> orderLine.getTotalForOrderLine())
                .sum();
    }
}
