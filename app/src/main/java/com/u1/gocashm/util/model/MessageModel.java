package com.u1.gocashm.util.model;

import java.io.Serializable;
import java.util.List;

public class MessageModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String idcard;
    private String phone;
    private List<MessageInfo> list;

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<MessageInfo> getList() {
        return list;
    }

    public void setList(List<MessageInfo> list) {
        this.list = list;
    }
}
