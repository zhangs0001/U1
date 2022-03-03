package com.u1.gocashm.model.request;


public class UpdateOrderReqModel {
    public String token;
    public String order_id;
    public Integer loan_days;
    public String principal;
    public String can_contact_time;
    public String loan_reason;
    public String position;
    public String step_name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getLoan_days() {
        return loan_days;
    }

    public void setLoan_days(Integer loan_days) {
        this.loan_days = loan_days;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getCan_contact_time() {
        return can_contact_time;
    }

    public void setCan_contact_time(String can_contact_time) {
        this.can_contact_time = can_contact_time;
    }

    public String getLoan_reason() {
        return loan_reason;
    }

    public void setLoan_reason(String loan_reason) {
        this.loan_reason = loan_reason;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStep_name() {
        return step_name;
    }

    public void setStep_name(String step_name) {
        this.step_name = step_name;
    }
}
