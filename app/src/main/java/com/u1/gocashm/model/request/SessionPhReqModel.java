package com.u1.gocashm.model.request;

/**
 * @author hewei
 * @date 2018/9/11 0011 上午 11:42
 */
public class SessionPhReqModel {

    private String accessToken;
    private int userId;
    private String deviceId;
    private String gps;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SessionPhReqModel{" +
                "accessToken='" + accessToken + '\'' +
                ", userId=" + userId +
                ", deviceId='" + deviceId + '\'' +
                ", gps='" + gps + '\'' +
                '}';
    }
}
