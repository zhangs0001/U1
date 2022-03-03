package com.u1.gocashm.model.request;

import com.u1.gocashm.util.model.ContactInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author hewei
 * @date 2018/9/6 0006 下午 2:20
 */
public class ApplyReqPhModel implements Serializable {

    private Integer applyAmount;
    private long applyId;
    private long preApplyId;
    private String approveNo;
    private String authName;
    private String channel;
    private String company;
    private String work_phone;
    private String contactName;
    private String contactPhone;
    private String contactRelationCode;
    private int customerId;
    private String deviceId;
    private String firstname;
    private String working_time_type;
    private String industry;
    private String isValidateCode;
    private String jobTypeCode;
    private String lastname;
    private String loanCode;
    private String loan_reason;
    private Integer loanTerms;
    private String middlename;
    private String salary;
    private String name;
    private String telephone;
    private String productCode;
    private String employment_type;
    private String source;
    private int userId;
    private String captcha;
    private List<Integer> images;
    private String birthday;
    private String email;
    private String card_num;
    private String marital_status;
    private String card_type_code;
    private String gender;
    private List<ThirdInfosBean> thirdInfos;
    private String homeAddrCityCode;
    private String homeAddrDetail;
    private String homeAddrProvinceCode;
    private String homeAddrCountyCode;
    private String howLongStaying;
    private String bank_no;
    private String bankCity;
    private String bankCityCode;
    private String bank_code;
    private String bankDeposit;
    private String bankName;
    private String password_check;
    private String password;
    private String incomeFrequency;
    private String profession;
    private String nextIncomeDate;
    private String jobPosition;
    private String gps;
    private String gpsCity;
    private String contactRelation;
    private String mobileType;
    private String mobileModel;
    //    @ApiModelProperty(value = "online Pay 是否线上付款Y,N")
    private String onlinePay;
    //    @ApiModelProperty(value = "payOrg 线下付款机构")
    private String institution_name;
    private String whiteListConfirmFlag;
    private String appsFlyerId;
    private String education_level;
    private Integer maxApplyAmount;
    private String religionCode;
    private String canContactTimeCode;
    private String livenessId;
    private List<ContactInfo> contacts;
    private String fullname;
    private String bank_act_num;
    private String token;
    private String province;
    private String city;
    private String district;
    private String address;
    private String live_length;
    private String contact_fullname1;
    private String relation1;
    private String contact_telephone1;
    private String contact_fullname2;
    private String relation2;
    private String contact_telephone2;
    private String contact_fullname3;
    private String relation3;
    private String contact_telephone3;
    private String position;
    private String client_id;
    private String idCardImagePath;

    public String getBackPhotoImagePath() {
        return backPhotoImagePath;
    }

    public void setBackPhotoImagePath(String backPhotoImagePath) {
        this.backPhotoImagePath = backPhotoImagePath;
    }

    private String backPhotoImagePath;
    private String holdImagePath;
    private String type;
    private String account_name;
    private String channel_name;
    private String company_id;
    private String step_name;
    private String amount;
    private String reference_no;
    private String loan_amount;
    private String invite_code;
    private String facebook_messager;
    private int it_service_need;


    public int getIt_service_need() {
        return it_service_need;
    }

    public void setIt_service_need(int it_service_need) {
        this.it_service_need = it_service_need;
    }

    public String getFacebook_messager() {
        return facebook_messager;
    }

    public void setFacebook_messager(String facebook_messager) {
        this.facebook_messager = facebook_messager;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public Integer getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Integer applyAmount) {
        this.applyAmount = applyAmount;
    }

    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public long getPreApplyId() {
        return preApplyId;
    }

