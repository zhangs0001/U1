package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/10/17 0017 下午 3:11
 */
public class RepayInfoPhModel extends AbstractBaseRespBean<Status, RepayInfoPhModel.Reapy> {

    public static class Reapy {
        /**
         * 帐户
         */
        private String account;

        /**
         * 还款码
         */
        private String payNo;

        /**
         * 帐户名
         */
        private String accountName;

        /**
         * 开户行名称
         */
        private String bankName;

        /**
         * 开户行支行
         */
        private String branchName;

        /**
         * 开户行代码
         */
        private String bankCode;

        /**
         * 邮箱
         */
        private String email;
        /**
         * 描述
         */
        private String describe;

        /**
         * 描述
         */
        private String remark;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPayNo() {
            return payNo;
        }

        public void setPayNo(String payNo) {
            this.payNo = payNo;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
