package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

/**
 * Created by kay_o on 2018/9/20.
 */

public class UserInfoDetailModel extends AbstractBaseRespBean<Status, UserInfoDetailModel.UserInfoDetail> {

    public static class UserInfoDetail {

        private String name;
        private String phone;
        private String sex;
        private String customerId;
        private String userId;
        private String maritalStatus;
        private String monthlyIncome;
        private String company;
        private String homeAddr;
        private String idcard;
        private String signFlag;
        private ProductPhRange productPhRange;
        private long applyId;
        private int loanCount;
        private int inLoanCount;
        private String continuedLoan;
        private String companyName;
        private CustomerDtoBeanPh customerDto;
        private List<ImagePhModel> images;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(String monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getHomeAddr() {
            return homeAddr;
        }

        public void setHomeAddr(String homeAddr) {
            this.homeAddr = homeAddr;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getSignFlag() {
            return signFlag;
        }

        public void setSignFlag(String signFlag) {
            this.signFlag = signFlag;
        }

        public ProductPhRange getProductPhRange() {
            return productPhRange;
        }

        public void setProductPhRange(ProductPhRange productPhRange) {
            this.productPhRange = productPhRange;
        }

        public long getApplyId() {
            return applyId;
        }

        public void setApplyId(long applyId) {
            this.applyId = applyId;
        }

        public int getLoanCount() {
            return loanCount;
        }

        public void setLoanCount(int loanCount) {
            this.loanCount = loanCount;
        }

        public int getInLoanCount() {
            return inLoanCount;
        }

        public void setInLoanCount(int inLoanCount) {
            this.inLoanCount = inLoanCount;
        }

        public String getContinuedLoan() {
            return continuedLoan;
        }

        public void setContinuedLoan(String continuedLoan) {
            this.continuedLoan = continuedLoan;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public CustomerDtoBeanPh getCustomerDto() {
            return customerDto;
        }

        public void setCustomerDto(CustomerDtoBeanPh customerDto) {
            this.customerDto = customerDto;
        }

        public List<ImagePhModel> getImages() {
            return images;
        }

        public void setImages(List<ImagePhModel> images) {
            this.images = images;
        }
    }

}