    public void setPreApplyId(long preApplyId) {
        this.preApplyId = preApplyId;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWork_phone() {
        return work_phone;
    }

    public void setWork_phone(String work_phone) {
        this.work_phone = work_phone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactRelationCode() {
        return contactRelationCode;
    }

    public void setContactRelationCode(String contactRelationCode) {
        this.contactRelationCode = contactRelationCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getWorking_time_type() {
        return working_time_type;
    }

    public void setWorking_time_type(String working_time_type) {
        this.working_time_type = working_time_type;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIsValidateCode() {
        return isValidateCode;
    }

    public void setIsValidateCode(String isValidateCode) {
        this.isValidateCode = isValidateCode;
    }

    public String getJobTypeCode() {
        return jobTypeCode;
    }

    public void setJobTypeCode(String jobTypeCode) {
        this.jobTypeCode = jobTypeCode;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getLoan_reason() {
        return loan_reason;
    }

    public void setLoan_reason(String loan_reason) {
        this.loan_reason = loan_reason;
    }

    public Integer getLoanTerms() {
        return loanTerms;
    }

    public void setLoanTerms(Integer loanTerms) {
        this.loanTerms = loanTerms;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getCard_type_code() {
        return card_type_code;
    }

    public void setCard_type_code(String card_type_code) {
        this.card_type_code = card_type_code;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<ThirdInfosBean> getThirdInfos() {
        return thirdInfos;
    }

    public void setThirdInfos(List<ThirdInfosBean> thirdInfos) {
        this.thirdInfos = thirdInfos;
    }

    public String getHomeAddrCityCode() {
        return homeAddrCityCode;
    }

    public void setHomeAddrCityCode(String homeAddrCityCode) {
        this.homeAddrCityCode = homeAddrCityCode;
    }

    public void setHomeAddrCountyCode(String homeAddrCountyCode) {
        this.homeAddrCountyCode = homeAddrCountyCode;
    }

    public String getHomeAddrCountyCode() {
        return homeAddrCountyCode;
    }

    public String getHomeAddrDetail() {
        return homeAddrDetail;
    }

    public void setHomeAddrDetail(String homeAddrDetail) {
        this.homeAddrDetail = homeAddrDetail;
    }

    public String getHomeAddrProvinceCode() {
        return homeAddrProvinceCode;
    }

    public void setHomeAddrProvinceCode(String homeAddrProvinceCode) {
        this.homeAddrProvinceCode = homeAddrProvinceCode;
    }

    public String getHowLongStaying() {
        return howLongStaying;
    }

    public void setHowLongStaying(String howLongStaying) {
        this.howLongStaying = howLongStaying;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
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

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPassword_check() {
        return password_check;
    }

    public void setPassword_check(String password_check) {
        this.password_check = password_check;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIncomeFrequency() {
        return incomeFrequency;
    }

    public void setIncomeFrequency(String incomeFrequency) {
        this.incomeFrequency = incomeFrequency;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getContactRelation() {
        return contactRelation;
    }

    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    public String getOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(String onlinePay) {
        this.onlinePay = onlinePay;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }

    public void setWhiteListConfirmFlag(String whiteListConfirmFlag) {
        this.whiteListConfirmFlag = whiteListConfirmFlag;
    }

    public void setAppsFlyerId(String appsFlyerId) {
        this.appsFlyerId = appsFlyerId;
    }

    public String getEducation_level() {
        return education_level;
    }

    public void setEducation_level(String education_level) {
        this.education_level = education_level;
    }

    public Integer getMaxApplyAmount() {
        return maxApplyAmount;
    }

    public void setMaxApplyAmount(Integer maxApplyAmount) {
        this.maxApplyAmount = maxApplyAmount;
    }

    public void setReligionCode(String religionCode) {
        this.religionCode = religionCode;
    }

    public void setCanContactTimeCode(String canContactTimeCode) {
        this.canContactTimeCode = canContactTimeCode;
    }

    public void setLivenessId(String livenessId) {
        this.livenessId = livenessId;
    }

    public List<ContactInfo> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactInfo> contacts) {
        this.contacts = contacts;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBank_act_num() {
        return bank_act_num;
    }

    public void setBank_act_num(String bank_act_num) {
        this.bank_act_num = bank_act_num;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLive_length(String live_length) {
        this.live_length = live_length;
    }

    public void setContact_fullname1(String contact_fullname1) {
        this.contact_fullname1 = contact_fullname1;
    }

    public void setRelation1(String relation1) {
        this.relation1 = relation1;
    }

    public void setContact_telephone1(String contact_telephone1) {
        this.contact_telephone1 = contact_telephone1;
    }

    public void setContact_fullname2(String contact_fullname2) {
        this.contact_fullname2 = contact_fullname2;
    }

    public void setRelation2(String relation2) {
        this.relation2 = relation2;
    }

    public void setContact_telephone2(String contact_telephone2) {
        this.contact_telephone2 = contact_telephone2;
    }

    public void setContact_fullname3(String contact_fullname3) {
        this.contact_fullname3 = contact_fullname3;
    }

    public void setRelation3(String relation3) {
        this.relation3 = relation3;
    }

    public void setContact_telephone3(String contact_telephone3) {
        this.contact_telephone3 = contact_telephone3;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getIdCardImagePath() {
        return idCardImagePath;
    }

    public void setIdCardImagePath(String idCardImagePath) {
        this.idCardImagePath = idCardImagePath;
    }

    public String getHoldImagePath() {
        return holdImagePath;
    }

    public void setHoldImagePath(String holdImagePath) {
        this.holdImagePath = holdImagePath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setStep_name(String step_name) {
        this.step_name = step_name;
    }

    public String getStep_name() {
        return step_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference_no() {
        return reference_no;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public static class ThirdInfosBean implements Serializable {
        /**
         * authPhone : string
         * authType : string
         * isAuth : string
         * thirdCode : string
         * thirdName : string
         */

        private String authPhone;
        private String authType;
        private String isAuth;
        private String thirdCode;
        private String thirdName;

        public String getAuthPhone() {
            return authPhone;
        }

        public void setAuthPhone(String authPhone) {
            this.authPhone = authPhone;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public String getIsAuth() {
            return isAuth;
        }

        public void setIsAuth(String isAuth) {
            this.isAuth = isAuth;
        }

        public String getThirdCode() {
            return thirdCode;
        }

        public void setThirdCode(String thirdCode) {
            this.thirdCode = thirdCode;
        }

        public String getThirdName() {
            return thirdName;
        }

        public void setThirdName(String thirdName) {
            this.thirdName = thirdName;
        }

        @Override
        public String toString() {
            return "ThirdInfosBean{" +
                    "authPhone='" + authPhone + '\'' +
                    ", authType='" + authType + '\'' +
                    ", isAuth='" + isAuth + '\'' +
                    ", thirdCode='" + thirdCode + '\'' +
                    ", thirdName='" + thirdName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApplyReqPhModel{" +
                "applyAmount=" + applyAmount +
                ", applyId=" + applyId +
                ", preApplyId=" + preApplyId +
                ", approveNo='" + approveNo + '\'' +
                ", authName='" + authName + '\'' +
                ", channel='" + channel + '\'' +
                ", company='" + company + '\'' +
                ", work_phone='" + work_phone + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactRelationCode='" + contactRelationCode + '\'' +
                ", customerId=" + customerId +
                ", deviceId='" + deviceId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", working_time_type='" + working_time_type + '\'' +
                ", industry='" + industry + '\'' +
                ", isValidateCode='" + isValidateCode + '\'' +
                ", jobTypeCode='" + jobTypeCode + '\'' +
                ", lastname='" + lastname + '\'' +
                ", loanCode='" + loanCode + '\'' +
                ", loan_reason='" + loan_reason + '\'' +
                ", loanTerms=" + loanTerms +
                ", middlename='" + middlename + '\'' +
                ", salary='" + salary + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", productCode='" + productCode + '\'' +
                ", employment_type='" + employment_type + '\'' +
                ", source='" + source + '\'' +
                ", userId=" + userId +
                ", captcha='" + captcha + '\'' +
                ", images=" + images +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", card_num='" + card_num + '\'' +
                ", marital_status='" + marital_status + '\'' +
                ", card_type_code='" + card_type_code + '\'' +
                ", gender='" + gender + '\'' +
                ", thirdInfos=" + thirdInfos +
                ", homeAddrCityCode='" + homeAddrCityCode + '\'' +
                ", homeAddrDetail='" + homeAddrDetail + '\'' +
                ", homeAddrProvinceCode='" + homeAddrProvinceCode + '\'' +
                ", homeAddrCountyCode='" + homeAddrCountyCode + '\'' +
                ", howLongStaying='" + howLongStaying + '\'' +
                ", bank_no='" + bank_no + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", bankCityCode='" + bankCityCode + '\'' +
                ", bank_code='" + bank_code + '\'' +
                ", bankDeposit='" + bankDeposit + '\'' +
                ", bankName='" + bankName + '\'' +
                ", password_check='" + password_check + '\'' +
                ", password='" + password + '\'' +
                ", incomeFrequency='" + incomeFrequency + '\'' +
                ", profession='" + profession + '\'' +
                ", nextIncomeDate='" + nextIncomeDate + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", gps='" + gps + '\'' +
                ", gpsCity='" + gpsCity + '\'' +
                ", contactRelation='" + contactRelation + '\'' +
                ", mobileType='" + mobileType + '\'' +
                ", mobileModel='" + mobileModel + '\'' +
                ", onlinePay='" + onlinePay + '\'' +
                ", institution_name='" + institution_name + '\'' +
                ", whiteListConfirmFlag='" + whiteListConfirmFlag + '\'' +
                ", appsFlyerId='" + appsFlyerId + '\'' +
                ", education_level='" + education_level + '\'' +
                ", maxApplyAmount=" + maxApplyAmount +
                ", religionCode='" + religionCode + '\'' +
                ", canContactTimeCode='" + canContactTimeCode + '\'' +
                ", livenessId='" + livenessId + '\'' +
                ", contacts=" + contacts +
                ", fullname='" + fullname + '\'' +
                ", bank_act_num='" + bank_act_num + '\'' +
                ", token='" + token + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", live_length='" + live_length + '\'' +
                ", contact_fullname1='" + contact_fullname1 + '\'' +
                ", relation1='" + relation1 + '\'' +
                ", contact_telephone1='" + contact_telephone1 + '\'' +
                ", contact_fullname2='" + contact_fullname2 + '\'' +
                ", relation2='" + relation2 + '\'' +
                ", contact_telephone2='" + contact_telephone2 + '\'' +
                ", contact_fullname3='" + contact_fullname3 + '\'' +
                ", relation3='" + relation3 + '\'' +
                ", contact_telephone3='" + contact_telephone3 + '\'' +
                ", position='" + position + '\'' +
                ", client_id='" + client_id + '\'' +
                ", idCardImagePath='" + idCardImagePath + '\'' +
                ", backPhotoImagePath='" + backPhotoImagePath + '\'' +
                ", holdImagePath='" + holdImagePath + '\'' +
                ", type='" + type + '\'' +
                ", account_name='" + account_name + '\'' +
                ", channel_name='" + channel_name + '\'' +
                ", company_id='" + company_id + '\'' +
                ", step_name='" + step_name + '\'' +
                ", amount='" + amount + '\'' +
                ", reference_no='" + reference_no + '\'' +
                ", loan_amount='" + loan_amount + '\'' +
                ", invite_code='" + invite_code + '\'' +
                ", facebook_messager='" + facebook_messager + '\'' +
                ", it_service_need=" + it_service_need +
                '}';
    }
}
