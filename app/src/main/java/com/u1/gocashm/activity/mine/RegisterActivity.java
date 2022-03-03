package com.u1.gocashm.activity.mine;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.MainPhActivity;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.LoginModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.dialog.RegisterCouponDialog;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2019/3/5 0005 下午 5:27
 */
public class RegisterActivity extends BasePhTitleBarActivity {


    @BindView(R.id.input_user_phone)
    InputView inputUserPhone;
    @BindView(R.id.input_password)
    InputView inputPassword;
    @BindView(R.id.input_confirm_password)
    InputView inputConfirmPassword;
    @BindView(R.id.et_get_code)
    EditText etSmsCode;
    @BindView(R.id.tv_sms_code_title)
    TextView tvSmsCodeTitle;
    @BindView(R.id.bt_get_code)
    Button btGetCode;

    @BindView(R.id.edit_invite_code)
    EditText edit_invite_code;
    @BindView(R.id.tv_get_invite_code)
    TextView tv_get_invite_code;


    private boolean isCountFinished;
    private CountDownTimer timerPh;

    private Activity activity;
    private SharedPreferencesPhUtil spUtils;


    private RequestPermissionDialog permissionDialog;
    private boolean permissionLocationFlag; //授权标志位
    private boolean permissionPhoneFlag; //授权标志位

