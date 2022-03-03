package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RenewaInfoPhModel extends AbstractBaseRespBean<Status, RenewaInfoPhModel.RenewaInfo> {


    public static class RenewaInfo implements Serializable {
        private int id;
        private String order_no;
        private String principal; //loan amount
        private RenewalPreInfo renewalPreInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public RenewalPreInfo getRenewalPreInfo() {
            return renewalPreInfo;
        }

        public void setRenewalPreInfo(RenewalPreInfo renewalPreInfo) {
            this.renewalPreInfo = renewalPreInfo;
        }

        public static class RenewalPreInfo{

            private String min_repay_amount; //minimun repayment for extension
            private String appointment_paid_time;//end time of extensio
            private String valid_period;//starting time of extension
            private String renewal_charge;
            private String overdue_fee;
            private double extend_appointment_paid_amount;//total repayment amount
            private String interest_fee;//interest
            private List<Integer> renewal_days = new ArrayList<>();
            private int issue;
            private int status;

            public String getMin_repay_amount() {
                return min_repay_amount;
            }

            public void setMin_repay_amount(String min_repay_amount) {
                this.min_repay_amount = min_repay_amount;
            }

            public String getAppointment_paid_time() {
                return appointment_paid_time;
            }

            public void setAppointment_paid_time(String appointment_paid_time) {
                this.appointment_paid_time = appointment_paid_time;
            }

            public String getValid_period() {
                return valid_period;
            }

            public void setValid_period(String valid_period) {
                this.valid_period = valid_period;
            }

            public String getRenewal_charge() {
                return renewal_charge;
            }

            public void setRenewal_charge(String renewal_charge) {
                this.renewal_charge = renewal_charge;
            }

            public String getOverdue_fee() {
                return overdue_fee;
            }

            public void setOverdue_fee(String overdue_fee) {
                this.overdue_fee = overdue_fee;
            }

            public double getExtend_appointment_paid_amount() {
                return extend_appointment_paid_amount;
            }

            public void setExtend_appointment_paid_amount(double extend_appointment_paid_amount) {
                this.extend_appointment_paid_amount = extend_appointment_paid_amount;
            }

            public String getInterest_fee() {
                return interest_fee;
            }

            public void setInterest_fee(String interest_fee) {
                this.interest_fee = interest_fee;
            }

            public List<Integer> getRenewal_days() {
                return renewal_days;
            }

            public void setRenewal_days(List<Integer> renewal_days) {
                this.renewal_days = renewal_days;
            }

            public int getIssue() {
                return issue;
            }

            public void setIssue(int issue) {
                this.issue = issue;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }


    }
}
