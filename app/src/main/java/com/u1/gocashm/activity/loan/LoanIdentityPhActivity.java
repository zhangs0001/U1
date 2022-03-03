package com.u1.gocashm.activity.loan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.u1.gocashm.R;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.request.FacebookReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.GsonPhHelper;
import com.u1.gocashm.util.LoanInfoPhUtils;
import com.u1.gocashm.util.PermissionPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.util.model.FacebookModel;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LoanInfoView;
import com.u1.gocashm.view.dialog.DatePickerPhDialog;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by jishudiannao on 2018/9/3.
 */

public class LoanIdentityPhActivity extends IdentityInfoActivity {

    @BindView(R.id.ll_info)
    LoanInfoView llPhInfo;
    @BindView(R.id.rg_sex)
    RadioGroup rgPhSex;
    @BindView(R.id.bt_id_continue)
    Button btPhContinue;
    @BindView(R.id.rb_male)
    RadioButton rbPhMale;
    @BindView(R.id.rb_female)
    RadioButton rbPhFemale;
    @BindView(R.id.input_birthday)
    InputView inputPhBirthday;
    @BindView(R.id.input_marital)
    InputView inputPhMarital;
    @BindView(R.id.input_email)
    InputView inputPhEmail;
    @BindView(R.id.tv_sex_tip)
    TextView tv_sex_tipPh;
    @BindView(R.id.tv_id_sex)
    TextView tvPhSex;
    @BindView(R.id.input_education)
    InputView inputEducation;
    @BindView(R.id.input_religion)
    InputView inputReligion;
    @BindView(R.id.input_first_name)
    InputView inputFirstName;
    @BindView(R.id.input_middle_name)
    InputView inputMiddleName;
    @BindView(R.id.input_last_name)
    InputView inputLastName;
    @BindView(R.id.iv_fb_login_success)
    ImageView ivFbLoginSuccess;
    @BindView(R.id.iv_fb_login)
    ImageView ivFbLogin;
    @BindView(R.id.tv_fb_login_success)
    TextView tvFbLoginSuccess;
    @BindView(R.id.input_bank_count)
    InputView inputBankCount;

    @BindView(R.id.et_input_facebook_link)
    EditText et_input_facebook_link;


    private SelectListPopupWindow popupWindow;
    private SelectListPopupWindow automaticInputEmailWindow;
    private SharedPreferencesPhUtil spPhUtil;
    //    private String cardPh;
    private String phonePh, sexPh, maritalPh, codeMaritalPh, emailPh, birthdayPh;
    private String education;
    private String educationCode;
    //    private String religion;
//    private String religionCode;
    private String bankAccount;
    private List<Integer> list;
    private Disposable disposable;
    private Activity activity;
    // 行为数据model
    private String MALE;
    private String FEMALE;
    private RequestPermissionDialog permissionDialog;
    private SensorManager MyManage;
    private MySensorListener sensorListener;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private boolean isFbLogin;
    private FacebookModel facebookModel;
    private File imageFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanidentity);
        ButterKnife.bind(this);
        activity = this;
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 启动身份信息填写页
        initListener();
    }

    private void initListener() {
        getFocus(inputPhEmail);
    }

    private void initData() {
        MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        spPhUtil = SharedPreferencesPhUtil.getInstance(this);
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (EventTagPh.APPLY_SUCCESS.equals(tagEvent.getTag()) || EventTagPh.LOAN.equals(tagEvent.getTag())) {
                    finish();
                }
            }
        });
        firstName = spPhUtil.getString(PhConstants.PhIdentityInfo.FIRST_NAME);
//        middleName = spPhUtil.getString(PhConstants.PhIdentityInfo.MIDDLE_NAME);
//        lastName = spPhUtil.getString(PhConstants.PhIdentityInfo.LAST_NAME);
        sexPh = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_SEX);
        maritalPh = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_MARITAL);
        codeMaritalPh = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_CODE_MARITAL);
        emailPh = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_EMAIL);
        list = spPhUtil.getListInteger(PhConstants.PhIdentityInfo.IDENTITY_LIST);
        birthdayPh = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_BIRTHDAY);
        phonePh = LoanInfoPhUtils.getApplyPhone();
        education = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_EDUCATION);
        educationCode = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_EDUCATION_CODE);
        bankAccount = spPhUtil.getString(PhConstants.PhIdentityInfo.BANK_ACCOUNT);
