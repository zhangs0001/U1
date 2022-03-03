package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * Created by jishudiannao on 2018/9/11.
 */

public class ContractDetailPhModel extends AbstractBaseRespBean<Status, ContractDetailPhModel.ContractDetail> {

    public static class ContractDetail {
        public String contractNo;
        public String name;
        public String payNo;
        public String applyId;
        public String loanTerm;
        public String applyDate;
        public String loanDate;
        public int contractAmount;
        public int loanAmount;
        public int payAmount;
        public int currentDueAmount;
        public String loanStatus;
        public String contractStatus;
        public String statusName;
        public String repaymentDate;
        public String paymentSchedules;
    }


}
