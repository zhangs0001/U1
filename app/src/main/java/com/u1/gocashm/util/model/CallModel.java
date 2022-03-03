package com.u1.gocashm.util.model;

import java.io.Serializable;
import java.util.List;

public class CallModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String idcard;
    private String phone;
    private List<CallInfo> list;

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

    public List<CallInfo> getList() {
        return list;
    }

    public void setList(List<CallInfo> list) {
        this.list = list;
    }
}
