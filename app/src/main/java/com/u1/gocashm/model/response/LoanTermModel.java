package com.u1.gocashm.model.response;

import java.io.Serializable;

/**
 * @author hewei
 * @date 2019/11/27 0027 下午 10:15
 */
public class LoanTermModel implements Serializable {

    private int loanTerm;
    private boolean selected;
    private boolean available;

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
