package com.u1.gocashm.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * 客户信息
 * Created by Brain on 2018/9/20.
 */

public class CustomerDtoBeanPh implements Serializable {
    private String applyId;
    private int customerId;
    private int userId;
    private String phone;
    private String firstName;
    private String middleName;
    private String lastName;
    private String name;
    private String approveNo;
    private String deviceId;
    private String gps;
    private String gpsCity;
    private String productCode;
    private String loanCode;
    private Integer loanTerms;
    private String source;
    private String channel;
    private String validateCode;
    private String isValidateCode;
    private String applyAmount;
    private String authName;
    private List<ImagePhModel> images;
    private String idcard;
    private String primaryCardNo;
    private String primaryCardType;
    private String primaryCardTypeName;
    private String sex;
    private String birth;
    private String maritalStatus;
    private String monthlyIncome;
    private String email;
    private String homeAddrProvince;
    private String homeAddrProvinceCode;
    private String homeAddrCity;
    private String homeAddrCityCode;
    private String homeAddrCounty;
    private String homeAddrCountyCode;
    private String homeAddrDetail;
    private String howLongStaying;
    private String company;
    private String industry;
    private String industryCode;
    private String jobType;
    private String jobTypeCode;
    private String companyPhone;
    private String companyAddrProvince;
    private String companyAddrProvinceCode;
    private String companyAddrCity;
    private String companyAddrCityCode;
    private String companyAddrCounty;
    private String companyAddrCountyCode;
    private String companyAddrDetail;
    private String contactName;
    private String contactRelation;
    private String contactRelationCode;
    private String contactPhone;
    private String otherContactName;
    private String otherContactRelation;
    private String otherContactRelationCode;
    private String otherContactPhone;
    private String bankName;
    private String bankCode;
    private String bankCity;
    private String bankCityCode;
    private String bankCardNo;
    private String bankDeposit;
    private String bankDepositName;
    private String isAutoRepayment;
    private String isFrequentlyUsedPhone;
    private String idcardAddress;
    private String mobileType;
    private String mobileModel;
    private String idcardDate;
    private String facebookAuth;
    private String zaloAuth;
    private String incumbency;
    private String incumbencyName;
    private int liabilities;
    private int monthlyRepayment;
    private String socialStatus;
    private String socialStatusName;
    private String customerGroup;
    private String loanPurpose;
    private String loanPurposeName;
    private String incomeFrequency;
    private String nextIncomeDate;
    private String jobPosition;
    private String maritalStatusName;
    private String howLongStayingName;
    private String incomeFrequencyName;
    private String educationCode;
    private String education;

    public String getMaritalStatusName() {
        return maritalStatusName;
    }

    public String getHowLongStayingName() {
        return howLongStayingName;
    }

    public String getIncomeFrequencyName() {
        return incomeFrequencyName;
    }

    public String getBankDepositName() {
        return bankDepositName;
    }

    public String getLoanPurposeName() {
        return loanPurposeName;
    }

    private List<ThirdInfosBean> thirdInfos;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getGpsCity() {
        return gpsCity;
    }

