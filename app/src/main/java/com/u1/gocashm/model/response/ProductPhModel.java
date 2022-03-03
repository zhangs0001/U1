package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jishudiannao on 2018/9/6.
 */
public class ProductPhModel extends AbstractBaseRespBean<Status, ProductPhModel.Product> {

    public static class Product implements Serializable {
        public String loanCode;
        public String productCode;
        public int minLoanTerms;
        public int maxLoanTerms;
        public String minAmount;
        public String maxAmount;
        private String amountStep;
        private int loanStep;
        private int maxViewTerms;
        private String maxViewAmount;
        private List<LoanTermModel> loanTerms;
        private String level;
        private int quality;
        private String reference_no;
        private String fullname;
        private String loan_amount;
        private String loan_days;
        private String interest_rate;
        private int interest;
        private int service_charge;
        private int period_amount;
        private String apr;
        private String serviceFeeRate;
        private String withdrawal_service_charge;
        private String email;
        private List<LoanTermModel> fullLoanTerms;
        private String dg_pay_lifetime_id;


        public String getAmountStep() {
            return amountStep;
        }

        public int getLoanStep() {
            return loanStep;
        }

        public int getMaxViewTerms() {
            return maxViewTerms;
        }

        public String getMaxViewAmount() {
            return maxViewAmount;
        }

        public List<LoanTermModel> getLoanTerms() {
            return loanTerms;
        }

        public void setLoanTerms(List<LoanTermModel> loanTerms) {
            this.loanTerms = loanTerms;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public String getReference_no() {
            return reference_no;
        }

        public void setReference_no(String reference_no) {
            this.reference_no = reference_no;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getLoan_amount() {
            return loan_amount;
        }

        public void setLoan_amount(String loan_amount) {
            this.loan_amount = loan_amount;
        }

        public String getLoan_days() {
            return loan_days;
        }

        public void setLoan_days(String loan_days) {
            this.loan_days = loan_days;
        }

        public String getInterest_rate() {
            return interest_rate;
        }

        public void setInterest_rate(String interest_rate) {
            this.interest_rate = interest_rate;
        }

        public int getInterest() {
            return interest;
        }

        public void setInterest(int interest) {
            this.interest = interest;
        }

        public int getService_charge() {
            return service_charge;
        }

        public void setService_charge(int service_charge) {
            this.service_charge = service_charge;
        }

        public int getPeriod_amount() {
            return period_amount;
        }

        public void setPeriod_amount(int period_amount) {
            this.period_amount = period_amount;
        }

        public String getApr() {
            return apr;
        }

        public void setApr(String apr) {
            this.apr = apr;
        }

        public String getServiceFeeRate() {
            return serviceFeeRate;
        }

        public void setServiceFeeRate(String serviceFeeRate) {
            this.serviceFeeRate = serviceFeeRate;
        }

        public String getWithdrawal_service_charge() {
            return withdrawal_service_charge;
        }

        public void setWithdrawal_service_charge(String withdrawal_service_charge) {
            this.withdrawal_service_charge = withdrawal_service_charge;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<LoanTermModel> getFullLoanTerms() {
            return fullLoanTerms;
        }

        public void setFullLoanTerms(List<LoanTermModel> fullLoanTerms) {
            this.fullLoanTerms = fullLoanTerms;
        }

        public String getDg_pay_lifetime_id() {
            return dg_pay_lifetime_id;
        }

        public void setDg_pay_lifetime_id(String dg_pay_lifetime_id) {
            this.dg_pay_lifetime_id = dg_pay_lifetime_id;
        }
    }
}
