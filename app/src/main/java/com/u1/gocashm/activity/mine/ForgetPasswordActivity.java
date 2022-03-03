package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.ForgetPwdPhReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.ResetPasswordRespModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.view.InputView;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by jishudiannao on 2018/9/6.
 */

public class ForgetPasswordActivity extends BasePhTitleBarActivity {

    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();


    @BindView(R.id.input_forget_pwd)
    InputView input_forget_pwdPh;
    @BindView(R.id.input_forget_pwdconfirm)
    InputView input_forget_pwd_confirmPh;
    @BindView(R.id.input_idcard_type)
    InputView inputIdcardType;
    @BindView(R.id.input_idcard)
    InputView inputIdcard;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;


    private String phone;
    private Activity activity;
    private boolean isHaveIdCard;
    private String cardType;
    private ResetPasswordRespModel.ResetPassword resetPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);
        activity = this;
        initData();
        initView();
    }

    @OnClick({R.id.bt_forget_submit})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_forget_submit:
                submit();
                break;
        }
    }

    private void initData() {
        phone = getIntent().getStringExtra(PhConstants.MODIFY_PWD_PHONE);
        resetPassword = (ResetPasswordRespModel.ResetPassword) getIntent().getSerializableExtra(PhConstants.APPLY_INFO);
    }

    private void initView() {
//        todo
        tvUserPhone.setText("+2 " + phone);
        if (resetPassword != null) {
            cardType = resetPassword.getCardType();
            if (!TextUtils.isEmpty(cardType)) {
                isHaveIdCard = true;
                inputIdcard.setVisibility(View.VISIBLE);
                inputIdcardType.setVisibility(View.VISIBLE);
                inputIdcardType.setText(cardType);
                inputIdcardType.setEnabled(false);
            }
        }
        inputIdcard.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (isHaveIdCard && !VerifyUtil.checkIdCard(cardType, s.toString())) {
                    inputIdcard.setVerify(getString(R.string.a16));
                } else {
                    inputIdcard.setVerify("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void submit() {
        String idcard = inputIdcard.getText();
        String pwd = input_forget_pwdPh.getText();
        String pwdconfirm = input_forget_pwd_confirmPh.getText();
        if (isHaveIdCard && !VerifyUtil.checkIdCard(cardType, idcard)) {
            inputIdcard.setVerify(getString(R.string.a16));
            return;
        } else {
            inputIdcard.setVerify("");
        }
        if (!VerifyUtil.isValidPassword(pwd)) {
            input_forget_pwdPh.setVerify(R.string.error_password_invalid);
            return;
        } else {
            input_forget_pwdPh.setVerify("");
        }
        if (!pwd.equals(pwdconfirm)) {
            input_forget_pwd_confirmPh.setVerify(R.string.error_password_not_same);
            return;
        } else {
            input_forget_pwd_confirmPh.setVerify("");
        }
        KeyboardUtils.hideSoftInput(activity);
        ForgetPwdPhReqModel modelPh = new ForgetPwdPhReqModel();
        modelPh.telephone = phone.replace(" ", "");
        modelPh.password = pwd;
        modelPh.password_check = pwdconfirm;
        if (isHaveIdCard) {
            modelPh.idcard = idcard;
        }
        NetworkPhRequest.resetPasswordSubmit(modelPh, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                String code = basePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    ToastUtils.showShort(R.string.password_set_ok2);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showShort(basePhModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showToast(R.string.error_request_fail);
            }
        });
    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.a14));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }
}
