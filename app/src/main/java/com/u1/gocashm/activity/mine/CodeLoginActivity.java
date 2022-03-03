package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.ForgetPwdPhReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.view.InputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public class CodeLoginActivity extends BasePhTitleBarActivity {

    private static final int FORGET_PASSWORD_CODE = 100;

    @BindView(R.id.input_user_phone)
    InputView inputUserPhone;
    @BindView(R.id.tv_sms_code_title)
    TextView tvSmsCodeTitle;
    @BindView(R.id.et_forget_code)
    EditText etForgetCode;
    @BindView(R.id.bt_forget_code)
    Button btForgetCode;
    @BindView(R.id.input_forget_pwd)
    InputView inputForgetPwd;
    @BindView(R.id.input_forget_pwdconfirm)
    InputView inputForgetPwdconfirm;

    private boolean isCountFinished;
    private CountDownTimer timer;
    private String phone;
    private Activity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_login);
        ButterKnife.bind(this);
        activity = this;
        initData();
    }

    private void initData() {
        stopCount();
        tvSmsCodeTitle.setText(getFirstWordRed(getString(R.string.code)));
//        InputPhUtils.setBlankToEditText(inputUserPhone.getEt_content());
        phone = getIntent().getStringExtra(PhConstants.MODIFY_PWD_PHONE);
        if (!TextUtils.isEmpty(phone)) {
            inputUserPhone.setText(phone);
            inputUserPhone.getEt_content().setSelection(phone.length());
        }
    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.a14));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @OnClick({R.id.bt_forget_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_forget_code:
                getCode();
                break;
            case R.id.tv_confirm:
                confirm();
                break;
        }
    }

    private void getCode() {
        String phone = inputUserPhone.getText().replace(" ", "");
        if (TextUtils.isEmpty(phone)) {
            inputUserPhone.setVerify(R.string.input_phone);
            return;
        } else if (!VerifyUtil.checkPhone(phone.replace(" ", ""))) {
            inputUserPhone.setVerify(R.string.error_phone_no_9);
            return;
        } else {
            inputUserPhone.setVerify("");
        }
        NetworkPhRequest.getSmsCode(phone.replace(" ", ""), "forgot_password", new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel smsCodeModel) {
                super.onNext(smsCodeModel);
                String code = smsCodeModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    startCount();
                } else {
                    showToast(smsCodeModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showToast(R.string.error_request_fail);
            }
        });
    }

    private void confirm() {
        phone = inputUserPhone.getText().replace(" ", "");
        String pwd = inputForgetPwd.getText();
        String pwdconfirm = inputForgetPwdconfirm.getText();
        String code = etForgetCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            inputUserPhone.setVerify(R.string.input_phone);
            return;
        } else if (!VerifyUtil.checkPhoneForPh(phone.replace(" ", ""))) {
            inputUserPhone.setVerify(R.string.verify_phone);
            return;
        } else {
            inputUserPhone.setVerify("");
        }
        if (!VerifyUtil.isValidPassword(pwd)) {
            inputForgetPwd.setVerify(R.string.error_password_invalid);
            return;
        } else {
            inputForgetPwd.setVerify("");
        }
        if (!pwd.equals(pwdconfirm)) {
            inputForgetPwdconfirm.setVerify(R.string.error_password_not_same);
            return;
        } else {
            inputForgetPwdconfirm.setVerify("");
        }
        if (TextUtils.isEmpty(code) && !VerifyUtil.checkSmsCode(code)) {
            showToast(getString(R.string.verify_smscode));
            return;
        }
        KeyboardUtils.hideSoftInput(activity);
        ForgetPwdPhReqModel modelPh = new ForgetPwdPhReqModel();
        modelPh.telephone = phone.replace(" ", "");
        modelPh.password = pwd;
        modelPh.password_check = pwdconfirm;
        modelPh.captcha = code;
        NetworkPhRequest.resetPassword(modelPh, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel resetPasswordRespModel) {
                super.onNext(resetPasswordRespModel);
                closeLoadingDialog();
                String modelCode = resetPasswordRespModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    finish();
                }
                showToast(resetPasswordRespModel.getMsg());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                showToast(R.string.error_request_fail);
            }
        });

    }

    private SpannableString getFirstWordRed(String text) {
        SpannableString style = new SpannableString(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        return style;
    }

    private void startCount() {
        if (!isCountFinished) {
            return;
        }
        isCountFinished = false;
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(1000 * 60, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (btForgetCode != null) {
                    btForgetCode.setText(PhUtils.format(getString(R.string.get_code_hint), (millisUntilFinished / 1000)));
                }
            }

            @Override
            public void onFinish() {
                if (btForgetCode != null) {
                    btForgetCode.setEnabled(true);
                    btForgetCode.setClickable(true);
                    btForgetCode.setTextColor(getResources().getColor(R.color.white));
                    btForgetCode.setText(R.string.reacquire_code);
                }
                isCountFinished = true;
            }
        };
        if (btForgetCode != null) {
            btForgetCode.setEnabled(false);
            btForgetCode.setClickable(false);
        }
        timer.start();
    }

    private void stopCount() {
        if (null != timer) {
            timer.cancel();
        }
        if (btForgetCode != null) {
            btForgetCode.setEnabled(true);
            btForgetCode.setClickable(true);
            btForgetCode.setText(R.string.get_code);
        }
        isCountFinished = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FORGET_PASSWORD_CODE && resultCode == RESULT_OK && data != null) {
            Intent intent = new Intent();
            intent.putExtra(PhConstants.APPLYPHONE, phone);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timer) {
            timer.cancel();
        }
    }
}
