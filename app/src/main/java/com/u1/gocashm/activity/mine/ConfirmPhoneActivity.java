package com.u1.gocashm.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hewei
 * @date 2019/9/19 0019 下午 4:27
 */
public class ConfirmPhoneActivity extends BasePhTitleBarActivity {

    @BindView(R.id.ll_confirm_phone)
    LinearLayout llConfirmPhone;
    @BindView(R.id.et_sms_confirm_code)
    EditText etSmsConfirmCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.ll_get_sms_code)
    LinearLayout llGetSmsCode;

    private String userPhone;
    private CountDownTimer timer;
    private boolean isCountFinished;
    private String smsCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_phone);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        stopCount();
        etSmsConfirmCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                if (str != null && str.length() == 6) {
                    login();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initData() {
        userPhone = getIntent().getStringExtra(PhConstants.PhUser.USER_PHONE);
    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.a15));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.theme_color_main));
    }

    @OnClick({R.id.tv_get_sms_code, R.id.tv_confirm, R.id.tv_get_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms_code:
            case R.id.tv_get_code:
                getYnCode();
                break;
            case R.id.tv_confirm:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        String code = etSmsConfirmCode.getText().toString();
        if (!code.equals(smsCode)) {
            showToast(R.string.error_sms_code);
        } else {
            Intent intent = new Intent();
            intent.putExtra(EventTagPh.CONFIRM_PHONE, smsCode);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void getYnCode() {
//        NetworkPhRequest.getSmsCode(userPhone.replace(" ", ""), "LOGIN", new PhSubscriber<SmsCodePhModel>() {
//            @Override
//            public void onNext(SmsCodePhModel smsCodePhModel) {
//                super.onNext(smsCodePhModel);
//                Status status = smsCodePhModel.getStatus();
//                if (status != null && "000".equals(status.getCode())) {
//                    changeLayout();
//                    startCount();
//                    smsCode = smsCodePhModel.getBody().getCode();
//                } else if (status != null) {
//                    showToast(status.getMsg());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                showToast(R.string.error_request_fail);
//            }
//        });
    }

    private void changeLayout() {
        if (llConfirmPhone.getVisibility() == View.VISIBLE) {
            llConfirmPhone.setVisibility(View.GONE);
            llGetSmsCode.setVisibility(View.VISIBLE);
        }
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
                if (tvGetCode != null) {
                    tvGetCode.setText(PhUtils.format(getString(R.string.get_code_hint), (millisUntilFinished / 1000)));
                }
            }

            @Override
            public void onFinish() {
                if (tvGetCode != null) {
                    tvGetCode.setEnabled(true);
                    tvGetCode.setClickable(true);
                    tvGetCode.setText(R.string.reacquire_code);
                }
                isCountFinished = true;
            }
        };
        if (tvGetCode != null) {
            tvGetCode.setEnabled(false);
            tvGetCode.setClickable(false);
        }
        timer.start();
    }

    private void stopCount() {
        if (null != timer) {
            timer.cancel();
        }
        if (tvGetCode != null) {
            tvGetCode.setEnabled(true);
            tvGetCode.setClickable(true);
            tvGetCode.setText(R.string.reacquire_code);
        }
        isCountFinished = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timer) {
            timer.cancel();
        }
    }
}
