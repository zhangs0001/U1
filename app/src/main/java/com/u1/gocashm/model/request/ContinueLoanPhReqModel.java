package com.u1.gocashm.model.request;

import java.math.BigDecimal;

/**
 * @author hewei
 * @date 2018/9/12 0012 下午 1:48
 */
public class ContinueLoanPhReqModel {

    private BigDecimal loanAmt;
    private int loanCount;
    private Integer loanTerms;
    private String productCode;
    private String localPhoneNum;
    private String localPhoneNum2;
    private String channel;
    private String source;
    private String gps;
    private Integer maxApplyAmount;

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public int getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(int loanCount) {
        this.loanCount = loanCount;
    }

    public Integer getLoanTerms() {
        return loanTerms;
    }

    public void setLoanTerms(Integer loanTerms) {
        this.loanTerms = loanTerms;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLocalPhoneNum() {
        return localPhoneNum;
    }

    public void setLocalPhoneNum(String localPhoneNum) {
        this.localPhoneNum = localPhoneNum;
    }

    public String getLocalPhoneNum2() {
        return localPhoneNum2;
    }

    public void setLocalPhoneNum2(String localPhoneNum2) {
        this.localPhoneNum2 = localPhoneNum2;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public Integer getMaxApplyAmount() {
        return maxApplyAmount;
    }

    public void setMaxApplyAmount(Integer maxApplyAmount) {
        this.maxApplyAmount = maxApplyAmount;
    }
}
