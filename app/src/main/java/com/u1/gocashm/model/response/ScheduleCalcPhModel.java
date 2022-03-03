package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

/**
 * Created by jishudiannao on 2018/9/6.
 */

public class ScheduleCalcPhModel extends AbstractBaseRespBean<Status, ScheduleCalcPhModel.ScheduleCalc> {
    public static class ScheduleCalc {

        private String service_charge;
        private String interest;
        private String loan_amount;
        private String actual_mount;
        private String period_amount;
        private String loan_days;
        private String loanPmtDueDate;
        private String withdrawalServiceCharge;
        private List<RepaymentPlansBean> repaymentPlans;

        /**
         *  优惠金额
         */
        private String discount_amount;
        /**
         * 每日还款数
         */
        private String dalily_cost;

        public String getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public String getDalily_cost() {
            return dalily_cost;
        }

        public void setDalily_cost(String dalily_cost) {
            this.dalily_cost = dalily_cost;
        }

        public String getService_charge() {
            return service_charge;
        }

        public void setService_charge(String service_charge) {
            this.service_charge = service_charge;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getLoan_amount() {
            return loan_amount;
        }

        public void setLoan_amount(String loan_amount) {
            this.loan_amount = loan_amount;
        }

        public String getActual_mount() {
            return actual_mount;
        }

        public void setActual_mount(String actual_mount) {
            this.actual_mount = actual_mount;
        }

        public String getPeriod_amount() {
            return period_amount;
        }

        public void setPeriod_amount(String period_amount) {
            this.period_amount = period_amount;
        }

        public String getLoan_days() {
            return loan_days;
        }

        public void setLoan_days(String loan_days) {
            this.loan_days = loan_days;
        }

        public String getLoanPmtDueDate() {
            return loanPmtDueDate;
        }

        public void setLoanPmtDueDate(String loanPmtDueDate) {
            this.loanPmtDueDate = loanPmtDueDate;
        }

        public String getWithdrawalServiceCharge() {
            return withdrawalServiceCharge;
        }

        public void setWithdrawalServiceCharge(String withdrawalServiceCharge) {
            this.withdrawalServiceCharge = withdrawalServiceCharge;
        }

        public List<RepaymentPlansBean> getRepaymentPlans() {
            return repaymentPlans;
        }

        public void setRepaymentPlans(List<RepaymentPlansBean> repaymentPlans) {
            this.repaymentPlans = repaymentPlans;
        }

        public static class RepaymentPlansBean {
            /**
             * repayDate : 18-03-2021
             * repayAmount : 2027.80
             * installment_num : 1
             */

            private String repayDate;
            private String repayAmount;
            private int installment_num;

            public String getRepayDate() {
                return repayDate;
            }

            public void setRepayDate(String repayDate) {
                this.repayDate = repayDate;
            }

            public String getRepayAmount() {
                return repayAmount;
            }

            public void setRepayAmount(String repayAmount) {
                this.repayAmount = repayAmount;
            }

            public int getInstallment_num() {
                return installment_num;
            }

            public void setInstallment_num(int installment_num) {
                this.installment_num = installment_num;
            }
        }
    }
}