    private static final int FORGET_PASSWORD = 100;
    private static final int CONFIRM_PHONE = 101;
    private static final int REGISTER = 102;
    private static final int REQUEST_PHONE_STATE = 1001;
    private static final int LOCATION = 1002;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activity = this;
        initData();
        initView();
        initPermission();
    }

    @SuppressLint("LogNotTimber")
    private void initView() {
//        InputPhUtils.setBlankToEditText(inputUserPhone.getEt_content());
        tvSmsCodeTitle.setText(getFirstWordRed(getString(R.string.code)));


    }

    private void initData() {
        spUtils = SharedPreferencesPhUtil.getInstance(activity);
        stopCount();
    }

    @Override
    public void initTitleBar() {
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        setTitleBack(R.string.register);
    }

    @OnClick({R.id.bt_get_code, R.id.tv_register, R.id.tv_to_login, R.id.tv_get_invite_code})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_get_code:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P01_I_Verifcode", true);
                getCode();
                break;
            case R.id.tv_register:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P01_C_Next", true);
                register();
                break;
            case R.id.tv_to_login:
                toLogin();
                break;
            case R.id.tv_get_invite_code:
                final int from = tv_get_invite_code.getWidth();
                final int to = (int) (from * 1.2f); // increase by 20%
                final LinearInterpolator interpolator = new LinearInterpolator();

                ValueAnimator firstAnimator = ValueAnimator.ofInt(from, to);
                firstAnimator.setTarget(tv_get_invite_code);
                firstAnimator.setInterpolator(interpolator);
                firstAnimator.setDuration(150);

                final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_get_invite_code.getLayoutParams();
                firstAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        params.width = (Integer) animation.getAnimatedValue();
                        tv_get_invite_code.setAlpha(1 - animation.getAnimatedFraction());
                        tv_get_invite_code.requestLayout();
                    }
                });

                firstAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // reset alpha channel
                        tv_get_invite_code.setAlpha(1.0f);
                        tv_get_invite_code.setVisibility(View.GONE);

                        edit_invite_code.setVisibility(View.VISIBLE);

                        ValueAnimator secondAnimator = ValueAnimator.ofInt(to, inputConfirmPassword.getWidth());
                        secondAnimator.setTarget(edit_invite_code);
                        secondAnimator.setInterpolator(interpolator);
                        secondAnimator.setDuration(150);

                        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) edit_invite_code.getLayoutParams();
                        secondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                params.width = (Integer) animation.getAnimatedValue();
                                edit_invite_code.requestLayout();
                            }
                        });
                        secondAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                InputMethodManager inputManager =
                                        (InputMethodManager) edit_invite_code.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputManager.showSoftInput(edit_invite_code, 0);
                            }
                        });

                        secondAnimator.start();
                    }
                });

                firstAnimator.start();
                break;
        }
    }

    private void register() {
        final String phone = inputUserPhone.getText();
        String code = etSmsCode.getText().toString();
        String password = inputPassword.getText();
        String passwordConfirm = inputConfirmPassword.getText();
        if (TextUtils.isEmpty(phone)) {
            inputUserPhone.setVerify(R.string.input_phone);
            return;
        } else if (!VerifyUtil.checkPhone(phone.replace(" ", ""))) {
            inputUserPhone.setVerify(R.string.error_phone_no);
            return;
        } else {
            inputUserPhone.setVerify("");
        }

       /* if (!VerifyUtil.isValidPassword(password)) {
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
        }*/


        if (!VerifyUtil.checkSmsCode(code)) {
            ToastUtils.showShort(R.string.verify_smscode);
            return;
        }
        ApplyReqPhModel model = new ApplyReqPhModel();
        String phoneNum = phone.replace(" ", "");
        model.setTelephone(phoneNum);
        model.setCaptcha(code);
        model.setClient_id("android");
        //model.setPassword(password);
        //model.setPassword_check(passwordConfirm);
        model.setInvite_code(edit_invite_code.getText().toString());
        NetworkPhRequest.registerV2(model, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel loginModel) {
                super.onNext(loginModel);
                closeLoadingDialog();
                String modelCode = loginModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    Map map = (Map) loginModel.getData();
                    LoginModel.Login data = new LoginModel.Login();
                    data.setToken((String) map.get("token"));
                    data.setUserId(((Double) map.get("userId")).intValue());

                    Gson gson = new Gson();
                    String couponStr;
                    Object coupon = map.get("will_get_coupon");
                    if (coupon instanceof String) {
                        couponStr = (String) map.get("will_get_coupon");
                    } else {
                        couponStr = gson.toJson(coupon);
                    }

                    if (!TextUtils.isEmpty(couponStr)) {
                        data.setWill_get_coupon(gson.fromJson(couponStr, LoginModel.Login.Invite.class));
                        RegisterCouponDialog.couponType = data.getWill_get_coupon().getCoupon_type();
                        RegisterCouponDialog.endTime = data.getWill_get_coupon().getEnd_time();
                    }

                    Intent intent = new Intent();
                    intent.putExtra(EventTagPh.REGISTER_PHONE, phone);
                    setResult(RESULT_OK, intent);
                    spUtils.clearApplyData();
                    spUtils.login(data.getToken(), data.getUserId(), phoneNum);
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_REGISTER, activity, null);
                    RxBus.getDefault().post(new TagEvent(EventTagPh.LOGIN_SUCCESS));

                    Intent intent1 = new Intent(activity, MainPhActivity.class);
                    intent1.putExtra("will_get_coupon", RegisterCouponDialog.couponType != -1);
                    intent1.putExtra("uploadDeviceInfos", true);
                    intent1.putExtra("showSetPassword", true);
                    startActivity(intent1);
                    finish();
                } else {
                    Toast.makeText(activity, loginModel.getMsg(), Toast.LENGTH_SHORT).show();
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

    private void toLogin() {
        startActivity(new Intent(this, LoginPhActivity.class));
        finish();
    }

    @SuppressLint("LogNotTimber")
    private void getCode() {
        String phone = inputUserPhone.getText();
        if (TextUtils.isEmpty(phone)) {
            inputUserPhone.setVerify(R.string.input_phone);
            return;
        } else if (!VerifyUtil.checkPhone(phone.replace(" ", ""))) {
            inputUserPhone.setVerify(R.string.error_phone_no);
            return;
        } else {
            inputUserPhone.setVerify("");
        }

//        String able = getResources().getConfiguration().locale.getCountry();
//        Log.e("zhangs", "initView: " + able);
//        if (able.equals("EG")) {
//            String[] sp = phone.split(" ");
//            Log.e("zhangs", "getCode: " + sp[0] + sp[1] + sp[2]);
//            NetworkPhRequest.getSmsCode(sp[0] + sp[1] + sp[2], "app_login", new PhSubscriber<BasePhModel>() {
//                @Override
//                public void onNext(BasePhModel smsCodePhModel) {
//                    super.onNext(smsCodePhModel);
//                    String code = smsCodePhModel.getCode();
//                    if (CODE_SUCCESS.equals(code)) {
//                        startCount();
//                    } else {
//                        showToast(smsCodePhModel.getMsg());
//                    }
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    super.onError(e);
//                    ToastUtils.showShort(getString(R.string.error_request_fail));
//                }
//            });
//        } else {
        NetworkPhRequest.getSmsCode(phone.replace(" ", ""), "app_login", new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel smsCodePhModel) {
                super.onNext(smsCodePhModel);
                String code = smsCodePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    startCount();
                } else {
                    showToast(smsCodePhModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(getString(R.string.error_request_fail));
            }
        });
//        }
    }

    private void startCount() {
        if (!isCountFinished) {
            return;
        }
        isCountFinished = false;
        if (timerPh != null) {
            timerPh.cancel();
        }
        timerPh = new CountDownTimer(1000 * 60, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (btGetCode != null) {
                    btGetCode.setText(getString(R.string.get_code_hint) + (millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (btGetCode != null) {
                    btGetCode.setEnabled(true);
                    btGetCode.setClickable(true);
                    btGetCode.setTextColor(getResources().getColor(R.color.white));
                    btGetCode.setText(R.string.reacquire_code);
                }
                isCountFinished = true;
            }
        };
        if (btGetCode != null) {
            btGetCode.setEnabled(false);
            btGetCode.setClickable(false);
        }
        timerPh.start();
    }

    private void stopCount() {
        if (null != timerPh) {
            timerPh.cancel();
        }
        if (btGetCode != null) {
            btGetCode.setEnabled(true);
            btGetCode.setClickable(true);
            btGetCode.setText(R.string.get_code);
        }
        isCountFinished = true;
    }

    @Override
    protected void onDestroy() {
        if (mBehaviorRecord != null) {
            mBehaviorRecord.setToken(TokenUtils.getToken(this));
        }
        super.onDestroy();
        if (null != timerPh) {
            timerPh.cancel();
        }
    }

    private SpannableString getFirstWordRed(String text) {
        SpannableString style = new SpannableString(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        return style;
    }


    @SuppressLint("CheckResult")
    private void initPermission() {
        //检查权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            showLocationDialog();
        } else {
            permissionLocationFlag = true;
        }
        if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            showPhoneStateDialog();
        } else {
            permissionPhoneFlag = true;
        }
    }

    private void showLocationDialog() {
        permissionDialog = new RequestPermissionDialog();
        permissionDialog.setData(R.drawable.location_img, R.string.location_info_title, R.string.location_info_content, R.string.location_info_hint);
        permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick() {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION);
            }
        });
        permissionDialog.show(getSupportFragmentManager(), "2");
    }

    private void showPhoneStateDialog() {
        permissionDialog = new RequestPermissionDialog();
        permissionDialog.setData(R.drawable.phone_img, R.string.phone_info_title, R.string.phone_info_content, R.string.phone_info_hint);
        permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick() {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
            }
        });
        permissionDialog.show(getSupportFragmentManager(), "1");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 做非空判断，避免数组下标越界
        if (grantResults.length <= 0) {
            return;
        }

        int result = grantResults[0];
        String permission = permissions.length > 0 ? permissions[0] : "";
        if (requestCode == REQUEST_PHONE_STATE) {
            permissionPhoneFlag = (result == PackageManager.PERMISSION_GRANTED);
            if (!permissionPhoneFlag) {
                showPhoneStateDialog();
            }
            if (result == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) { //用户选择了"不再询问"
                    getAppDetailSettingIntent();
                }
            }
        } else if (requestCode == LOCATION) {
            permissionLocationFlag = (result == PackageManager.PERMISSION_GRANTED);
            if (!permissionLocationFlag) {
                showLocationDialog();
            }
            if (result == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) { //用户选择了"不再询问"
                    getAppDetailSettingIntent();
                }
            }
        }
    }

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        toLogin();
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected List<BehaviorRecord> getRecords() {
        return new ArrayList<BehaviorRecord>() {{
            add(inputUserPhone.getBehaviorRecord());
            add(inputPassword.getBehaviorRecord());
            add(inputConfirmPassword.getBehaviorRecord());
        }};
    }

    @Override
    protected String getRecordBackKey() {
        return "P01_C_Back";
    }

    @Override
    protected String getRecordEnterKey() {
        return "P01_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P01_Leave";
    }
}
