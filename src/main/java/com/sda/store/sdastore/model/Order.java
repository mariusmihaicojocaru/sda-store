package com.sda.store.sdastore.model;

import javax.persistence.*;
import java.util.List;

@Entity(name="user_orders") //order este un keyword in sql si se incurca cu el si da eroare ca nu stie ce sa faca
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> orderLineList;

    @Column(nullable = false)
    private Double total;

    //relatie cu payment details

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderLine> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<OrderLine> orderLineList) {
        this.orderLineList = orderLineList;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
