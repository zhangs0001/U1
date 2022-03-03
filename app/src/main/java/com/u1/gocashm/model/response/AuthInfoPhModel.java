package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/9/11 0011 下午 3:44
 */
public class AuthInfoPhModel extends AbstractBaseRespBean<Status, AuthInfoPhModel.AuthInfo> {

    public static class AuthInfo {
        private String authId;
        private String authType;
        private String deviceId;
        private String idcard;
        private String isAuth;
        private String phone;

        public String getAuthId() {
            return authId;
        }

        public void setAuthId(String authId) {
            this.authId = authId;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getIsAuth() {
            return isAuth;
        }

        public void setIsAuth(String isAuth) {
            this.isAuth = isAuth;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
