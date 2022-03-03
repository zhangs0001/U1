package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/9/10 0010 上午 11:02
 */
public class LoginModel extends AbstractBaseRespBean<Status,LoginModel.Login> {

    public static class Login {
        private int userId;
        private String token;
        private String refresh_token;
        private Invite will_get_coupon;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public Invite getWill_get_coupon() {
            return will_get_coupon;
        }

        public void setWill_get_coupon(Invite will_get_coupon) {
            this.will_get_coupon = will_get_coupon;
        }

        public static class Invite{
            private int coupon_type;
            private String end_time;

            public int getCoupon_type() {
                return coupon_type;
            }

            public void setCoupon_type(int coupon_type) {
                this.coupon_type = coupon_type;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }
        }
    }
}
