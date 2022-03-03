package com.u1.gocashm.util.model;

import java.io.Serializable;

public class ContactModel implements Serializable {
    private String data;
    private String token;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
