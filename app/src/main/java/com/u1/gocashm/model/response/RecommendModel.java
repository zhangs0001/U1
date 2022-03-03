package com.u1.gocashm.model.response;



import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

/**
 * @author hewei
 * @date 2018/11/9 0009 上午 11:48
 */
public class RecommendModel extends AbstractBaseRespBean<Status, List<RecommendModel.Recommend>> {

    public static class Recommend {
        private String created_at;
        private User invited_user;
        private String updated_at;
        public static class User{
            private String telephone;
            private String fullname;

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public User getInvited_user() {
            return invited_user;
        }

        public void setInvited_user(User invited_user) {
            this.invited_user = invited_user;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
