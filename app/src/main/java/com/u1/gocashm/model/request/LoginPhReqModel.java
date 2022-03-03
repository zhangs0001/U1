package com.u1.gocashm.model.request;

/**
 * @author hewei
 * @date 2018/9/10 0010 上午 11:04
 */
public class LoginPhReqModel {
    private String phone;
    private String code;
    private String password;
    private String loginType; //登录类型，枚举 PWD_LOGIN、 CODE_LOGIN
    private String deviceId;
    private String gps; // GPS经纬度，如116.396574,39.992706

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
