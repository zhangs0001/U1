package com.u1.gocashm.model.request;


import java.util.List;

public class AppInfoReqModel {

    private long applyId;
    private String deviceId;
    private List<AppPhInfo> appInfos;


    public long getApplyId() {
        return applyId;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<AppPhInfo> getAppPhInfos() {
        return appInfos;
    }

    public void setAppPhInfos(List<AppPhInfo> appPhInfos) {
        this.appInfos = appPhInfos;
    }
}