    public void setGpsCity(String gpsCity) {
        this.gpsCity = gpsCity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public Integer getLoanTerms() {
        return loanTerms;
    }

    public void setLoanTerms(Integer loanTerms) {
        this.loanTerms = loanTerms;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getIsValidateCode() {
        return isValidateCode;
    }

    public void setIsValidateCode(String isValidateCode) {
        this.isValidateCode = isValidateCode;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public List<ImagePhModel> getImages() {
        return images;
    }

    public void setImages(List<ImagePhModel> images) {
        this.images = images;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPrimaryCardNo() {
        return primaryCardNo;
    }

    public void setPrimaryCardNo(String primaryCardNo) {
        this.primaryCardNo = primaryCardNo;
    }

    public String getPrimaryCardType() {
        return primaryCardType;
    }

    public void setPrimaryCardType(String primaryCardType) {
        this.primaryCardType = primaryCardType;
    }

    public String getPrimaryCardTypeName() {
        return primaryCardTypeName;
    }

    public void setPrimaryCardTypeName(String primaryCardTypeName) {
        this.primaryCardTypeName = primaryCardTypeName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeAddrProvince() {
        return homeAddrProvince;
    }

    public void setHomeAddrProvince(String homeAddrProvince) {
        this.homeAddrProvince = homeAddrProvince;
    }

    public String getHomeAddrProvinceCode() {
        return homeAddrProvinceCode;
    }

    public void setHomeAddrProvinceCode(String homeAddrProvinceCode) {
        this.homeAddrProvinceCode = homeAddrProvinceCode;
    }

    public String getHomeAddrCity() {
        return homeAddrCity;
    }

    public void setHomeAddrCity(String homeAddrCity) {
        this.homeAddrCity = homeAddrCity;
    }

    public String getHomeAddrCityCode() {
        return homeAddrCityCode;
    }

    public void setHomeAddrCityCode(String homeAddrCityCode) {
        this.homeAddrCityCode = homeAddrCityCode;
    }

    public String getHomeAddrCounty() {
        return homeAddrCounty;
    }

    public void setHomeAddrCounty(String homeAddrCounty) {
        this.homeAddrCounty = homeAddrCounty;
    }

    public String getHomeAddrCountyCode() {
        return homeAddrCountyCode;
    }

    public void setHomeAddrCountyCode(String homeAddrCountyCode) {
        this.homeAddrCountyCode = homeAddrCountyCode;
    }

    public String getHomeAddrDetail() {
        return homeAddrDetail;
    }

    public void setHomeAddrDetail(String homeAddrDetail) {
        this.homeAddrDetail = homeAddrDetail;
    }

    public String getHowLongStaying() {
        return howLongStaying;
    }

    public void setHowLongStaying(String howLongStaying) {
        this.howLongStaying = howLongStaying;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobTypeCode() {
        return jobTypeCode;
    }

    public void setJobTypeCode(String jobTypeCode) {
        this.jobTypeCode = jobTypeCode;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyAddrProvince() {
        return companyAddrProvince;
    }

    public void setCompanyAddrProvince(String companyAddrProvince) {
        this.companyAddrProvince = companyAddrProvince;
    }

    public String getCompanyAddrProvinceCode() {
        return companyAddrProvinceCode;
    }

    public void setCompanyAddrProvinceCode(String companyAddrProvinceCode) {
        this.companyAddrProvinceCode = companyAddrProvinceCode;
    }

    public String getCompanyAddrCity() {
        return companyAddrCity;
    }

    public void setCompanyAddrCity(String companyAddrCity) {
        this.companyAddrCity = companyAddrCity;
    }

    public String getCompanyAddrCityCode() {
        return companyAddrCityCode;
    }

    public void setCompanyAddrCityCode(String companyAddrCityCode) {
        this.companyAddrCityCode = companyAddrCityCode;
    }

    public String getCompanyAddrCounty() {
        return companyAddrCounty;
    }

    public void setCompanyAddrCounty(String companyAddrCounty) {
        this.companyAddrCounty = companyAddrCounty;
    }

    public String getCompanyAddrCountyCode() {
        return companyAddrCountyCode;
    }

    public void setCompanyAddrCountyCode(String companyAddrCountyCode) {
        this.companyAddrCountyCode = companyAddrCountyCode;
    }

    public String getCompanyAddrDetail() {
        return companyAddrDetail;
    }

    public void setCompanyAddrDetail(String companyAddrDetail) {
        this.companyAddrDetail = companyAddrDetail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactRelation() {
        return contactRelation;
    }

    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    public String getContactRelationCode() {
        return contactRelationCode;
    }

    public void setContactRelationCode(String contactRelationCode) {
        this.contactRelationCode = contactRelationCode;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getOtherContactName() {
        return otherContactName;
    }

    public void setOtherContactName(String otherContactName) {
        this.otherContactName = otherContactName;
    }

    public String getOtherContactRelation() {
        return otherContactRelation;
    }

    public void setOtherContactRelation(String otherContactRelation) {
        this.otherContactRelation = otherContactRelation;
    }

    public String getOtherContactRelationCode() {
        return otherContactRelationCode;
    }

    public void setOtherContactRelationCode(String otherContactRelationCode) {
        this.otherContactRelationCode = otherContactRelationCode;
    }

    public String getOtherContactPhone() {
        return otherContactPhone;
    }

    public void setOtherContactPhone(String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankCityCode() {
        return bankCityCode;
    }

    public void setBankCityCode(String bankCityCode) {
        this.bankCityCode = bankCityCode;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getIsAutoRepayment() {
        return isAutoRepayment;
    }

    public void setIsAutoRepayment(String isAutoRepayment) {
        this.isAutoRepayment = isAutoRepayment;
    }

    public String getIsFrequentlyUsedPhone() {
        return isFrequentlyUsedPhone;
    }

    public void setIsFrequentlyUsedPhone(String isFrequentlyUsedPhone) {
        this.isFrequentlyUsedPhone = isFrequentlyUsedPhone;
    }

    public String getIdcardAddress() {
        return idcardAddress;
    }

    public void setIdcardAddress(String idcardAddress) {
        this.idcardAddress = idcardAddress;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public String getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    public String getIdcardDate() {
        return idcardDate;
    }

    public void setIdcardDate(String idcardDate) {
        this.idcardDate = idcardDate;
    }

    public String getFacebookAuth() {
        return facebookAuth;
    }

    public void setFacebookAuth(String facebookAuth) {
        this.facebookAuth = facebookAuth;
    }

    public String getZaloAuth() {
        return zaloAuth;
    }

    public void setZaloAuth(String zaloAuth) {
        this.zaloAuth = zaloAuth;
    }

    public String getIncumbency() {
        return incumbency;
    }

    public void setIncumbency(String incumbency) {
        this.incumbency = incumbency;
    }

    public int getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(int liabilities) {
        this.liabilities = liabilities;
    }

    public int getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public void setMonthlyRepayment(int monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public String getSocialStatus() {
        return socialStatus;
    }

    public void setSocialStatus(String socialStatus) {
        this.socialStatus = socialStatus;
    }

    public String getSocialStatusName() {
        return socialStatusName;
    }

    public void setSocialStatusName(String socialStatusName) {
        this.socialStatusName = socialStatusName;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getIncomeFrequency() {
        return incomeFrequency;
    }

    public void setIncomeFrequency(String incomeFrequency) {
        this.incomeFrequency = incomeFrequency;
    }

    public String getNextIncomeDate() {
        return nextIncomeDate;
    }

    public void setNextIncomeDate(String nextIncomeDate) {
        this.nextIncomeDate = nextIncomeDate;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public List<ThirdInfosBean> getThirdInfos() {
        return thirdInfos;
    }

    public void setThirdInfos(List<ThirdInfosBean> thirdInfos) {
        this.thirdInfos = thirdInfos;
    }

    public String getIncumbencyName() {
        return incumbencyName;
    }

    public void setIncumbencyName(String incumbencyName) {
        this.incumbencyName = incumbencyName;
    }

    public String getEducationCode() {
        return educationCode;
    }

    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public static class ThirdInfosBean {
        /**
         * thirdName : Facebook Messanger
         * isAuth : 否
         * authPhone : 0981234567
         * thirdCode : Facebook Messanger
         * authType : null
         */

        private String thirdName;
        private String isAuth;
        private String authPhone;
        private String thirdCode;
        private Object authType;

        public String getThirdName() {
            return thirdName;
        }

        public void setThirdName(String thirdName) {
            this.thirdName = thirdName;
        }

        public String getIsAuth() {
            return isAuth;
        }

        public void setIsAuth(String isAuth) {
            this.isAuth = isAuth;
        }

        public String getAuthPhone() {
            return authPhone;
        }

        public void setAuthPhone(String authPhone) {
            this.authPhone = authPhone;
        }

        public String getThirdCode() {
            return thirdCode;
        }

        public void setThirdCode(String thirdCode) {
            this.thirdCode = thirdCode;
        }

        public Object getAuthType() {
            return authType;
        }

        public void setAuthType(Object authType) {
            this.authType = authType;
        }
    }
}
