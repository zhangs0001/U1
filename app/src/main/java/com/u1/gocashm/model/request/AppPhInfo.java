package com.u1.gocashm.model.request;

/**
 * @author hewei
 * @date 2018/9/28 0028 下午 2:34
 */
public class AppPhInfo {

    private String pkgName;
    private String appName;
    private String packagePath;
    private String versionName;
    private int versionCode;
    private String firstInstallTime;
    private String isAppActive;
    private String lastUpdateTime;

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(String firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public String getIsAppActive() {
        return isAppActive;
    }

    public void setIsAppActive(String isAppActive) {
        this.isAppActive = isAppActive;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
