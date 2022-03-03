package com.u1.gocashm.util.model;

import java.io.Serializable;

public class DeviceInfo implements Serializable {
    /**
     * 启动时间
     */
    private String startTime;

    private String idcard;

    private String phone;
    /**
     * // 总空间
     */
    private String availableMemory;
    /**
     * // 可用空间
     */
    private String residualMemory;
    /**
     * // 持久设备ID
     */
    private String persistentDeviceId;
    /**
     * // 设备ID
     */
    private String deviceId;
    /**
     * // 设备型号
     */
    private String deviceName;
    /**
     * // 设备名
     */
    private String device;
    /**
     * // 设备版本号
     */
    private String deviceVersion;
    /**
     * // 屏幕宽度
     */
    private String screenSize;
    /**
     * // GPS定位
     */
    private String gpsInfo;
    /**
     * // 内网ip
     */
    private String intranetIP;
    /**
     * // mac地址
     */
    private String wifiMac;
    /**
     * // wifi名
     */
    private String wifiName;
    /**
     * // 是否代理登录
     */
    private String isAgent;
    /**
     * // 是否手机越狱
     */
    private String isBreakPrison;
    /**
     * //广告id
     */
    private String googleAdvertisingId;
    /**
     * //是否有通讯功能
     */
    private String isCommunication;
    /**
     * //是否模拟器
     */
    private String isSimulator;
    /**
     * //构建日期
     */
    private String buildDate;

    private long applyId;

    private String anglex;
    private String angley;
    private String anglez;
    /**
     * MEID
     */
    private String meid;

    /**
     * 运行内存
     */
    private String runMemory;

    /**
     * 内存已使用量
     */
    private String memoryUsed;

    /**
     * 基带版本
     */
    private String basebandVersion;

    /**
     * 电池状态
     */
    private String batteryStatus;

    /**
     * 电池电量
     */
    private String batteryPower;

    /**
     * 网络运营商
     */
    private String networkOperators;

    /**
     * 信号强度
     */
    private String signalStrength;

    /**
     * 移动网络类型
     */
    private String mobileNetworkType;

    /**
     * MCC 需授权手机信息
     */
    private String mcc;

    /**
     * MNC 需授权手机信息
     */
    private String mnc;

    /**
     * 已开机时间
     */
    private String howLongBootTime;

    /**
     * 语言
     */
    private String language;

    /**
     * 时区
     */
    private String timeZone;

    /**
     * 系统时间
     */
    private String systemTime;

    /**
     * 屏幕亮度
     */
    private String screenBrightness;

    /**
     * 是否有NFC功能
     */
    private String nfcFunction;

    /**
     * 照片数量
     */
    private Integer numberOfPhotos;
    /**
     * 开机时间
     */
    private String bootTime;
    /**
     * 版本号
     */
    private String version;

    public String getGoogleAdvertisingId() {
        return googleAdvertisingId;
    }

    public void setGoogleAdvertisingId(String googleAdvertisingId) {
        this.googleAdvertisingId = googleAdvertisingId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(String availableMemory) {
        this.availableMemory = availableMemory;
    }

    public String getResidualMemory() {
        return residualMemory;
    }

    public void setResidualMemory(String residualMemory) {
        this.residualMemory = residualMemory;
    }

    public String getPersistentDeviceId() {
        return persistentDeviceId;
    }

    public void setPersistentDeviceId(String persistentDeviceId) {
        this.persistentDeviceId = persistentDeviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(String gpsInfo) {
        this.gpsInfo = gpsInfo;
    }

    public String getIntranetIP() {
        return intranetIP;
    }

    public void setIntranetIP(String intranetIP) {
        this.intranetIP = intranetIP;
    }

//    public String getRequestIp() {
//        return requestIp;
//    }
//
//    public void setRequestIp(String requestIp) {
//        this.requestIp = requestIp;
//    }

    public String getWifiMac() {
        return wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(String isAgent) {
        this.isAgent = isAgent;
    }

    public String getIsBreakPrison() {
        return isBreakPrison;
    }

    public void setIsBreakPrison(String isBreakPrison) {
        this.isBreakPrison = isBreakPrison;
    }

    public void setApplyId(long applyId) {
        this.applyId = applyId;
    }

    public void setAnglex(String anglex) {
        this.anglex = anglex;
    }

    public void setAngley(String angley) {
        this.angley = angley;
    }

    public void setAnglez(String anglez) {
        this.anglez = anglez;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getIsCommunication() {
        return isCommunication;
    }

    public void setIsCommunication(String isCommunication) {
        this.isCommunication = isCommunication;
    }

    public String getIsSimulator() {
        return isSimulator;
    }

    public void setIsSimulator(String isSimulator) {
        this.isSimulator = isSimulator;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public long getApplyId() {
        return applyId;
    }

    public String getAnglex() {
        return anglex;
    }

    public String getAngley() {
        return angley;
    }

    public String getAnglez() {
        return anglez;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getRunMemory() {
        return runMemory;
    }

    public void setRunMemory(String runMemory) {
        this.runMemory = runMemory;
    }

    public String getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(String memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getBasebandVersion() {
        return basebandVersion;
    }

    public void setBasebandVersion(String basebandVersion) {
        this.basebandVersion = basebandVersion;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(String batteryPower) {
        this.batteryPower = batteryPower;
    }

    public String getNetworkOperators() {
        return networkOperators;
    }

    public void setNetworkOperators(String networkOperators) {
        this.networkOperators = networkOperators;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getMobileNetworkType() {
        return mobileNetworkType;
    }

    public void setMobileNetworkType(String mobileNetworkType) {
        this.mobileNetworkType = mobileNetworkType;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getHowLongBootTime() {
        return howLongBootTime;
    }

    public void setHowLongBootTime(String howLongBootTime) {
        this.howLongBootTime = howLongBootTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getScreenBrightness() {
        return screenBrightness;
    }

    public void setScreenBrightness(String screenBrightness) {
        this.screenBrightness = screenBrightness;
    }

    public String getNfcFunction() {
        return nfcFunction;
    }

    public void setNfcFunction(String nfcFunction) {
        this.nfcFunction = nfcFunction;
    }

    public Integer getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public void setNumberOfPhotos(Integer numberOfPhotos) {
        this.numberOfPhotos = numberOfPhotos;
    }

    public String getBootTime() {
        return bootTime;
    }

    public void setBootTime(String bootTime) {
        this.bootTime = bootTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
