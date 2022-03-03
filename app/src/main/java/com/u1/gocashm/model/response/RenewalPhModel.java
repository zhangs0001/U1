package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

public class RenewalPhModel extends AbstractBaseRespBean<Status, RenewalPhModel.Renewal> {
    public static class Renewal {
        private long order_id;
        private int renewal_days;
        private long repayment_plan_id;
        private long uid;
        private String valid_period;
        private int merchant_id;
        private int status;
        private String updated_at;
        private String created_at;
        private int id;


        public long getOrder_id() {
            return order_id;
        }

        public void setOrder_id(long order_id) {
            this.order_id = order_id;
        }

        public int getRenewal_days() {
            return renewal_days;
        }

        public void setRenewal_days(int renewal_days) {
            this.renewal_days = renewal_days;
        }

        public long getRepayment_plan_id() {
            return repayment_plan_id;
        }

        public void setRepayment_plan_id(long repayment_plan_id) {
            this.repayment_plan_id = repayment_plan_id;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getValid_period() {
            return valid_period;
        }

        public void setValid_period(String valid_period) {
            this.valid_period = valid_period;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
}
