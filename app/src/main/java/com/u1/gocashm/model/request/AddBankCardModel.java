package com.u1.gocashm.model.request;

import java.io.Serializable;

public class AddBankCardModel implements Serializable {

    /**
     * card_number : 2234234234234234
     * expiry_year : 1234
     * expiry_month : 12
     * cvv : 123
     * user_id : 39
     * merchant_id : 1
     * updated_at : 2022-02-28 11:41:33
     * created_at : 2022-02-28 11:41:33
     * id : 1
     */

    private String card_number;
    private String expiry_year;
    private String expiry_month;
    private String cvv;
    private int user_id;
    private int merchant_id;
    private String updated_at;
    private String created_at;
    private int id;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
