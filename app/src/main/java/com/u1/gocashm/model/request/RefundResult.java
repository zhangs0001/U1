package com.u1.gocashm.model.request;

/*
TODO com.u5.cash_cat.data 
TODO Time: 2022/2/12 11:26 
TODO Name: zhang
    * 入乡随俗 *
*/

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;


import java.util.List;

public class RefundResult extends AbstractBaseRespBean<Status, List<RefundResult.Refund>>{

    public static class Refund {

        /**
         * bankNameKey : Bank Name:
         * bankName : Kanbawza Bank
         * branchNameKey : Name:
         * branchName : Yan Naing Kyaw
         * accountNumerKey : Account Number:
         * accountNumber : 3173 0199 9107 73601
         */

        private String bankNameKey;
        private String bankName;
        private String branchNameKey;
        private String branchName;
        private String accountNumerKey;
        private String accountNumber;

        public String getBankNameKey() {
            return bankNameKey;
        }

        public void setBankNameKey(String bankNameKey) {
            this.bankNameKey = bankNameKey;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBranchNameKey() {
            return branchNameKey;
        }

        public void setBranchNameKey(String branchNameKey) {
            this.branchNameKey = branchNameKey;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getAccountNumerKey() {
            return accountNumerKey;
        }

        public void setAccountNumerKey(String accountNumerKey) {
            this.accountNumerKey = accountNumerKey;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

    }

}
