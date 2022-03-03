package com.u1.gocashm.model.request;

import java.util.ArrayList;
import java.util.List;
public class BehaviorRecordReqModel {
    private String token;
    private List<BehaviorRecord> data_json = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<BehaviorRecord> getData_json() {
        return data_json;
    }

    public void setData_json(List<BehaviorRecord> data_json) {
        this.data_json = data_json;
    }

    public List<BehaviorRecord> getRecords() {
        return data_json;
    }

    @Override
    public String toString() {
        return "BehaviorRecordReqModel{" +
                "token='" + token + '\'' +
                ", data_json=" + data_json +
                '}';
    }
}
