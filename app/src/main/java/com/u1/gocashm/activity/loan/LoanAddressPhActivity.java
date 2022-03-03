package com.u1.gocashm.activity.loan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.appevents.AppEventsLogger;

import com.u1.gocashm.R;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.AppPhUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.PermissionPhUtils;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LoanInfoView;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;

import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


/**
 * Created by jishudiannao on 2018/9/5.
 */

public class LoanAddressPhActivity extends IdentityInfoActivity {

    @BindView(R.id.ll_info)
    LoanInfoView llPhInfo;
    @BindView(R.id.bt_continue)
    Button btPhContinue;
    @BindView(R.id.input_province)
    InputView input_provincePh;
    @BindView(R.id.input_city)
    InputView input_cityPh;
    @BindView(R.id.input_county)
    InputView input_countyPh;
    @BindView(R.id.input_detail)
    InputView input_detailPh;
    @BindView(R.id.input_duration)
    InputView input_durationPh;

    private SelectListPopupWindow popupWindow;
    private SharedPreferencesPhUtil spPhUtil;
    private String codePhProvince, codePhCity, codePhCounty, codePhDuration, provincePh, phCity, countyPh, detailPh, durationPh;
    private Disposable disposable;
    // 行为数据model
    private Activity activity;
    private RequestPermissionDialog permissionDialog;
    private SensorManager MyManage;
    private MySensorListener sensorListener;

    //通讯录 短信 权限申请次数
    private int applicationNumber = 1;

