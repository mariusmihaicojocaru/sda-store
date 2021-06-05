package com.sda.store.sdastore.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class PaymentDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String cardHolder;

    @Column(length = 16)
    private String cardNumber;

    private Date expirationDate;

    // nu am voie sa le salvez
//    private String securityCode;

    @ManyToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private CardProvider cardProvider;

    @OneToOne
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CardProvider getCardProvider() {
        return cardProvider;
    }

    public void setCardProvider(CardProvider cardProvider) {
        this.cardProvider = cardProvider;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
