package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

public class ApplyRespModel extends AbstractBaseRespBean<Status, ApplyRespModel.Apply> {

    public static class Apply{

        private String app_version;
        private String status;
        private int quality;
        private String app_client;
        private int merchant_id;
        private int app_id;
        private String order_no;
        private int user_id;
        private int principal;
        private int loan_days;
        private double daily_rate;
        private String created_at;
        private String updated_at;
        private double overdue_rate;
        private String auth_process;
        private Integer id;

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public String getApp_client() {
            return app_client;
        }

        public void setApp_client(String app_client) {
            this.app_client = app_client;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getApp_id() {
            return app_id;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getPrincipal() {
            return principal;
        }

        public void setPrincipal(int principal) {
            this.principal = principal;
        }

        public int getLoan_days() {
            return loan_days;
        }

        public void setLoan_days(int loan_days) {
            this.loan_days = loan_days;
        }

        public double getDaily_rate() {
            return daily_rate;
        }

        public void setDaily_rate(double daily_rate) {
            this.daily_rate = daily_rate;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public double getOverdue_rate() {
            return overdue_rate;
        }

        public void setOverdue_rate(double overdue_rate) {
            this.overdue_rate = overdue_rate;
        }

        public String getAuth_process() {
            return auth_process;
        }

        public void setAuth_process(String auth_process) {
            this.auth_process = auth_process;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
