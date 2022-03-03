package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

/**
 * @author hewei
 * @date 2018/9/6 0006 上午 11:35
 */
public class SmsCodePhModel extends AbstractBaseRespBean<Status, List<SmsCodePhModel.SmsCode>> {

    public static class SmsCode {
        private String kaptcha;

        public String getCode() {
            return kaptcha;
        }

        public void setCode(String kaptcha) {
            this.kaptcha = kaptcha;
        }
    }
}
