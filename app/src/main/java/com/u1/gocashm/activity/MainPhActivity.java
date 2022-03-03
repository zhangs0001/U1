package com.u1.gocashm.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.PermissionUtils;
import com.u1.gocashm.PhApplication;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarFragmentActivity;
import com.u1.gocashm.activity.mine.LoginPhActivity;
import com.u1.gocashm.activity.mine.SetPasswordActivity;
import com.u1.gocashm.fragment.LoanResultPhFragment;
import com.u1.gocashm.fragment.MinePhFragment;
import com.u1.gocashm.fragment.PreViewFragment;
import com.u1.gocashm.fragment.RepayFragment;
import com.u1.gocashm.model.request.AppInfoReqModel;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.CouponModel;
import com.u1.gocashm.model.response.LocationModel;
import com.u1.gocashm.model.response.User;
import com.u1.gocashm.model.response.UserInfoModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.ApkTool;
import com.u1.gocashm.util.AppPhUtils;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.GpsPhUtils;
import com.u1.gocashm.util.GsonPhHelper;
import com.u1.gocashm.util.LiveDataBus;
import com.u1.gocashm.util.NoticeMananger;
import com.u1.gocashm.util.PermissionPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.ThreadUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.util.listener.MyPhoneStateListener;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.util.model.DeviceInfo;
import com.u1.gocashm.util.model.LocationInfo;
import com.u1.gocashm.view.dialog.RegisterCouponDialog;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import cn.shuzilm.core.Main;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


public class MainPhActivity extends BasePhTitleBarFragmentActivity {

    private static final String TAG = MainPhActivity.class.getSimpleName();
    private static final int LOCATION = 101;
    private static final int REQUEST_PHONE_STATE = 102;
    MainPhActivity activity;

    private Fragment[] fragments;

    private PreViewFragment loanFragment;
    private MinePhFragment minePhFragment;
    private LoanResultPhFragment resultFragment;
    private RepayFragment repayFragment;
//    private PreviewFragment previewFragment;

    private ImageView[] imageButtons;
    private TextView[] textViews;

    private int index;
    private int currentTabIndex;// 当前fragment的index

    private Disposable disposable;

    private boolean isResultFragment;
    public String loanStatus;

    private MySensorListener sensorListener;
    private SensorManager MyManage; //新建sensor的管理器
    private MyPhoneStateListener myphonelister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        initViews();
        initTabView();
        initData();
        verifyPassword();

