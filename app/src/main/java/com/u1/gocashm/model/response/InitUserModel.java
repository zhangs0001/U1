package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;


public class InitUserModel  extends AbstractBaseRespBean<Status, InitUserModel.UserLevel> {

    public static class UserLevel{
        private String level;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }


}
