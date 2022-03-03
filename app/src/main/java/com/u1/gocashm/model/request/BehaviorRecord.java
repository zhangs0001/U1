package com.u1.gocashm.model.request;

/**
 * @author hewei
 * @date 2018/9/13 0013 下午 3:10
 */
public class BehaviorRecord {
    private String controlNo;
    private String newValue;
    private String oldValue;
    private String startTime;
    private String endTime;

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = String.valueOf(endTime);
    }

    public void setEndTime() {
        setEndTime(System.currentTimeMillis());
    }

    public void setStartTime() {
        setStartTime(String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return "BehaviorRecord{" +
                "controlNo='" + controlNo + '\'' +
                ", newValue='" + newValue + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}


