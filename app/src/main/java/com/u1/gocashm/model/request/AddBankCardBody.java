package com.u1.gocashm.model.request;

import java.io.Serializable;

public class AddBankCardBody implements Serializable {
    private String token;
    private String card_number;
    private String expiry_year;
    private String expiry_month;
    private String cvv;
    private String holder_name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(String expiry_year) {
        this.expiry_year = expiry_year;
    }

    public String getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(String expiry_month) {
        this.expiry_month = expiry_month;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    @Override
    public String toString() {
        return "AddBankCardBody{" +
                "token='" + token + '\'' +
                ", card_number='" + card_number + '\'' +
                ", expiry_year='" + expiry_year + '\'' +
                ", expiry_month='" + expiry_month + '\'' +
                ", cvv='" + cvv + '\'' +
                ", holder_name='" + holder_name + '\'' +
                '}';
    }
}
