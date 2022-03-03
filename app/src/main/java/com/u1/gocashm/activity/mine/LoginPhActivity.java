package com.u1.gocashm.activity.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.MainPhActivity;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.request.LoginPhReqModel;
import com.u1.gocashm.model.response.LoginModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.AppPhUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;

import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/9/6 0006 下午 1:55
 */
public class LoginPhActivity extends BasePhTitleBarActivity {

    private static final String TAG = LoginPhActivity.class.getSimpleName();
    private static final int FORGET_PASSWORD = 100;
    private static final int CONFIRM_PHONE = 101;
    private static final int REGISTER = 102;
    private static final int REQUEST_PHONE_STATE = 1001;
    private static final int LOCATION = 1002;
    private static final int REQUEST_PHONE_NUMBER = 1003;
    @BindView(R.id.input_user_phone)
    InputView inputPhUserPhone;
    @BindView(R.id.input_password)
    InputView inputPhPassword;

    private Activity activity;
    private SharedPreferencesPhUtil preferencesPhUtil;

    private RequestPermissionDialog permissionDialog;
    private String smsCode;
    private static long backPressed;
    private boolean permissionLocationFlag; //授权标志位
    private boolean permissionPhoneFlag; //授权标志位
    private boolean permissionPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        ButterKnife.bind(this);
        initData();
        initView();
        initPermission();
    }

    private void initView() {
//        inputPhUserPhone.getEt_content().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int i2) {
//                if (s == null || s.length() == 0) {
//                    return;
//                }
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < s.length(); i++) {
//                    if (i != 4 && i != 9 && s.charAt(i) == ' ') {
//                        continue;
//                    } else {
//                        sb.append(s.charAt(i));
//                        if ((sb.length() == 5 || sb.length() == 10) && sb.charAt(sb.length() - 1) != ' ') {
//                            sb.insert(sb.length() - 1, ' ');
//                        }
//                    }
//                }
//                if (!sb.toString().equals(s.toString())) {
//                    int index = start + 1;
//                    if (sb.charAt(start) == ' ') {
//                        if (before == 0) {
//                            index++;
//                        } else {
//                            index--;
//                        }
//                    } else {
//                        if (before == 1) {
//                            index--;
//                        }
//                    }
//
//                    inputPhUserPhone.getEt_content().setText(sb.toString());
//                    inputPhUserPhone.getEt_content().setSelection(index);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    private void initData() {
        preferencesPhUtil = SharedPreferencesPhUtil.getInstance(activity);
        String phoneNum = getIntent().getStringExtra(PhConstants.APPLYPHONE);
        if (!TextUtils.isEmpty(phoneNum)) {
            inputPhUserPhone.setText(phoneNum);
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String adId = AppPhUtils.getAdvertisId(activity);
                    preferencesPhUtil.saveString(PhConstants.ADVERTISID, adId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initTitleBar() {
        setTitle(R.string.login);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @OnClick({R.id.tv_forget_password, R.id.tv_login, R.id.tv_to_main})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_forget_password:
                forgetPwd();
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_to_main:
                BehaviorRecord p00CRegister = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P00_C_Register", true);
                p00CRegister.setEndTime();
                BehaviorPhUtils.saveBehaviorReqModelV2(mBehaviorRecord);
                toRegister();
                break;
        }
    }

    private void forgetPwd() {
        String phone = inputPhUserPhone.getText();
        Intent intent = new Intent(activity, CodeLoginActivity.class);
        if (!TextUtils.isEmpty(phone)) {
            intent.putExtra(PhConstants.MODIFY_PWD_PHONE, phone);
        }
        startActivityForResult(intent, FORGET_PASSWORD);
    }

    private void login() {
        final String phoneNum = inputPhUserPhone.getText();
        String password = inputPhPassword.getText();
        //todo
        if (!VerifyUtil.checkPhoneForPh(phoneNum.replace(" ", ""))) {
            inputPhUserPhone.setVerify(R.string.error_phone_no);
            return;
        } else {
            inputPhUserPhone.setVerify("");
        }
        if (!VerifyUtil.isValidPassword(password)) {
            inputPhPassword.setVerify(R.string.error_password_invalid);
            return;
        } else {
            inputPhPassword.setVerify("");
        }
        LoginPhReqModel loginPhReq = new LoginPhReqModel();
        if (!TextUtils.isEmpty(smsCode)) {
            loginPhReq.setCode(smsCode);
        }
        String phone = phoneNum.replace(" ", "");
        loginPhReq.setPhone(phone);
        loginPhReq.setPassword(password);
        loginPhReq.setLoginType("PWD_LOGIN");
        loginPhReq.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
        loginPhReq.setGps(AppPhUtils.getGpsInfo());
        NetworkPhRequest.login(loginPhReq, new PhSubscriber<LoginModel>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(LoginModel loginModel) {
                super.onNext(loginModel);
                closeLoadingDialog();
                String code = loginModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    LoginModel.Login body = loginModel.getData();
                    preferencesPhUtil.clearApplyData();
                    preferencesPhUtil.login(body.getToken(), body.getUserId(), phone);
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_LOG_IN, activity, null);
                    RxBus.getDefault().post(new TagEvent(EventTagPh.LOGIN_SUCCESS));
                    Intent intent = new Intent(activity, MainPhActivity.class);
                    intent.putExtra("uploadDeviceInfos", true);
                    startActivity(intent);
                    finish();
                    inputPhPassword.setText("");
                } else {
                    smsCode = null;
                    showToast(loginModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                Log.e(TAG, e.getMessage());
            }
        });
    }

    private void toRegister() {
        Intent intent = new Intent(activity, RegisterActivity.class);
        startActivityForResult(intent, REGISTER);
    }

    private void uploadJpushInfo(String idCard, String customerId) {
        try {
            JpushReqModel model = new JpushReqModel();
            model.setRegistrationId(JPushInterface.getRegistrationID(activity));
            model.setCustId(idCard);
            model.setDeviceType("android");
            model.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
            model.setAppName(getString(R.string.app_name));
            model.setCustomerId(customerId);
            model.setAppVersion(AppUtils.getAppVersionName());
            UploadInfoUtils.saveJpushInfo(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            showPhoneNumberDialog();
        } else {
            permissionPhoneNumber = true;
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

    private void showPhoneNumberDialog() {
        permissionDialog = new RequestPermissionDialog();
        permissionDialog.setData(R.drawable.phone_img, R.string.phone_number_info_title, R.string.phone_number_info_content, R.string.phone_info_hint);
        permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick() {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_NUMBERS}, REQUEST_PHONE_NUMBER);
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
        } else if (requestCode == REQUEST_PHONE_NUMBER) {
            permissionPhoneNumber = (result == PackageManager.PERMISSION_GRANTED);
            if (!permissionPhoneNumber) {
                showPhoneNumberDialog();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FORGET_PASSWORD && resultCode == RESULT_OK && data != null) {
            String phone = data.getStringExtra(PhConstants.APPLYPHONE);
            inputPhUserPhone.setText(phone);
        } else if (requestCode == CONFIRM_PHONE && resultCode == RESULT_OK && data != null) {
            smsCode = data.getStringExtra(EventTagPh.CONFIRM_PHONE);
            login();
        } else if (requestCode == REGISTER && resultCode == RESULT_OK && data != null) {
            String phone = data.getStringExtra(EventTagPh.REGISTER_PHONE);
            inputPhUserPhone.setText(phone);
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            finish();
            System.exit(0);
        } else {
            ToastUtils.showShort(getString(R.string.exit_notice));
            backPressed = System.currentTimeMillis();
        }
    }

    private boolean enable =true;
    @Override
    protected void onDestroy() {
        enable = false;
        super.onDestroy();
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return enable;
    }

    @Override
    protected String getRecordBackKey() {
        return "P00_C_Back";
    }

    @Override
    protected String getRecordEnterKey() {
        return "P00_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P00_Leave";
    }
}
