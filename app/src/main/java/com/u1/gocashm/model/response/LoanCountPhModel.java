package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * Created by jishudiannao on 2018/9/11.
 */

public class LoanCountPhModel extends AbstractBaseRespBean<Status, LoanCountPhModel.LoanCount> {

    public static class LoanCount {
        public int loanCount;
    }


}
