package com.u1.gocashm.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import cn.jpush.android.api.JPushInterface;

import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.u1.gocashm.activity.mine.LoginPhActivity;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.view.dialog.UpdateDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.u1.gocashm.BuildConfig;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhActivity;
import com.u1.gocashm.activity.mine.GooglePlayPhActivity;
import com.u1.gocashm.model.response.VersionPhModel;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.WeakRefHandler;
import com.u1.gocashm.util.constant.PhConstants;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/8/27 0027 下午 2:06
 */
public class UserGuideActivity extends BasePhActivity {
    private static final String TAG = UserGuideActivity.class.getSimpleName();
    private final int SPLASH_DISPLAY = 0;
    private FragmentActivity activity;
    private Handler.Callback mCallBack = new Handler.Callback() {

        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SPLASH_DISPLAY:
                    startMainActivity();
                    finish();
                    break;
                default:
                    break;
            }
            return true;
        }
    };
    private WeakRefHandler mHandler = new WeakRefHandler(mCallBack);
    private UpdateDialog updateDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        activity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.isConnected()) {
            checkPhVersion();
        } else {
            CircleDialog.Builder builder = new CircleDialog.Builder();
            builder.setText(getString(R.string.error_network_disable))
                    .setPositive(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                            startActivity(intent);
                        }
                    }).show(getSupportFragmentManager());
        }
    }

    private void checkPhVersion() {
        NetworkPhRequest.versionCheck(new PhSubscriber<VersionPhModel>() {

            @Override
            public void onNext(VersionPhModel versionPhModel) {
                super.onNext(versionPhModel);
                String code = versionPhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    final VersionPhModel.Version body = versionPhModel.getData();
                    Log.e(TAG, "onNext: " + body.toString());
                    int net_versionName = Integer.parseInt(body.getVersion().replace(".", ""));
                    int local_versionName = Integer.parseInt(BuildConfig.VERSION_NAME.replace(".", ""));
                    if (body != null && local_versionName < net_versionName) {
                        if (updateDialog != null) {
                            try {
                                updateDialog.dismiss();
                            } catch (Exception e) {
                            }
                        }
                        updateDialog = new UpdateDialog();
                        updateDialog.setVersion(body);
                        updateDialog.setActivity(UserGuideActivity.this);
                        updateDialog.setDialogClickListener(new UpdateDialog.DialogClickListener() {
                            @Override
                            public void onClickCancel() {
                                initData();
                            }

                            @Override
                            public void onClickYes() {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(body.getUrl()));
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(intent);
                                    } else {//没有应用市场，通过浏览器跳转到Google Play
                                        Intent intent2 = new Intent(activity, GooglePlayPhActivity.class);
                                        intent2.putExtra(PhConstants.WEB_URL, body.getUrl());
                                        startActivity(intent2);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        updateDialog.show(getSupportFragmentManager(), "1");
                    } else {
                        initData();
                    }
                } else {
                    showToast(versionPhModel.getMsg());
                    initData();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(TAG, e.getMessage());
                showToast(R.string.error_request_fail);
                initData();
            }
        });
    }

    private void initData() {
        mHandler.sendEmptyMessageAtTime(SPLASH_DISPLAY, 2000);
    }

    private void startMainActivity() {
        SharedPreferencesPhUtil preferencesUtil = SharedPreferencesPhUtil.getInstance(activity);
        boolean isShow = preferencesUtil.getBoolean(PhConstants.FIRST_RUN_DIALOG);
        if (isShow) {
            if (TokenUtils.TokenCheck(activity)) {
                startActivity(new Intent(activity, MainPhActivity.class));
            } else {
                startActivity(new Intent(activity, LoginPhActivity.class));
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(activity, PolicyActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private void uploadJpushInfo(String idCard, String customerId) {
        JpushReqModel model = new JpushReqModel();
        model.setRegistrationId(JPushInterface.getRegistrationID(activity));
        model.setCustId(idCard);
        model.setDeviceType("android");
        model.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
        model.setAppName(getString(R.string.app_name));
        model.setCustomerId(customerId);
        model.setAppVersion(AppUtils.getAppVersionName());
        UploadInfoUtils.saveJpushInfo(model);
    }

}
