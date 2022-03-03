package com.u1.gocashm.model.request;

/**
 * @author hewei
 * @date 2019/12/19 0019 下午 6:06
 */
public class DdiReqModel {

    private int protocol;
    private String did;
    private String pkg;
    private String ver;

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
