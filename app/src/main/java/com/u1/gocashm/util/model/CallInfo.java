package com.u1.gocashm.util.model;

import java.io.Serializable;

public class CallInfo implements Serializable {
    public static final int TYPE_SEND = 1; // 主叫
    public static final int TYPE_RECEIVE = 2; // 被叫
    public static final int TYPE_MISS = 3; // 未接

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //    @SerializedName("id")
    private String callNumber;
    private int callType;
    private String startime;
    private String endTime;

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
