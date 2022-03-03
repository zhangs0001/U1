package com.u1.gocashm.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 产品参数
 * Created by Brain on 2018/9/20.
 */

public class ProductPhRange implements Serializable {

    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String productCode;
    private String loanCode;
    private int minLoanTerms;
    private int maxLoanTerms;

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
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

    public int getMinLoanTerms() {
        return minLoanTerms;
    }

    public void setMinLoanTerms(int minLoanTerms) {
        this.minLoanTerms = minLoanTerms;
    }

    public int getMaxLoanTerms() {
        return maxLoanTerms;
    }

    public void setMaxLoanTerms(int maxLoanTerms) {
        this.maxLoanTerms = maxLoanTerms;
    }
}
