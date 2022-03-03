package com.u1.gocashm.model.response;



import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.io.Serializable;

public class ResetPasswordRespModel extends AbstractBaseRespBean<Status, ResetPasswordRespModel.ResetPassword> {

    public static class ResetPassword implements Serializable {

        /**
         * name : aa
         * cardType : TIN
         * cardTypeCode : PY04
         */

        private String name;
        private String cardType;
        private String cardTypeCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardTypeCode() {
            return cardTypeCode;
        }

        public void setCardTypeCode(String cardTypeCode) {
            this.cardTypeCode = cardTypeCode;
        }
    }
}
