package com.u1.gocashm.model.request;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;
import com.u1.gocashm.model.response.LoanResultPhModel;

import java.util.List;

public class FawryBean extends AbstractBaseRespBean<Status, FawryBean.Fawry> {

    public static class Fawry {

        /**
         * status : SUCCESS
         * requestNo : 998079147
         * tradeNo : 998079147
         * msg : 2022-02-22 19;36:25
         */

        private String status;
        private String requestNo;
        private String tradeNo;
        private String msg;
        private String walletQr;

        public String getWalletQr() {
            return walletQr;
        }

        public void setWalletQr(String walletQr) {
            this.walletQr = walletQr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRequestNo() {
            return requestNo;
        }

        public void setRequestNo(String requestNo) {
            this.requestNo = requestNo;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

}
