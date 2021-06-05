package com.sda.store.sdastore.controller.dto;

import com.sda.store.sdastore.model.CardProvider;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class PaymentDetailsDto {

    private String cardHolder;

    private String cardNumber;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date expirationDate;

    private CardProvider cardProvider;

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

    public CardProvider getCardProvider() {
        return cardProvider;
    }

    public void setCardProvider(CardProvider cardProvider) {
        this.cardProvider = cardProvider;
    }
}
