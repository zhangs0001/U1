package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * Created by jishudiannao on 2018/9/6.
 */

public class ApplyIndexPhModel extends AbstractBaseRespBean<Status, ApplyIndexPhModel.ApplyIndex> {

    public static class ApplyIndex {
        public long applyId;
        public long preApplyId;
        public long customerId;
        public int userId;
        public String phone;
        public String firstName;
        public String name;
        public String approveNo;
        public String deviceId;
        public String productCode;
        public String loanCode;
        public int loanTerms;
        public String source;
        public String channel;
        public String validateCode;
        public String isValidateCode;
        public long applyAmount;
        public String authName;
        public Object images;
        public String middleName;
        public String lastName;
    }


}