        myphonelister = new MyPhoneStateListener();
    }

    private void verifyPassword() {
        if (getIntent().getBooleanExtra("showSetPassword", false)) {
            NetworkPhRequest.getUserInfo(TokenUtils.getToken(activity), new PhSubscriber<UserInfoModel>() {
                @Override
                public void onNext(UserInfoModel userInfoModel) {
                    super.onNext(userInfoModel);
                    String code = userInfoModel.getCode();
                    if (CODE_SUCCESS.equals(code)) {
                        UserInfoModel.UserInfo data = userInfoModel.getData();
                        User user = data.getUser();
                        if (!user.isReload()) {
                            startActivity(new Intent(activity, SetPasswordActivity.class));
                        }
                    }
                }
            });
        }
    }

    private void uploadDeviceInfos() {
        if (!getIntent().getBooleanExtra("uploadDeviceInfos", false)) return;
        try {
            MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
            sensorListener = new MySensorListener();
            MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);

            String nativePhone = null;
            try {
                TelephonyManager Tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                nativePhone = "";
                if (PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
                    nativePhone = Tel.getLine1Number();
                }
                Tel.listen(myphonelister, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS); //实现监听
            } catch (Exception e) {
            }

            int level = 0;
            boolean isCharging = false;
            try {
                Intent batteryStatus = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
            } catch (Exception e) {
            }

            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setBatteryStatus(isCharging ? "Charging" : "unCharging");
            deviceInfo.setBatteryPower(String.valueOf(level));
            deviceInfo.setSignalStrength(SharedPreferencesPhUtil.getInstance(activity).getString(PhConstants.PHONE_DBM));
            deviceInfo.setPhone(nativePhone);
            AppPhUtils.uploadDeviceInfo(TokenUtils.getToken(activity), deviceInfo, activity, AppPhUtils.getScreenWidth(activity), AppPhUtils.getScreenHeight(activity)
                    , sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadAppInfo() {
        if (!getIntent().getBooleanExtra("uploadDeviceInfos", false)) return;
        AppInfoReqModel infoReqBean = new AppInfoReqModel();
        try {
            infoReqBean.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            infoReqBean.setAppPhInfos(ApkTool.getInstallAppList(getPackageManager(), activity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> maps = new HashMap<>();
        try {
            maps.put("data_json", GsonPhHelper.getGson().toJson(infoReqBean.getAppPhInfos()));
        } catch (Exception e) {
        }
//        todo 没写mapkey token
        maps.put("token", TokenUtils.getToken(activity));
        NetworkPhRequest.uploadAppInfo(UploadInfoUtils.getRequestBody(maps), new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel baseModel) {
                super.onNext(baseModel);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    private void uploadGpsInfo() {
        if (!getIntent().getBooleanExtra("uploadDeviceInfos", false)) return;
        ApplyReqPhModel reqModel = new ApplyReqPhModel();
        reqModel.setToken(TokenUtils.getToken(activity));
        LocationInfo locationInfo = new LocationInfo();
        Location location = GpsPhUtils.getLocation();
        if (location != null) {
            locationInfo.setLatitude(String.valueOf(location.getLatitude()));
            locationInfo.setLongitude(String.valueOf(location.getLongitude()));
            reqModel.setPosition(GsonPhHelper.getGson().toJson(locationInfo));
            NetworkPhRequest.getLocation(reqModel, new PhSubscriber<LocationModel>() {

                @Override
                public void onNext(LocationModel locationModel) {
                    super.onNext(locationModel);
                    String code = locationModel.getCode();
                    if (CODE_SUCCESS.equals(code)) {
                        LocationModel.Location data = locationModel.getData();
                        locationInfo.setAddress(data.getAddress());
                        locationInfo.setCity(data.getCity());
                        locationInfo.setProvince(data.getState());
                        locationInfo.setDistrict(data.getDetail().getDistrict());
                        locationInfo.setStreet(data.getDetail().getStreet());
                        locationInfo.setType("finish_apply");
                        UploadInfoUtils.uploadLocation(TokenUtils.getToken(activity), GsonPhHelper.getGson().toJson(locationInfo));
                    }
                }
            });
        }
    }


    private void initData() {
        if (getIntent().getBooleanExtra("will_get_coupon", false)) {
            RegisterCouponDialog.showRegisterCouponDialog(getSupportFragmentManager(), "1");
            RegisterCouponDialog.couponType = -1;
        }
        int whichPage = getIntent().getIntExtra(PhConstants.EXTRA_WHICH_PAGE, 0);
        if (whichPage != currentTabIndex) {
            onTabSelected(whichPage);
        }
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (EventTagPh.APPLY_SUCCESS.equals(tagEvent.getTag())) {
                    onTabSelected(3, tagEvent.getObject());
                } else if (EventTagPh.LOGIN_SUCCESS.equals(tagEvent.getTag())) {
                    onTabSelected(3);
                } else if (EventTagPh.LOAN.equals(tagEvent.getTag())) {
                    onTabSelected(0);
                } else if (EventTagPh.RE_APPLY.equals(tagEvent.getTag())) {
                    loanStatus = (String) tagEvent.getObject();
                    onTabSelected(0);
                } else if (EventTagPh.RE_LOAN.equals(tagEvent.getTag())) {
                    loanStatus = (String) tagEvent.getObject();
                    onTabSelected(0);
                } else if (EventTagPh.LOGOUT.equals(tagEvent.getTag())) {
                    loanStatus = "";
                    logout();
                } else if (EventTagPh.NOAPPLY.equals(tagEvent.getTag())) {
                    onTabSelected(0);
                } else if (EventTagPh.MESSAGE_UNREAD_COUNT.equals(tagEvent.getTag()) && minePhFragment != null) {
                    minePhFragment.setUnreadToView((Integer) tagEvent.getObject());
                } else if (EventTagPh.REPAYMENT.equals(tagEvent.getTag())) {
                    onTabSelected(1);
                }
            }
        });

        if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            showPhoneStateDialog();
        }

        if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            showLocationDialog();
        } else {
            ThreadUtil.submit(() -> uploadDeviceInfos());
            ThreadUtil.submit(() -> uploadAppInfo());
            ThreadUtil.submit(() -> uploadGpsInfo());
        }

        if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_NUMBERS) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            showPhoneNumberDialog();
        }

        NoticeMananger.pullUrgentNotices(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void logout() {
        PhApplication.getInstance().exitApplication();
        startActivity(new Intent(activity, LoginPhActivity.class));
    }

    private void showPhoneNumberDialog() {
        RequestPermissionDialog permissionDialog = new RequestPermissionDialog();
        permissionDialog.setData(R.drawable.icon_phone_number, R.string.phone_number_info_title, R.string.phone_number_info_content, R.string.phone_info_hint);
        permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick() {
                new RxPermissions(activity).requestEach(Manifest.permission.READ_PHONE_NUMBERS).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            permissionDialog.dismiss();
                            PermissionPhUtils.uploadDeviceInfo(activity, true, "READ_PHONE_NUMBERS");
                        } else if (!permission.shouldShowRequestPermissionRationale) {
                            getAppDetailSettingIntent();
                        }
                    }
                });

            }
        });
        permissionDialog.show(getSupportFragmentManager(), "3");

    }


    private void showLocationDialog() {
        RequestPermissionDialog locationDialog = new RequestPermissionDialog();
        locationDialog.setData(R.drawable.location_img, R.string.location_info_title, R.string.location_info_content, R.string.location_info_hint);
        locationDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @SuppressLint("CheckResult")
            @Override
            public void OnConfirmClick() {
                new RxPermissions(activity).requestEach(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            locationDialog.dismiss();
                            ThreadUtil.submit(() -> uploadDeviceInfos());
                            ThreadUtil.submit(() -> uploadAppInfo());
                            ThreadUtil.submit(() -> uploadGpsInfo());
                        } else if (!permission.shouldShowRequestPermissionRationale) {
                            getAppDetailSettingIntent();
                        }
                    }
                });
            }
        });
        locationDialog.show(getSupportFragmentManager(), "2");
    }

    private void showPhoneStateDialog() {
        RequestPermissionDialog phoneStateDialog = new RequestPermissionDialog();
        phoneStateDialog.setData(R.drawable.phone_img, R.string.phone_info_title, R.string.phone_info_content, R.string.phone_info_hint);
        phoneStateDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @SuppressLint("CheckResult")
            @Override
            public void OnConfirmClick() {
                new RxPermissions(activity).requestEach(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            phoneStateDialog.dismiss();
                            PermissionPhUtils.uploadDeviceInfo(activity, true, "READ_PHONE_STATE");
                        } else if (!permission.shouldShowRequestPermissionRationale) {
                            getAppDetailSettingIntent();
                        }
                    }
                });
            }
        });
        phoneStateDialog.show(getSupportFragmentManager(), "1");
    }

    private void initTabView() {
        if (TokenUtils.TokenCheck(this)) isResultFragment = true;
        loanFragment = new PreViewFragment();
//        previewFragment = new PreviewFragment();
        minePhFragment = new MinePhFragment();
        resultFragment = new LoanResultPhFragment();
        repayFragment = new RepayFragment();
        fragments = new Fragment[]{loanFragment, repayFragment, minePhFragment, resultFragment};
        imageButtons = new ImageView[4];
        imageButtons[0] = findViewById(R.id.iv_loan);
//        imageButtons[1] = findViewById(R.id.iv_loan);
        imageButtons[1] = findViewById(R.id.iv_repayment);
        imageButtons[2] = findViewById(R.id.iv_mine);
        imageButtons[0].setSelected(true);
        textViews = new TextView[4];
        textViews[0] = findViewById(R.id.tv_loan);
//        textViews[1] = findViewById(R.id.tv_loan);
        textViews[1] = findViewById(R.id.tv_repayment);
        textViews[2] = findViewById(R.id.tv_mine);
        textViews[0].setTextColor(getResources().getColor(R.color.blue));
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, previewFragment, "0")
                .add(R.id.fragment_container, loanFragment, "0")
                .add(R.id.fragment_container, repayFragment, "1")
                .add(R.id.fragment_container, minePhFragment, "2")
                .add(R.id.fragment_container, resultFragment, "3")
                .hide(minePhFragment).hide(repayFragment).hide(isResultFragment ? loanFragment : resultFragment).show(isResultFragment ? resultFragment : loanFragment).commit();
    }

    private void initViews() {
    }

    @Override
    public void onClick(View v) {

    }

    public String getLoanStatus() {
        return loanStatus;
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.message_tab:
                index = 3;
//                LiveDataBus.get().with("Privacy_Status").postValue(1);
                break;
            case R.id.repayment_tab:
                index = 1;
//                LiveDataBus.get().with("Privacy_Status").postValue(1);
                break;
            case R.id.mine_tab:
                index = 2;
////                LiveDataBus.get().with("Privacy_Status").postValue(11);
////                LiveDataBus.get().with("Privacy_Status", Integer.class).observe(this, new Observer<Integer>() {
////                    @Override
////                    public void onChanged(Integer index) {
////                        if (index == 10) {
////                            LiveDataBus.get().with("Privacy_Status").postValue(1);
////                        }else {
////                            LiveDataBus.get().with("Privacy_Status").postValue(10);
//                        }
//                    }
//                });
                String privacyText = getString(R.string.policy_privacy);
                Intent intent = new Intent(MainPhActivity.this, WebPhActivity.class);
                intent.putExtra(PhConstants.WEB_TITLE, privacyText);
                intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_POLICY);
                startActivity(intent);
                break;
        }
        onTabSelected(index);
    }

    public void onTabSelected(int index) {
        onTabSelected(index, null);
    }

    public void onTabSelected(int index, Object obj) {
        if (isResultFragment) {
            isResultFragment = false;
            currentTabIndex = 3;
        }
        if (index < 0) {
            index = 0;
        } else if (index > 3) {
            index = 3;
        }
        this.index = index;
        setTitle();
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            if (fragments[index] instanceof LoanResultPhFragment && (obj != null && obj instanceof CouponModel.Coupon)) {
                resultFragment.setCoupon((CouponModel.Coupon) obj);
            }
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        if (index < 3) {
            if (currentTabIndex == 3) {
                imageButtons[0].setSelected(false);
                textViews[0].setTextColor(getResources().getColor(R.color.grey_4));
            } else {
                imageButtons[currentTabIndex].setSelected(false);
                textViews[currentTabIndex].setTextColor(getResources().getColor(R.color.grey_4));
            }
            // 把当前tab设为选中状态
            imageButtons[index].setSelected(true);
            textViews[index].setTextColor(getResources().getColor(R.color.blue));
        } else {
            imageButtons[0].setSelected(true);
            imageButtons[1].setSelected(false);
            imageButtons[2].setSelected(false);
            textViews[0].setTextColor(getResources().getColor(R.color.blue));
            textViews[1].setTextColor(getResources().getColor(R.color.grey_4));
            textViews[2].setTextColor(getResources().getColor(R.color.grey_4));
        }
        currentTabIndex = index;
    }

    private void setTitle() {
        if (index == 0 || index == 3) {
            setTitle(R.string.app_name);
            if (TokenUtils.TokenCheck(this)) {
                getpHTitleBar().hideRight();
            } else {
                getpHTitleBar().setRightText(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainPhActivity.this, LoginPhActivity.class));
                    }
                });
            }
        }
//        else if (index == 3) {
//            setTitle(R.string.app_name);
//            getpHTitleBar().hideRight();
//        }
        else if (index == 2) {
            setTitle(R.string.app_name);
            getpHTitleBar().hideRight();
        } else if (index == 1) {
            setTitle(R.string.repay);
            getpHTitleBar().hideRight();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        if (intent != null) {
            int whichPage = intent.getIntExtra(PhConstants.EXTRA_WHICH_PAGE, 0);
            if (whichPage != currentTabIndex) {
                onTabSelected(whichPage);
            }
        }
    }

    @Override
    public void initTitleBar() {
        setTitle();
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
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
        moveTaskToBack(true);
    }
}
