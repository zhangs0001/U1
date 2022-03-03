package com.u1.gocashm.activity.loan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.appevents.AppEventsLogger;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.AppInfoReqModel;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.request.RecordPhModel;
import com.u1.gocashm.model.response.ApplyModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.Customer;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.model.response.GoogleGpsPhModel;
import com.u1.gocashm.model.response.UserInfoDetailModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AFPhListener;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.ApkTool;
import com.u1.gocashm.util.AppPhUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.FireBaseUtil;
import com.u1.gocashm.util.GaodeLocation;
import com.u1.gocashm.util.GsonPhHelper;
import com.u1.gocashm.util.LoanInfoPhUtils;
import com.u1.gocashm.util.PermissionPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.WordPhUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.util.listener.MyPhoneStateListener;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.util.model.DeviceInfo;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LoanInfoView;
import com.u1.gocashm.view.dialog.LoanAgreementDialog;
import com.u1.gocashm.view.dialog.ModifyAmountDialog;
import com.u1.gocashm.view.dialog.NoCardHintDialog;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/9/12 0012 上午 10:32
 */
public class ContinuedLoanPhActivity extends BasePhTitleBarActivity implements GaodeLocation.LocationResultListener{
    private static final int PICK_CONTACT = 100;
    private static final String TAG = ContinuedLoanPhActivity.class.getSimpleName();
    @BindView(R.id.ll_info)
    LoanInfoView llPhInfo;
    @BindView(R.id.tv_user_name)
    TextView tvPhUserName;
    @BindView(R.id.tv_agreement)
    TextView tvPhAgreement;
    @BindView(R.id.input_bank_name)
    InputView inputPhBankName;
    @BindView(R.id.input_bank_num)
    InputView inputPhBankNum;
    @BindView(R.id.input_relatives_name)
    InputView inputPhRelativesName;
    @BindView(R.id.input_relatives_phone)
    InputView inputPhRelativesPhone;
    @BindView(R.id.input_relationship)
    InputView inputPhRelationship;
    @BindView(R.id.rb_bank)
    RadioButton rbBank;
    @BindView(R.id.rb_cash)
    RadioButton rbCash;
    @BindView(R.id.rg_select_pay)
    RadioGroup rgSelectPay;
    @BindView(R.id.user_bank_info_layout)
    LinearLayout userBankInfoLayout;
    @BindView(R.id.input_agency_name)
    InputView inputAgencyName;
    @BindView(R.id.input_agency_fee)
    InputView inputAgencyFee;
    @BindView(R.id.user_agency_info_layout)
    LinearLayout userAgencyInfoLayout;
    @BindView(R.id.user_name_layout)
    LinearLayout userNameLayout;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_middle_name)
    EditText etMiddleName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.input_name_layout)
    LinearLayout inputNameLayout;
    @BindView(R.id.checkbox_agreement)
    CheckBox checkboxAgreement;
    @BindView(R.id.tv_agreement_hint)
    TextView tvAgreementHint;

    private FragmentActivity phActivity;

    private SelectListPopupWindow phPopupWindow;

    private String bankPhName;
    private String bankPhCode;
    private String userPhName;
    private String userPhPhone;
    private String bankCardPhNum;
    private SharedPreferencesPhUtil spPhUtil;
    private String relationshipPh;
    private String relationshipCodePh;
    private String relativesPhonePh;
    private Cursor cursor;
    private String relativesNamePh;
    // 行为数据model
    private BehaviorPhReqModel bfModel;
    private Activity activity;
    private boolean isAgency;
    private String agencyName;
    private String agencyCode;
    private String lastName;
    private String firstName;
    private String middleName;

    private MySensorListener sensorListener;
    private SensorManager MyManage; //新建sensor的管理器
    private String[] gps;
    private LoanAgreementDialog agreementDialog;
    private RecordPhModel recordPhModel;
    private ModifyAmountDialog modifyAmountDialog;
    private Integer amount;
    private Integer term;
    private Integer maxAmount;
    private String productCode;
    private Integer initAmount;
    private Integer initTerm;
    private IntentFilter ifilter;
    private boolean isCharging;
    private int level;
    private DeviceInfo deviceInfo;
    private RequestPermissionDialog permissionDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_loan);
        ButterKnife.bind(this);
        activity = this;
        phActivity = this;
        initData();
        initListener();
    }

    private void initListener() {
        rgSelectPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_bank) {
                    BehaviorPhUtils.setChangeModify(bfModel, "P07_C02_PAY_TYPE", "no", "yes");
                    userAgencyInfoLayout.setVisibility(View.GONE);
                    userBankInfoLayout.setVisibility(View.VISIBLE);
                    isAgency = false;
                } else {
                    NoCardHintDialog dialog = new NoCardHintDialog();
                    dialog.setOnClick(new NoCardHintDialog.OnClick() {
                        @Override
                        public void OnClick() {
                            rbBank.setChecked(true);
                        }
                    });
                    dialog.setOnCancelClick(new NoCardHintDialog.OnCancelClick() {
                        @Override
                        public void OnCancelClick() {
                            BehaviorPhUtils.setChangeModify(bfModel, "P07_C02_PAY_TYPE", "yes", "no");
                        }
                    });
                    dialog.show(getSupportFragmentManager(), "1");
                    userAgencyInfoLayout.setVisibility(View.VISIBLE);
                    userBankInfoLayout.setVisibility(View.GONE);
                    isAgency = true;
                }
            }
        });
        rbBank.setChecked(true);
        inputAgencyFee.setEnabled(false);
        inputPhBankNum.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s == null || s.length() == 0) {
                    return;
                }
                if (TextUtils.isEmpty(bankPhName)) {
                    return;
                }
                checkBankCardNum(bankPhName, s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkboxAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tvAgreementHint.setVisibility(View.GONE);
                }
            }
        });
        addNameInputListener(etFirstName);
        addNameInputListener(etMiddleName);
        addNameInputListener(etLastName);
    }

    private void addNameInputListener(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String inputString = s.toString();
                    String firstLetterCapString = WordPhUtil.capitalize(inputString);
                    if (!firstLetterCapString.equals(inputString)) {
                        editText.setText(firstLetterCapString);
                        editText.setSelection(editText.getText().length());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 启动续贷
        initView();
    }

    private void initView() {
        NetworkPhRequest.getPayType(new PhSubscriber<BasePhModel>() {
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
                    String type = (String) basePhModel.getData();
                    if ("Y".equals(type)) {
                        rgSelectPay.setVisibility(View.VISIBLE);
                    } else {
                        rgSelectPay.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
        gps = new String[1];
        NetworkPhRequest.getGoogleGps(new PhSubscriber<GoogleGpsPhModel>() {
            @Override
            public void onNext(GoogleGpsPhModel gpsModel) {
                super.onNext(gpsModel);
                GoogleGpsPhModel.LocationBean location1 = gpsModel.getLocation();
                if (location1 != null) {
                    gps[0] = String.valueOf(location1.getLat()) + "," + String.valueOf(location1.getLng());
                }
            }

        });
        // 银行卡号
        AFPhListener.getInstance().addEditTextListener(inputPhBankNum.getEt_content(), bfModel, "P07_C04_I_BANKCARDNO");
        // 联系人
        AFPhListener.getInstance().addEditTextListener(inputPhRelativesName.getEt_content(), bfModel, "P07_C05_I_CONTACTNAME");

        getFocus(inputPhBankNum);
        getFocus(inputPhRelativesName);

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

    private void initData() {
        initAmount = LoanInfoPhUtils.getAmount();
        initTerm = LoanInfoPhUtils.getApplyTerm();
        bfModel = BehaviorPhUtils.setEnterPageModify("P07_C00", activity);
        spPhUtil = SharedPreferencesPhUtil.getInstance(phActivity);
        firstName = spPhUtil.getString(PhConstants.PhUser.FIRST_NAME);
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        MyPhoneStateListener myphonelister = new MyPhoneStateListener();
        TelephonyManager Tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Tel.listen(myphonelister, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS); //实现监听
        deviceInfo = new DeviceInfo();
        MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        Customer customer = spPhUtil.getCustomer();
        if (customer != null) {
            if (TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(TokenUtils.getToken(activity))) {
                inputNameLayout.setVisibility(View.VISIBLE);
                userNameLayout.setVisibility(View.GONE);
            } else {
                inputNameLayout.setVisibility(View.GONE);
                userNameLayout.setVisibility(View.VISIBLE);
            }
            userPhName = customer.getName();
            userPhPhone = customer.getPhone();
            tvPhUserName.setText(userPhName);
        } else {
            NetworkPhRequest.getUserInfoDetail(new PhSubscriber<UserInfoDetailModel>() {

                @Override
                public void onStart() {
                    super.onStart();
                    showLoadingDialog();
                }

                @Override
                public void onNext(UserInfoDetailModel userInfoDetailModel) {
                    super.onNext(userInfoDetailModel);
                    closeLoadingDialog();
                    String code = userInfoDetailModel.getCode();
                    if (CODE_SUCCESS.equals(code)) {
                        SharedPreferencesPhUtil.getInstance(phActivity).setUserInfo(userInfoDetailModel.getData());
                        userPhName = userInfoDetailModel.getData().getName();
                        userPhPhone = userInfoDetailModel.getData().getPhone();
                        tvPhUserName.setText(userPhName);
                    } else {
                        showToast(userInfoDetailModel.getMsg());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    Log.e(TAG, e.getMessage());
                    closeLoadingDialog();
                }
            });
        }
    }

    @Override
    public void initTitleBar() {
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        setTitleBack(R.string.pay_info_title);
        llPhInfo.setScheduleLayoutVisible(false);
    }

    @OnClick({R.id.input_bank_name, R.id.tv_submit, R.id.input_relatives_phone, R.id.input_relationship, R.id.agreement_layout, R.id.input_agency_name})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.input_bank_name:
                selectBank();
                break;
            case R.id.input_agency_name:
                selectAgency();
                break;
            case R.id.input_relatives_phone:
                selectRelativesPhone();
                break;
            case R.id.input_relationship:
                selectRelationship();
                break;
            case R.id.agreement_layout:
                toAgreement(false, null);
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
        inputPhBankNum.inputClearFocus();
        inputPhRelativesName.inputClearFocus();
    }

    private void submit() {
        bankCardPhNum = inputPhBankNum.getText();
        relativesNamePh = inputPhRelativesName.getText();
        firstName = etFirstName.getText().toString().trim();
        middleName = etMiddleName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();

        if (inputNameLayout.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                showToast(R.string.input_name);
                return;
            }
            if (!StringUtils.equalsIgnoreCase(userPhName.replace(" ", ""), (firstName + middleName + lastName).replace(" ", ""))) {
                showToast(getString(R.string.a1));
                return;
            }
        }
        if (isAgency) {
            if (TextUtils.isEmpty(agencyName)) {
                inputAgencyName.setVerify(R.string.please_select);
                return;
            } else {
                inputAgencyName.setVerify("");
            }
        } else {
            if (TextUtils.isEmpty(bankPhName)) {
                inputPhBankName.setVerify(R.string.verify_bankname);
                return;
            } else {
                inputPhBankName.setVerify("");
            }
            if (TextUtils.isEmpty(bankCardPhNum)) {
                inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                return;
            } else {
                inputPhBankNum.setVerify("");
            }
            if (checkBankCardNum(bankPhName, bankCardPhNum)) {
                return;
            } else {
                inputPhBankNum.setVerify("");
            }
        }
        if (TextUtils.isEmpty(relativesNamePh)) {
            inputPhRelativesName.setVerify(R.string.verify_relativesname);
            return;
        } else {
            inputPhRelativesName.setVerify("");
        }
        if (TextUtils.isEmpty(relativesPhonePh)) {
            inputPhRelativesPhone.setVerify(R.string.verify_relativesphone);
            return;
        } else {
            inputPhRelativesPhone.setVerify("");
        }
//        if (!VerifyUtil.checkPhoneForPh(relativesPhonePh)) {
//            inputPhRelativesPhone.setVerify(R.string.error_phone_no);
//            return;
//        } else {
//            inputPhRelativesPhone.setVerify("");
//        }
        if (TextUtils.isEmpty(relationshipPh)) {
            inputPhRelationship.setVerify(R.string.verify_relationship);
            return;
        } else {
            inputPhRelationship.setVerify("");
        }

        if (!checkboxAgreement.isChecked()) {
            tvAgreementHint.setVisibility(View.VISIBLE);
            return;
        }
        final ApplyReqPhModel applyPh = new ApplyReqPhModel();
        applyPh.setApplyId(LoanInfoPhUtils.getApplyId());
        applyPh.setApplyAmount(amount);
        applyPh.setLoanTerms(term);
        applyPh.setMaxApplyAmount(maxAmount);
        applyPh.setProductCode(productCode);
        if (inputNameLayout.getVisibility() == View.VISIBLE) {
            applyPh.setFirstname(firstName);
            applyPh.setMiddlename(middleName);
            applyPh.setLastname(lastName);
        }
        if (isAgency) {
            applyPh.setInstitution_name(agencyCode);
            applyPh.setOnlinePay("N");
        } else {
            applyPh.setBank_code(bankPhCode);
            applyPh.setBank_no(bankCardPhNum);
        }
        applyPh.setTelephone(userPhPhone);
        applyPh.setContactPhone(relativesPhonePh);
        applyPh.setContactRelationCode(relationshipCodePh);
        applyPh.setContactName(relativesNamePh);
        applyPh.setSource("APP");
        applyPh.setChannel("UPESO");
        applyPh.setUserId(TokenUtils.getUserId(activity));
        String gpsInfo = AppPhUtils.getGpsInfo();
        if (!"0,0".equals(gpsInfo)) {
            applyPh.setGps(gpsInfo);
            next(applyPh);
        } else {
            applyPh.setGps(gps[0]);
            next(applyPh);
        }
    }

    private void next(final ApplyReqPhModel applyPh) {
        NetworkPhRequest.continuedLoanSubmit(applyPh, new PhSubscriber<ApplyModel>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(ApplyModel applyModel) {
                super.onNext(applyModel);
                closeLoadingDialog();
                String code = applyModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    AppEventsLogger.newLogger(activity).logEvent(PhConstants.FaceBookEvent.EVENT_FIRST_SIGN);
                    // 保存行为数据
                    ApplyModel.ApplyBean body = applyModel.getData();
                    BehaviorPhUtils.saveBehaviorReqModel(bfModel);
                    RxBus.getDefault().post(new TagEvent(EventTagPh.APPLY_SUCCESS));
                    deviceInfo.setBatteryStatus(isCharging ? "Charging" : "unCharging");
                    deviceInfo.setBatteryPower(String.valueOf(level));
                    deviceInfo.setSignalStrength(spPhUtil.getString(PhConstants.PHONE_DBM));
//                    AppPhUtils.uploadDeviceInfo(TokenUtils.getToken(activity), deviceInfo, phActivity, AppPhUtils.getScreenWidthAndHeight(phActivity)
//                            , sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
                    AppPhUtils.updateContactList(TokenUtils.getToken(activity));
                    uploadJpushInfo(body.getApply().getIdcard(), String.valueOf(body.getApply().getCustomerId()));
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_FIRST_SIGN, activity, TokenUtils.getOrderId(activity));
                    FireBaseUtil.fireBaseEvent(activity, PhConstants.FaceBookEvent.EVENT_FIRST_SIGN, body.getApply().getApplyId());
                    uploadAppInfo();
                    finish();
                } else {
                    showToast(applyModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                Log.e(TAG, e.getMessage());
                showToast(R.string.error_request_fail);
            }
        });
    }

    private void showModifyAmountDialog(ApplyModel.ApplyBean body, boolean isUp, final ApplyReqPhModel apply) {
        modifyAmountDialog = new ModifyAmountDialog();
        modifyAmountDialog.setData(body.getProduct(), isUp ? ModifyAmountDialog.UP_AMOUNT : ModifyAmountDialog.DOWN_AMOUNT);
        modifyAmountDialog.setConfirmListener(new ModifyAmountDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick() {
                amount = modifyAmountDialog.getAmount();
                term = modifyAmountDialog.getTerm();
                maxAmount = modifyAmountDialog.getMaxAmount();
                productCode = modifyAmountDialog.getProductCode();
                apply.setApplyAmount(amount);
                apply.setLoanTerms(term);
                apply.setProductCode(productCode);
                apply.setMaxApplyAmount(maxAmount);
                confirmAmount(true, apply);
            }
        });
        modifyAmountDialog.setCancelListener(new ModifyAmountDialog.OnCancelClick() {
            @Override
            public void OnCancelClick() {
                maxAmount = modifyAmountDialog.getMaxAmount();
                productCode = modifyAmountDialog.getProductCode();
                apply.setMaxApplyAmount(maxAmount);
                apply.setProductCode(productCode);
                next(apply);
            }
        });
        modifyAmountDialog.show(getSupportFragmentManager(), "2");
    }

    private void confirmAmount(final boolean showDialog, final ApplyReqPhModel apply) {
        NetworkPhRequest.updateAmount(apply, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel baseModel) {
                super.onNext(baseModel);
                closeLoadingDialog();
                if (CODE_SUCCESS.equals(baseModel.getCode())) {
                    if (showDialog) {
                        toAgreement(true, apply);
                    }
                } else {
                    showToast(baseModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }


    private void uploadAppInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppInfoReqModel infoReqBean = new AppInfoReqModel();
                infoReqBean.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
                infoReqBean.setAppPhInfos(ApkTool.getInstallAppList(getPackageManager(), activity));
                Map<String, Object> maps = new HashMap<>();
                maps.put("data_json", GsonPhHelper.getGson().toJson(infoReqBean.getAppPhInfos()));
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
        }).start();
    }


    private void selectBank() {
        showPopupWindow("BANK", "bank", inputPhBankName.getTv_content());
    }


    private void selectAgency() {
        showPopupWindow("PAY_ORG", "agency", inputAgencyName.getTv_content());
    }


    private void toAgreement(final boolean isModify, final ApplyReqPhModel apply) {
        String accessToken = spPhUtil.getString(PhConstants.PhUser.ACCESS_TOKEN);
        long applyId = spPhUtil.getLong(PhConstants.PhUser.APPLY_ID, 0L);
        if (applyId == 0) {
            return;
        }
        NetworkPhRequest.getContractPreview(String.valueOf(term), String.valueOf(amount), accessToken, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                showToast(getString(R.string.error_request_fail));
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                String code = basePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    agreementDialog = new LoanAgreementDialog();
                    agreementDialog.setLoanContractUrl(basePhModel.getData().toString());
                    agreementDialog.setOnClick(new LoanAgreementDialog.OnClick() {
                        @Override
                        public void OnClick() {
                            if (isModify) {
                                next(apply);
                            } else {
                                getAgreementBehavior(true);

                            }
                        }
                    });
                    agreementDialog.setOnCancelClick(new LoanAgreementDialog.OnCancelClick() {
                        @Override
                        public void OnCancelClick() {
                            if (!isModify) {
                                getAgreementBehavior(false);
                            } else {
                                apply.setApplyAmount(initAmount);
                                apply.setLoanTerms(initTerm);
                                confirmAmount(false, apply);
                            }
                        }
                    });
                    agreementDialog.show(getSupportFragmentManager(), "1");
                } else {
                    showToast(basePhModel.getMsg());
                }
            }
        });
    }

    private void getAgreementBehavior(boolean isAgree) {
        recordPhModel = new RecordPhModel();
        recordPhModel.setId("P06_C07_B_CONTRACT");
        recordPhModel.setNewValue(isAgree ? "Agree" : "Disagree");
        recordPhModel.setStartTime(BehaviorPhUtils.format.format(new Date(agreementDialog.getInitTime())));
        recordPhModel.setEndTime(BehaviorPhUtils.format.format(new Date(agreementDialog.getEndTime())));
        bfModel.getRecords().add(recordPhModel);
        checkboxAgreement.setChecked(isAgree);
    }

    private void selectRelationship() {
        showPopupWindow("RELATIONSHIP", "relationshipPh", inputPhRelationship.getTv_content());
    }

    private void selectRelativesPhone() {
        permissionDialog = new RequestPermissionDialog();
        permissionDialog.setData(R.drawable.contact_img, R.string.contacts_info_title, R.string.contacts_info_content, R.string.contacts_info_hint);
        permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @SuppressLint("CheckResult")
            @Override
            public void OnConfirmClick() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    new RxPermissions(activity).requestEach(Manifest.permission.READ_CONTACTS).subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                permissionDialog.dismiss();
                                PermissionPhUtils.uploadDeviceInfo(activity, true, "READ_CONTACTS");
                                onContactApp();
                            } else if (!permission.shouldShowRequestPermissionRationale) {
                                getAppDetailSettingIntent();
                            }
                        }
                    });
                } else {
                    onContactApp();
                }
            }
        });
        permissionDialog.show(getSupportFragmentManager(), "2");
    }

    private void onContactApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK && data != null) {
            try {
                getPhoneNum(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getPhoneNum(Intent data) {
        Uri contactData = data.getData();
        cursor = managedQuery(contactData, null, null, null, null);
        boolean isOpen = false;
        try {
            isOpen = cursor != null && cursor.moveToFirst();
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }
        if (isOpen) {
            int hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone > 0 && !TextUtils.isEmpty(contactId)) {
                try {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones != null && phones.moveToNext()) {
                        relativesPhonePh = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    if (null != phones && !phones.isClosed()) {
                        phones.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!TextUtils.isEmpty(relativesPhonePh)) {
            relativesPhonePh = relativesPhonePh.replaceAll("[^0-9]", "");
            // 联系人手机号
            BehaviorPhUtils.setChangeModify(bfModel, "P07_C06_I_CONTACTPHONE", relativesPhonePh, inputPhRelativesPhone.getText());
            inputPhRelativesPhone.getTv_content().setText(relativesPhonePh);
        }
    }


    private void showPopupWindow(String code, final String value, final TextView textView) {
        NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
            @Override
            public void onNext(DictionaryPhModel dictionaryPhModel) {
                super.onNext(dictionaryPhModel);
                String modelCode = dictionaryPhModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    phPopupWindow = new SelectListPopupWindow(phActivity, dictionaryPhModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                            if (dictionary != null) {
                                switch (value) {
                                    case "bank":
                                        // 银行名称
                                        BehaviorPhUtils.setChangeModify(bfModel, "P07_C01_S_BANKNAME", bankPhCode, dictionary.getCode());
                                        bankPhName = dictionary.getName();
                                        bankPhCode = dictionary.getCode();
                                        break;
                                    case "relationshipPh":
                                        // 联系人关系
                                        BehaviorPhUtils.setChangeModify(bfModel, "P07_C06_I_RELATIONCODE", relationshipCodePh, dictionary.getCode());
                                        relationshipPh = dictionary.getName();
                                        relationshipCodePh = dictionary.getCode();
                                        break;
                                    case "agency":
                                        agencyName = dictionary.getName();
                                        agencyCode = dictionary.getCode();
//                                        NetworkPhRequest.getAgencyFee(agencyCode, spPhUtil.getLong(PhConstants.PhUser.APPLY_ID, 0L), new PhSubscriber<BasePhModel>() {
//                                            @Override
//                                            public void onStart() {
//                                                super.onStart();
//                                                showLoadingDialog();
//                                            }
//
//                                            @Override
//                                            public void onNext(BasePhModel basePhModel) {
//                                                super.onNext(basePhModel);
//                                                closeLoadingDialog();
//                                                Status modelStatus = basePhModel.getStatus();
//                                                if (modelStatus != null && "000".equals(modelStatus.getCode())) {
//                                                    BigDecimal fee = new BigDecimal(String.valueOf(basePhModel.getBody()));
//                                                    inputAgencyFee.setText(fee.intValue() + "₱");
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onError(Throwable e) {
//                                                super.onError(e);
//                                                closeLoadingDialog();
//                                            }
//                                        });
                                        break;
                                }
                                textView.setText(dictionary.getName());
                                if (phPopupWindow != null) phPopupWindow.dismiss();
                            }
                        }
                    });
                    if (phPopupWindow.isShowing())
                        phPopupWindow.dismiss();
                    else {
                        phPopupWindow.showPopupWindow(textView);
                    }
                }
            }
        });

    }

    private boolean checkBankCardNum(String bank, String bankNum) {
        switch (bank) {
            case "BPI":
            case "Land Bank":
                if (!VerifyUtil.checkBankCardWith10(bankNum)) {
                    inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                    return true;
                } else {
                    inputPhBankNum.setVerify("");
                }
                break;
            case "UCPB":
            case "Union Bank":
            case "Eastwest Bank":
            case "PNB":
            case "Asia United Bank":
                if (!VerifyUtil.checkBankCardWith12(bankNum)) {
                    inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                    return true;
                } else {
                    inputPhBankNum.setVerify("");
                }
                break;
            case "Metrobank":
            case "Security Bank":
                if (!VerifyUtil.checkBankCardWith13(bankNum)) {
                    inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                    return true;
                } else {
                    inputPhBankNum.setVerify("");
                }
                break;
            case "BPI Family Savings Bank":
                if (!VerifyUtil.checkBankCardWith102(bankNum)) {
                    inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                    return true;
                } else {
                    inputPhBankNum.setVerify("");
                }
                break;
            case "RCBC":
                if (!(VerifyUtil.checkBankCardWith10(bankNum) || VerifyUtil.checkBankCardWith16(bankNum))) {
                    inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                    return true;
                } else {
                    inputPhBankNum.setVerify("");
                }
                break;
            case "China Bank":
                if (bankNum.startsWith("6")) {
                    inputPhBankNum.setVerify(R.string.verify_bankcardnum_china_bank);
                    return true;
                } else {
                    if (!VerifyUtil.checkBankCardForChinaBank(bankNum)) {
                        inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                        return true;
                    } else {
                        inputPhBankNum.setVerify("");
                    }
                }
                break;
            case "BDO(Banco De Oro)":
                if (!(VerifyUtil.checkBankCardWith10(bankNum) || VerifyUtil.checkBankCardWith12(bankNum))) {
                    inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                    return true;
                } else {
                    inputPhBankNum.setVerify("");
                }
                break;
            default:
                return false;
        }
        return false;
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
    protected void onDestroy() {
        MyManage.unregisterListener(sensorListener);
        inputPhBankNum.inputClearFocus();
        inputPhBankNum.inputClearFocus();
        BehaviorPhUtils.setDestroyModify(bfModel, "P07_C99");
        try {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onLocationResult(String json, JSONObject jsonObject) {
        
    }
}
