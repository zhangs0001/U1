package com.u1.gocashm.model.request;

/**
 * Created by jishudiannao on 2018/9/11.
 */
public class ForgetPwdPhReqModel {
    public String telephone;
    public String captcha;
    public String password;
    public String password_check;
    public String isValidateCode; //是否核对验证码Y是N否
    public String idcard;
}