//            religion = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_RELIGION);
//            religionCode = spPhUtil.getString(PhConstants.PhIdentityInfo.IDENTITY_RELIGION_CODE);
        isFbLogin = spPhUtil.getBoolean(PhConstants.IS_FB_LOGIN);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        accessToken = loginResult.getAccessToken();
                        isFbLogin = accessToken != null && !accessToken.isExpired();
                        if (isFbLogin) {
                            ivFbLoginSuccess.setVisibility(View.VISIBLE);
                            tvFbLoginSuccess.setVisibility(View.VISIBLE);
                        } else {
                            ivFbLoginSuccess.setVisibility(View.GONE);
                            tvFbLoginSuccess.setVisibility(View.GONE);
                        }
                        getFacebookInfo(accessToken);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("111", exception.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        inputPhEmail.inputClearFocus();
        uploadBehaviorRecords(
                inputPhBirthday,
                inputPhMarital,
                inputPhEmail,
                inputEducation,
                inputFirstName,
//todo

//                inputMiddleName,
//                inputLastName,
                inputBankCount);

        MyManage.unregisterListener(sensorListener);
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    private void initView() {
        MALE = getString(R.string.a6);
        FEMALE = getString(R.string.a7);
        if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            permissionDialog = new RequestPermissionDialog();
            permissionDialog.setData(R.drawable.phone_img, R.string.phone_info_title, R.string.phone_info_content, R.string.phone_info_hint);
            permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
                @Override
                public void OnConfirmClick() {
                    PermissionPhUtils.requestPhoneStatePermission(activity);
                }
            });
            permissionDialog.show(getSupportFragmentManager(), "1");
        }
        tvPhSex.setText(getFirstWordRed(getString(R.string.sex)));
        inputFirstName.setText(firstName);
//        inputMiddleName.setText(middleName);
//        inputLastName.setText(lastName);
        inputPhBirthday.setText(birthdayPh);
        if (sexPh.equals(MALE) || sexPh.equals("男"))
            rbPhMale.setChecked(true);
        else if (sexPh.equals(FEMALE) || sexPh.equals("女"))
            rbPhFemale.setChecked(true);
        inputPhMarital.setText(maritalPh);
        inputEducation.setText(education);
        inputBankCount.setText(bankAccount);
