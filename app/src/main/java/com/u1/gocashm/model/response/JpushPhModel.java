package com.u1.gocashm.model.response;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class JpushPhModel {


    /**
     * code : string
     * data : {}
     * message : string
     */

    private String code;
    private DataBean data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {

    }
}
