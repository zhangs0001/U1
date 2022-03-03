package com.u1.gocashm.model.response;



import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

public class UserSurveyModel extends AbstractBaseRespBean<Status, UserSurveyModel.UserSurvey> {

    public static class UserSurvey {
        private String title;
        private List<String> items;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }
    }
}