//        inputReligion.setText(religion);
        inputPhEmail.setText(emailPh);
        llPhInfo.setStatus(1);

        // 性别
        rgPhSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_male) {
                    sexPh = MALE;
                    BehaviorPhUtils.setChangeModifyV2(mBehaviorRecord, "P02_S_Gender", "female", "male");
                } else if (checkedId == R.id.rb_female) {
                    BehaviorPhUtils.setChangeModifyV2(mBehaviorRecord, "P02_S_Gender", "male", "female");
                    sexPh = FEMALE;
                }

            }
        });


        addRuntimeCheckEvents();

    }

    private void addRuntimeCheckEvents() {
        inputPhEmail.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                automaticInputInEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputFirstName.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        todo
//        inputMiddleName.getEt_content().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                checkMiddleName(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        inputLastName.getEt_content().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                checkLastName(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        inputPhEmail.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.input_marital, R.id.bt_id_continue, R.id.input_birthday, R.id.input_idcard_type,
            R.id.input_education, R.id.input_religion, R.id.iv_fb_login, R.id.input_bank_count})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.input_marital:
                showPopup();
                break;
            case R.id.input_birthday:
                selectBirthDay();
                break;
            case R.id.bt_id_continue:
                submit();
                break;
            case R.id.input_idcard_type:
                selectCardType();
                break;
            case R.id.input_education:
                selectEducation();
                break;
            case R.id.input_religion:
                selectReligion();
                break;
            case R.id.iv_fb_login:
                facebookLogin();
                break;
            case R.id.input_bank_count:
                selectBankCount();
                break;
        }
        inputPhEmail.inputClearFocus();
    }

    private void selectBankCount() {
        List<DictionaryPhModel.Dictionary> list = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            DictionaryPhModel.Dictionary dictionary = new DictionaryPhModel.Dictionary();
            dictionary.setName(i + "");
            dictionary.setCode(i + "");
            list.add(dictionary);
        }
        popupWindow = new SelectListPopupWindow(activity, list, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                if (dictionary != null) {
                    bankAccount = dictionary.getName();
                    inputBankCount.setText(dictionary.getName());
                    if (popupWindow != null) popupWindow.dismiss();
                }
            }
        });
        if (popupWindow.isShowing())
            popupWindow.dismiss();
        else {
            popupWindow.showPopupWindow(inputBankCount.getTv_content());
        }
    }

    private void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
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

    private void selectCardType() {
//        showPopupWindow("PRIMAARYID ", "cardType", inputPhIdCardType.getTv_content());
    }

    private void selectEducation() {
        showPopupWindow("EDUCATION ", "education", inputEducation.getTv_content());
    }

    private void selectReligion() {
        showPopupWindow("RELIGION ", "religion", inputReligion.getTv_content());
    }

    private final static String[] emails = new String[]{
            "gmail.com",
            "yahoo.com",
//            todo
//            "yahoo.com.ph",
//            "deped.gov.ph",
//            "gmail.com.ph",
            "facebook.com"};

    private void automaticInputInEmail(String text) {
        if (!text.endsWith("@")) {
            if (automaticInputEmailWindow != null && automaticInputEmailWindow.isShowing()) {
                automaticInputEmailWindow.dismiss();
            }
            return;
        }
        List<DictionaryPhModel.Dictionary> mailList = new ArrayList<>();
        for (String email : emails) {
            DictionaryPhModel.Dictionary dictionary = new DictionaryPhModel.Dictionary();
            int index = text.indexOf("@");
            String name = (index >= 0 ? text.subSequence(0, index) : text) + "@" + email;
            dictionary.setName(name);
            dictionary.setCode(name);
            mailList.add(dictionary);
        }
        if (automaticInputEmailWindow == null) {
            automaticInputEmailWindow = new SelectListPopupWindow(activity, mailList, new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                    if (dictionary == null) {
                        return;
                    }
                    inputPhEmail.setText(dictionary.getName());
                    inputPhEmail.getEt_content().setSelection(inputPhEmail.getText().length());
                    if (automaticInputEmailWindow != null) automaticInputEmailWindow.dismiss();
                    automaticInputEmailWindow = null;
                }
            });
        }
        automaticInputEmailWindow.setFocusable(false);
        if (automaticInputEmailWindow.isShowing())
            automaticInputEmailWindow.setList(mailList);
        else {
            automaticInputEmailWindow.showAsDropUp(inputPhEmail.getEt_content());
        }
    }

    private void showPopupWindow(String code, final String value, final TextView textView) {
        NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
            @Override
            public void onNext(DictionaryPhModel dictionaryPhModel) {
                super.onNext(dictionaryPhModel);
                String modelCode = dictionaryPhModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    popupWindow = new SelectListPopupWindow(activity, dictionaryPhModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                            if (dictionary == null) {
                                return;
                            }
                            switch (value) {
                                case "maritalPh":
                                    // 婚姻状况
                                    //BehaviorPhUtils.setChangeModify(bfModel, "P02_C07_R_MARITAL", codeMaritalPh, dictionary.getCode());
                                    codeMaritalPh = dictionary.getCode();
                                    Log.e("zhangs", "onItemClick: " + dictionary.getCode());
                                    break;
                                case "education":
                                    // 学历
                                    //BehaviorPhUtils.setChangeModify(bfModel, "P02_C07_R_EDUCATION", educationCode, dictionary.getCode());
                                    educationCode = dictionary.getCode();
                                    break;
//                                case "religion":
//                                    // 宗教
//                                    BehaviorPhUtils.setChangeModify(bfModel, "P02_C10_R_RELIGION", religionCode, dictionary.getCode());
//                                    religionCode = dictionary.getCode();
//                                    break;
                            }
                            textView.setText(dictionary.getName());
                            if (popupWindow != null) popupWindow.dismiss();
                        }
                    });
                    if (popupWindow.isShowing())
                        popupWindow.dismiss();
                    else {
                        popupWindow.showPopupWindow(textView);
                    }
                }
            }
        });

    }

    private void selectBirthDay() {
        DatePickerPhDialog datePickerPhDialog = new DatePickerPhDialog(this, birthdayPh);
        datePickerPhDialog.setOnDateSelectListener(new DatePickerPhDialog.OnDateSelectListener() {
            @Override
            public void onItemSelect(String date) {
                // 出生日期
                //BehaviorPhUtils.setChangeModify(bfModel, "P02_C05_S_BIRTH", birthdayPh, date);
                birthdayPh = date;
                inputPhBirthday.setText(birthdayPh);
            }
        });
    }

    private void showPopup() {
        showPopupWindow("MARITAL_STATUS ", "maritalPh", inputPhMarital.getTv_content());
    }

    private void submit() {

        // 点击下一步按钮行为
        final BehaviorRecord pNextRecord = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P02_C_Next");

//        cardPh = inputPhIdCard.getText();
        firstName = inputFirstName.getText();
//        middleName = inputMiddleName.getText();
//        lastName = inputLastName.getText();
        maritalPh = inputPhMarital.getText();
        education = inputEducation.getText();
        bankAccount = inputBankCount.getText();
//        religion = inputReligion.getText();
        emailPh = inputPhEmail.getText();

        list.clear();

        // 校验数据合法性
        if (verification()) {
            return;
        }
//        todo
        if (TextUtils.isEmpty(middleName)) {
            fullName = firstName;
        } else {
            fullName = firstName;
        }
        //BehaviorPhUtils.setSensorValue(bfModel, "P02_C20_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        ApplyReqPhModel applyPhReqModel = new ApplyReqPhModel();
        applyPhReqModel.setGender(sexPh);
        applyPhReqModel.setMarital_status(codeMaritalPh);
        applyPhReqModel.setEducation_level(educationCode);
//        applyPhReqModel.setReligionCode(religionCode);
        applyPhReqModel.setEmail(emailPh);
        applyPhReqModel.setBirthday(birthdayPh);
        applyPhReqModel.setBank_act_num(bankAccount);
//        todo
        applyPhReqModel.setFullname(firstName);
//        applyPhReqModel.setMiddlename(middleName);
//        applyPhReqModel.setLastname(lastName);
        applyPhReqModel.setToken(TokenUtils.getToken(activity));
        applyPhReqModel.setStep_name("baseInfo");
        String facebookLink = et_input_facebook_link.getText().toString();
        if (!TextUtils.isEmpty(facebookLink)) {
            applyPhReqModel.setFacebook_messager("https://www.facebook.com/" + facebookLink);
        }
        NetworkPhRequest.applyPersonalinfo(applyPhReqModel, new PhSubscriber<BasePhModel>() {

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
                pNextRecord.setEndTime(String.valueOf(System.currentTimeMillis()));
                if (CODE_SUCCESS.equals(code)) {
                    AppEventsLogger.newLogger(activity).logEvent(PhConstants.FaceBookEvent.EVENT_IDENTITY);
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_IDENTITY, activity, TokenUtils.getOrderId(activity));
                    saveData();
                    if (isFbLogin) {
                        saveFacebookInfo(imageFile, facebookModel);
                    }
                    NetworkPhRequest.getUserStep(applyPhReqModel, new PhSubscriber<>());
                    switchStep(LoanAddressPhActivity.class);
                    finish();
                } else {
                    showToast(basePhModel.getMsg());
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


    private boolean verification() {
        boolean checkFlag = false;
        if (checkFirstName(firstName)) {
            checkFlag = true;
        }
//        if (checkMiddleName(middleName)) {
//            checkFlag = true;
//        }
//        if (
        if (checkBirthday(birthdayPh)) {
            checkFlag = true;
        }
        if (checkSex(sexPh)) {
            checkFlag = true;
        }
        if (checkMarital(maritalPh)) {
            checkFlag = true;
        }
        if (checkEducation(education)) {
            checkFlag = true;
        }
        if (checkBankAccount(bankAccount)) {
            checkFlag = true;
        }
//        if (checkReligion(religion)) {
//            checkFlag = true;
//        }
        if (checkEmail(emailPh)) {
            checkFlag = true;
        }
        return checkFlag;
    }

    private boolean checkFirstName(String firstName) {
        if (TextUtils.isEmpty(firstName)) {
            inputFirstName.setVerify(R.string.apply_contact_name_hint1);
            return true;
        } else {
            inputFirstName.setVerify("");
            return false;
        }
    }

//    todo
//    private boolean checkMiddleName(String middleName) {
//        if (TextUtils.isEmpty(middleName)) {
//            inputMiddleName.setVerify(R.string.apply_contact_name_hint2);
//            return true;
//        } else {
//            inputMiddleName.setVerify("");
//            return false;
//        }
//    }
//
//    private boolean checkLastName(String lastName) {
//        if (TextUtils.isEmpty(lastName)) {
//            inputLastName.setVerify(R.string.apply_contact_name_hint3);
//            return true;
//        } else {
//            inputLastName.setVerify("");
//            return false;
//        }
//    }

    private boolean checkBankAccount(String bankAccount) {
        if (TextUtils.isEmpty(bankAccount)) {
            inputBankCount.setVerify(getString(R.string.a8));
            return true;
        } else {
            inputBankCount.setVerify("");
            return false;
        }
    }

    private boolean checkBirthday(String birthday) {
        if (TextUtils.isEmpty(birthday)) {
            inputPhBirthday.setVerify(R.string.verify_birthday);
            return true;
        } else {
            inputPhBirthday.setVerify("");
            return false;
        }
    }

    private boolean checkSex(String sex) {
        if (TextUtils.isEmpty(sex)) {
            tv_sex_tipPh.setText(getString(R.string.verify_sex_tip));
            return true;
        } else {
            tv_sex_tipPh.setText("");
            return false;
        }
    }

    private boolean checkMarital(String marital) {
        if (TextUtils.isEmpty(marital)) {
            inputPhMarital.setVerify(R.string.verify_marital);
            return true;
        } else {
            inputPhMarital.setVerify("");
            return false;
        }
    }

    private boolean checkEducation(String education) {
        if (TextUtils.isEmpty(education)) {
            inputEducation.setVerify(R.string.education_status_hint);
            return true;
        } else {
            inputEducation.setVerify("");
            return false;
        }
    }

    private boolean checkReligion(String religion) {
        if (TextUtils.isEmpty(religion)) {
            inputReligion.setVerify(R.string.please_select);
            return true;
        } else {
            inputReligion.setVerify("");
            return false;
        }
    }

    private boolean checkEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            inputPhEmail.setVerify(R.string.verify_email);
            return true;
        }
        if (!RegexUtils.isEmail(email)) {
            inputPhEmail.setVerify(R.string.verify_emailaddress);
            return true;
        } else {
            inputPhEmail.setVerify("");
            return false;
        }
    }

    private void getFacebookInfo(AccessToken accessToken) {
        try {
            if (accessToken != null) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (object != null) {
                            facebookModel = new Gson().fromJson(new Gson().toJson(object), FacebookModel.class);
                            Log.e("111", GsonPhHelper.getGson().toJson(facebookModel));
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        imageFile = Glide.with(activity)
                                                .load(facebookModel.getNameValuePairs().getPicture().getNameValuePairs().getData().getNameValuePairs().getUrl())
                                                .downloadOnly(facebookModel.getNameValuePairs().getPicture().getNameValuePairs().getData().getNameValuePairs().getWidth(),
                                                        facebookModel.getNameValuePairs().getPicture().getNameValuePairs().getData().getNameValuePairs().getHeight())
                                                .get();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,first_name,last_name,name_format,short_name,picture");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFacebookInfo(File file, FacebookModel facebookModel) {
        FacebookReqModel model = new FacebookReqModel();
        model.setType("facebook");
        model.setId(facebookModel.getNameValuePairs().getId());
        if (TextUtils.isEmpty(facebookModel.getNameValuePairs().getEmail())) {
            model.setEmail(emailPh);
        } else {
            model.setEmail(facebookModel.getNameValuePairs().getEmail());
        }
        model.setFirst_name(facebookModel.getNameValuePairs().getFirst_name());
        model.setLast_name(facebookModel.getNameValuePairs().getLast_name());
        model.setName(facebookModel.getNameValuePairs().getName());
        model.setName_format(facebookModel.getNameValuePairs().getName_format());
        model.setShort_name(facebookModel.getNameValuePairs().getShort_name());
        model.setPicture(file);
        model.setToken(TokenUtils.getToken(activity));
        NetworkPhRequest.saveFacebookInfo(model, new PhSubscriber<BasePhModel>() {

            @Override
            public void onNext(BasePhModel baseRcModel) {
                super.onNext(baseRcModel);
            }
        });
    }

    private void saveData() {
        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_SEX, sexPh);
        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_MARITAL, maritalPh);
        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_CODE_MARITAL, codeMaritalPh);
        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_EMAIL, emailPh);
        spPhUtil.saveListInteger(PhConstants.PhIdentityInfo.IDENTITY_LIST, list);
        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_BIRTHDAY, birthdayPh);
        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_EDUCATION, education);
        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_EDUCATION_CODE, educationCode);
        spPhUtil.saveString(PhConstants.PhUser.USER_NAME, fullName);
        spPhUtil.saveBoolean(PhConstants.FINISH_STATUS.BASE_INFO, true);
//        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_RELIGION, religion);
//        spPhUtil.saveString(PhConstants.PhIdentityInfo.IDENTITY_RELIGION_CODE, religionCode);
    }

    private ApplyReqPhModel.ThirdInfosBean getThirdInfo(String name, String phone, String auth) {
        ApplyReqPhModel.ThirdInfosBean thirdInfosBean = new ApplyReqPhModel.ThirdInfosBean();
        thirdInfosBean.setThirdCode(name);
        thirdInfosBean.setThirdName(name);
        thirdInfosBean.setAuthPhone(phone);
        thirdInfosBean.setIsAuth(auth);
        return thirdInfosBean;
    }

    private SpannableString getFirstWordRed(String text) {
        SpannableString style = new SpannableString(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        return style;
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.info_idcard);
//        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        StatusPhBarUtil.setColor(this, Color.parseColor("#085F06"));
    }
}
