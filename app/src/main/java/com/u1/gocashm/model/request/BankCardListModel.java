package com.u1.gocashm.model.request;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;
import com.u1.gocashm.model.response.PaymentStatusModel;

import java.util.List;

public class BankCardListModel extends AbstractBaseRespBean<Status, List<BankCardListModel.bankcardlist>> {

    public static class bankcardlist {


        /**
         * id : 1
         * merchant_id : 1
         * user_id : 39
         * card_number : 2147483647
         * expiry_year : 1234
         * expiry_month : 12
         * cvv : 123
         * status : 1
         * created_at : 2022-02-28 11:41:33
         * updated_at : 2022-02-28 11:41:33
         */

        private int id;
        private int merchant_id;
        private int user_id;
        private int card_number;
        private String expiry_year;
        private String expiry_month;
        private String cvv;
        private int status;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCard_number() {
            return card_number;
        }

        public void setCard_number(int card_number) {
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        @Override
        public String toString() {
            return "bankcardlist{" +
                    "id=" + id +
                    ", merchant_id=" + merchant_id +
                    ", user_id=" + user_id +
                    ", card_number=" + card_number +
                    ", expiry_year='" + expiry_year + '\'' +
                    ", expiry_month='" + expiry_month + '\'' +
                    ", cvv='" + cvv + '\'' +
                    ", status=" + status +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    '}';
        }
    }
}