    private static final int SETTING_REQUESTCODE = 1001;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanaddress);
        ButterKnife.bind(this);
        activity = this;
        initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initData() {
        MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionDialog = new RequestPermissionDialog();
            permissionDialog.setData(R.drawable.location_img, R.string.location_info_title, R.string.location_info_content, R.string.location_info_hint);
            permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
                @Override
                public void OnConfirmClick() {
                    PermissionPhUtils.requestLocationPermission(activity);
                }
            });
            permissionDialog.show(getSupportFragmentManager(), "2");
        }
        spPhUtil = SharedPreferencesPhUtil.getInstance(this);
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (EventTagPh.APPLY_SUCCESS.equals(tagEvent.getTag()) || EventTagPh.LOAN.equals(tagEvent.getTag())) {
                    finish();
                }
            }
        });

        provincePh = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_PROVINCE);
        codePhProvince = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_PROVINCE_CODE);
        phCity = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_CITY);
        codePhCity = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_CITY_CODE);
        countyPh = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_COUNTY);
        codePhCounty = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_COUNTY_CODE);
        detailPh = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_DETAIL);
        durationPh = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_DURATION);
        codePhDuration = spPhUtil.getString(PhConstants.PhAddressInfo.ADDRESS_DURATION_CODE);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String adId = AppPhUtils.getAdvertisId(activity);
                    spPhUtil.saveString(PhConstants.ADVERTISID, adId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 销毁页面
        input_detailPh.inputClearFocus();
        MyManage.unregisterListener(sensorListener);
        uploadBehaviorRecords(
                input_provincePh,
                input_cityPh,
                input_countyPh,
                input_detailPh,
                input_durationPh);
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    private void initView() {
        input_provincePh.setText(provincePh);
        input_cityPh.setText(phCity);
        input_countyPh.setText(countyPh);
        input_detailPh.setText(detailPh);
        input_durationPh.setText(durationPh);
        llPhInfo.setStatus(2);

        getFocus(input_detailPh);
    }

    @OnClick({R.id.input_province, R.id.input_city, R.id.input_county, R.id.input_duration, R.id.bt_continue})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.input_province:
                showPopup("DISTRICT", "provincePh", input_provincePh.getLl());
                break;
            case R.id.input_city:
                if (codePhProvince != null)
                    showPopup(codePhProvince, "phCity", input_cityPh.getLl());
                else
                    ToastUtils.showShort(R.string.verify_city_empty);
                break;
            case R.id.input_county:
                if (codePhCity != null)
                    showPopup(codePhCity, "countyPh", input_countyPh.getLl());
                else
                    ToastUtils.showShort(R.string.verify_county_empty);
                break;
            case R.id.input_duration:
                showPopup("HOW_LONG_STAYING", "durationPh", input_durationPh.getLl());
                break;
            case R.id.bt_continue:

                                submit();
                                Log.e("权限", "同意");

                break;
        }
        input_detailPh.inputClearFocus();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SETTING_REQUESTCODE);
    }

    public void getFocus(final InputView input) {
        input.getEt_content().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                input.getFocus();
                return false;
            }
        });
    }

    private void showPopup(String code, final String type, final View view) {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
        else {
            NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
                @Override
                public void onNext(DictionaryPhModel dictionaryPhModel) {
                    super.onNext(dictionaryPhModel);
                    String modelCode = dictionaryPhModel.getCode();
                    if (CODE_SUCCESS.equals(modelCode)) {
                        popupWindow = new SelectListPopupWindow(LoanAddressPhActivity.this, dictionaryPhModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                                switch (type) {
                                    case "provincePh":
                                        // 选择居住地址所在省/市
                                        //BehaviorPhUtils.setChangeModify(bfModel, "P03_C08_S_HOMEPROVINCE", codePhProvince, dictionary.getCode());
                                        input_provincePh.setText(dictionary.getName());
                                        codePhProvince = dictionary.getCode();
                                        input_cityPh.setText("");
                                        codePhCity = "";
                                        input_countyPh.setText("");
                                        codePhCounty = "";
                                        break;
                                    case "phCity":
                                        // 选择居住地址所在区
                                        //BehaviorPhUtils.setChangeModify(bfModel, "P03_C09_S_HOMECITY", codePhCity, dictionary.getCode());
                                        input_cityPh.setText(dictionary.getName());
                                        codePhCity = dictionary.getCode();
                                        input_countyPh.setText("");
                                        codePhCounty = "";
                                        break;
                                    case "countyPh":
                                        // 选择居住地址所在街道
                                        //BehaviorPhUtils.setChangeModify(bfModel, "P03_C10_S_HOMECOUNTY", codePhCounty, dictionary.getCode());
                                        input_countyPh.setText(dictionary.getName());
                                        codePhCounty = dictionary.getCode();
                                        break;
                                    case "durationPh":
                                        // 居住时长
                                        //BehaviorPhUtils.setChangeModify(bfModel, "P03_C20_I_HOMEDURATION", codePhDuration, dictionary.getCode());
                                        input_durationPh.setText(dictionary.getName());
                                        codePhDuration = dictionary.getCode();
                                        break;
                                }
                                if (popupWindow != null) popupWindow.dismiss();

                            }
                        });
                        popupWindow.showPopupWindow(view);
                    }
                }
            });
        }
    }

    private void submit() {
        //点击按钮
        BehaviorRecord pNextRecord = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P03_C_Next");
        provincePh = input_provincePh.getText();
        phCity = input_cityPh.getText();
        countyPh = input_countyPh.getText();
        detailPh = input_detailPh.getText();
        durationPh = input_durationPh.getText();

        if (TextUtils.isEmpty(provincePh)) {
            input_provincePh.setVerify(R.string.verify_province);
            return;
        } else {
            input_provincePh.setVerify("");
        }
        if (TextUtils.isEmpty(phCity)) {
            input_cityPh.setVerify(R.string.verify_city);
            return;
        } else {
            input_cityPh.setVerify("");
        }
  /*      if (TextUtils.isEmpty(countyPh)) {
            input_countyPh.setVerify(R.string.verify_county);
            return;
        } else {
            input_countyPh.setVerify("");
        }*/
        if (TextUtils.isEmpty(detailPh)) {
            input_detailPh.setVerify(R.string.verify_detail);
            return;
        } else if (PhUtils.isNumber(detailPh)) {
            input_detailPh.setVerify("not all numbers");
            return;
        } else {
            input_detailPh.setVerify("");
        }
        if (TextUtils.isEmpty(durationPh)) {
            input_durationPh.setVerify(R.string.verify_duration);
            return;
        } else {
            input_durationPh.setVerify("");
        }
        //BehaviorPhUtils.setSensorValue(bfModel, "P03_C20_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        ApplyReqPhModel applyPhReqModel = new ApplyReqPhModel();
        applyPhReqModel.setProvince(codePhProvince);
        applyPhReqModel.setCity(codePhCity);
        applyPhReqModel.setDistrict(codePhCounty);
        applyPhReqModel.setAddress(detailPh);
        applyPhReqModel.setLive_length(codePhDuration);
        applyPhReqModel.setToken(TokenUtils.getToken(activity));
        applyPhReqModel.setStep_name("addressInfo");
        NetworkPhRequest.applyAddressinfo(applyPhReqModel, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                pNextRecord.setEndTime();
                if (CODE_SUCCESS.equals(basePhModel.getCode())) {
                    AppEventsLogger.newLogger(activity).logEvent(PhConstants.FaceBookEvent.EVENT_ADDRESS);
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_ADDRESS, activity, TokenUtils.getOrderId(activity));
                    NetworkPhRequest.getUserStep(applyPhReqModel, new PhSubscriber<>());
                    saveData();
                    switchStep(JobInfoPhActivity.class);
                    finish();
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

    private void saveData() {
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_PROVINCE, provincePh);
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_CITY, phCity);
        // spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_COUNTY, countyPh);
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_PROVINCE_CODE, codePhProvince);
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_CITY_CODE, codePhCity);
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_COUNTY_CODE, codePhCounty);
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_DETAIL, detailPh);
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_DURATION, durationPh);
        spPhUtil.saveString(PhConstants.PhAddressInfo.ADDRESS_DURATION_CODE, codePhDuration);
        spPhUtil.saveBoolean(PhConstants.FINISH_STATUS.ADDRESS_INFO, true);
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.info_address);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

}
