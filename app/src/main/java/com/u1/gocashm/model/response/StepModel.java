package com.u1.gocashm.model.response;


import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

public class StepModel extends AbstractBaseRespBean<Status, StepModel.Step> {

    public static class Step {

        /**
         * id : 289
         * user_id : 68795
         * step_name : baseInfo
         * created_at : 2021-03-10 11:06:16
         * updated_at : 2021-03-10 11:06:16
         */

        private int id;
        private int user_id;
        private String step_name;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getStep_name() {
            return step_name;
        }

        public void setStep_name(String step_name) {
            this.step_name = step_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
