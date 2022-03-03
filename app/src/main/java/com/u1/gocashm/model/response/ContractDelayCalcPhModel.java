package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/9/11 0011 下午 6:59
 */
public class ContractDelayCalcPhModel extends AbstractBaseRespBean<Status, ContractDelayCalcPhModel.ContractDelayCalc> {

    public static class ContractDelayCalc {
        private String minRepayAmt;
        private int extendDay;

        public String getMinRepayAmt() {
            return minRepayAmt;
        }

        public void setMinRepayAmt(String minRepayAmt) {
            this.minRepayAmt = minRepayAmt;
        }

        public int getExtendDay() {
            return extendDay;
        }

        public void setExtendDay(int extendDay) {
            this.extendDay = extendDay;
        }
    }
}
