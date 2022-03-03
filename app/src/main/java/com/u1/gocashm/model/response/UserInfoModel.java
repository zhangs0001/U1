package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

public class UserInfoModel extends AbstractBaseRespBean<Status, UserInfoModel.UserInfo> {

    public static class UserInfo {

        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

}
