package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;


public class ChannelListModel extends AbstractBaseRespBean<Status, List<ChannelListModel.Channel>> {

    public static class Channel {
        public String name;
        public String fee;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }
    }


}
