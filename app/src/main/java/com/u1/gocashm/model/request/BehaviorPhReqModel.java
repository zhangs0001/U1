package com.u1.gocashm.model.request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hewei
 * @date 2018/9/13 0013 下午 3:10
 */
public class BehaviorPhReqModel {

    private String applyId;
    private String cookieId;
    private String internalIp;
    private String outerIp;
    private String screenHeight;
    private String screenWidth;
    private List<RecordPhModel> records = new ArrayList<>();

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public List<RecordPhModel> getRecords() {
        return records;
    }

    public void setRecords(List<RecordPhModel> records) {
        this.records = records;
    }
}
