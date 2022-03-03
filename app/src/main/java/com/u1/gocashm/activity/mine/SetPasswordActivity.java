package com.u1.gocashm.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.ChangePwdPhReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.view.InputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/8/30 0030 上午 10:54
 */
public class SetPasswordActivity extends BasePhTitleBarActivity {
    @BindView(R.id.input_password)
    InputView inputPassword;
    @BindView(R.id.input_confirm_password)
    InputView inputConfirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
    }

    @Override
    public void initTitleBar() {
        setTitle(getString(R.string.a19));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @OnClick({R.id.tv_confirm})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_confirm:
                updatePassword(inputPassword.getText(), inputConfirmPassword.getText());
                break;
        }
    }

    private void updatePassword(String password, String passwordConfirm) {
        if (!VerifyUtil.isValidPassword(password)) {
            inputPassword.setVerify(R.string.error_password_invalid);
            return;
        } else {
            inputPassword.setVerify("");
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            inputConfirmPassword.setVerify(R.string.set_new_password_hint);
            return;
        } else {
            if (!password.equals(passwordConfirm)) {
                inputConfirmPassword.setVerify(R.string.error_password_not_same);
                return;
            } else {
                inputConfirmPassword.setVerify("");
            }
        }

        KeyboardUtils.hideSoftInput(this);
        ChangePwdPhReqModel modelPh = new ChangePwdPhReqModel();
        modelPh.old_password = "";
        modelPh.new_password = password;
        modelPh.check_password = passwordConfirm;
        modelPh.token = TokenUtils.getToken(this);
        NetworkPhRequest.changePassword(modelPh, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
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
                closeLoadingDialog();
                showToast(R.string.error_request_fail);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
