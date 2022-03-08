package com.u1.gocashm.model.request;

import java.io.Serializable;

public class ApplyReqPayMentTypeBody implements Serializable {
    private String Vodafone;
    private String Orange;
    private String myFawry;
    private String BankWallet;
    private String Other;

    public String getVodafone() {
        return Vodafone;
    }

    public void setVodafone(String vodafone) {
        Vodafone = vodafone;
    }

    public String getOrange() {
        return Orange;
    }

    public void setOrange(String orange) {
        Orange = orange;
    }

    public String getMyFawry() {
        return myFawry;
    }

    public void setMyFawry(String myFawry) {
        this.myFawry = myFawry;
    }

    public String getBankWallet() {
        return BankWallet;
    }

    public void setBankWallet(String bankWallet) {
        BankWallet = bankWallet;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    @Override
    public String toString() {
        return "ApplyReqPayMentTypeBody{" +
                "Vodafone='" + Vodafone + '\'' +
                ", Orange='" + Orange + '\'' +
                ", myFawry='" + myFawry + '\'' +
                ", BankWallet='" + BankWallet + '\'' +
                ", Other='" + Other + '\'' +
                '}';
    }
}
