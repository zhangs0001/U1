package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

public class PaymentStatusModel extends AbstractBaseRespBean<Status, List<PaymentStatusModel.PaymentStatus>> {

    public static class PaymentStatus {
        /**
         * transaction_no : 62177ED835EDF20220224
         * repay_no : 998068677
         * created_at : 2022-02-24 14:49:28
         * status : PAID
         * business_amount : 27.99
         * amount : 27.99
         */

        private String transaction_no;
        private String repay_no;
        private String created_at;
        private String status;
        private String business_amount;
        private String amount;

        public String getTransaction_no() {
            return transaction_no;
        }

        public void setTransaction_no(String transaction_no) {
            this.transaction_no = transaction_no;
        }

        public String getRepay_no() {
            return repay_no;
        }

        public void setRepay_no(String repay_no) {
            this.repay_no = repay_no;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBusiness_amount() {
            return business_amount;
        }

        public void setBusiness_amount(String business_amount) {
            this.business_amount = business_amount;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

}
