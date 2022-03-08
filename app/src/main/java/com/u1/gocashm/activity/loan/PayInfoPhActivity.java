package com.u1.gocashm.activity.loan;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.u1.gocashm.model.request.ApplyReqPayMentTypeBody;
import com.u1.uclibrary.idcardcamera.camera.PhCameraActivity;
import com.u1.gocashm.R;
import com.u1.gocashm.model.request.AppInfoReqModel;
import com.u1.gocashm.model.request.AppPhInfo;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.request.ImagePhReqModel;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.request.RecordPhModel;
import com.u1.gocashm.model.response.ApplyModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.ChannelListModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.model.response.OrderListModel;
import com.u1.gocashm.model.response.User;
import com.u1.gocashm.model.response.UserInfoModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AFPhListener;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.LoanInfoPhUtils;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TextColorUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.WordPhUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LoanInfoView;
import com.u1.gocashm.view.dialog.LoanAgreementDialog;
import com.u1.gocashm.view.dialog.ModifyAmountDialog;
import com.u1.gocashm.view.dialog.SurveyDialog;
import com.u1.gocashm.view.dialog.WorkCardExampleDialog;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/9/4 0004 下午 3:03
 */
public class PayInfoPhActivity extends IdentityInfoActivity {

    private static final String TAG = PayInfoPhActivity.class.getSimpleName();
    private static final int TAKE_WORK_CARD = 101;
    private static final int TYPE_BANK = 0;
    private static final int TYPE_CASH = 1;
    private static final int TYPE_CHANNEL = 2;
    @BindView(R.id.tv_user_name)
    TextView tvPhUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvPhUserPhone;
    @BindView(R.id.tv_update_phone)
    TextView tvPhUpdatePhone;
    @BindView(R.id.et_sms_code)
    EditText etPhSmsCode;
    @BindView(R.id.tv_get_code)
    TextView tvPhGetCode;
    @BindView(R.id.tv_sms_code_title)
    TextView tvPhSmsCodeTitle;
    @BindView(R.id.tv_agreement)
    TextView tvPhAgreement;
    @BindView(R.id.ll_info)
    LoanInfoView llPhInfo;
    @BindView(R.id.tv_submit)
    TextView tvPhSubmit;
    @BindView(R.id.register_info_layout)
    LinearLayout llPhRegister;
    @BindView(R.id.tv_payinfo_code)
    TextView tvPhCode;
    @BindView(R.id.input_bank_name)
    InputView inputPhBankName;
    @BindView(R.id.input_bank_num)
    InputView inputPhBankNum;
    @BindView(R.id.input_password)
    InputView inputPhPassword;
    @BindView(R.id.agreement_layout)
    LinearLayout agreementLayout;
    @BindView(R.id.rb_bank)
    RadioButton rbBank;
    @BindView(R.id.user_bank_info_layout)
    LinearLayout userBankInfoLayout;
    @BindView(R.id.input_agency_name)
    InputView inputAgencyName;
    @BindView(R.id.input_agency_fee)
    InputView inputAgencyFee;
    @BindView(R.id.user_agency_info_layout)
    LinearLayout userAgencyInfoLayout;
    /*    @BindView(R.id.rb_cash)
        RadioButton rbCash;*/
    @BindView(R.id.rg_select_pay)
    RadioGroup rgSelectPay;
    @BindView(R.id.tv_agreement_hint)
    TextView tvAgreementHint;
    @BindView(R.id.sms_code_layout)
    LinearLayout smsCodeLayout;
    @BindView(R.id.iv_work_preview)
    ImageView ivWorkPreview;
    @BindView(R.id.user_name_layout)
    LinearLayout userNameLayout;
    @BindView(R.id.input_channel)
    InputView inputChannel;
    @BindView(R.id.input_channel_name)
    InputView inputChannelName;
    @BindView(R.id.input_channel_email)
    InputView inputChannelEmail;
    @BindView(R.id.input_channel_phone)
    InputView inputChannelPhone;
    @BindView(R.id.phone_change_tips_tv)
    TextView tipsTv;
    @BindView(R.id.other_info_layout)
    LinearLayout channelInfoLayout;

    private FragmentActivity activity;

    private SelectListPopupWindow popupWindow;

    private String bankNamePh;
    private String bankCodePh;
    private String agencyName;
    private String agencyCode;
    private int selectFlag;

    private boolean isCountFinished;
    private CountDownTimer timer;
    private String userPhone;
    private String userName;
    private String email;
    private Disposable disposable;
    private SharedPreferencesPhUtil spPhUtil;
    private String bankCardNum;
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次
    // 行为数据model
    private BehaviorPhReqModel bfModel;

    private MySensorListener sensorListener;
    private SensorManager MyManage; //新建sensor的管理器
    private LoanAgreementDialog agreementDialog;
    private RecordPhModel recordPhModel;
    private ModifyAmountDialog modifyAmountDialog;
    private Integer amount;
    private Integer term;
    private Integer maxAmount;
    private String productCode;
    private Integer initAmount;
    private Integer initTerm;
    private String workCardPath;
    private int workCardImageId;
    private String channelName;
    private BehaviorRecord payMethodBehavior;
    private BehaviorRecord p06CompanyID;
    private BehaviorRecord pNextRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_info);
        activity = this;
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 启动支付信息
        initBehavior();
    }

    @Override
    protected void onDestroy() {
        MyManage.unregisterListener(sensorListener);
        inputPhBankNum.inputClearFocus();
        inputPhPassword.inputClearFocus();
        inputClearFocus(etPhSmsCode);
        BehaviorPhUtils.setDestroyModify(bfModel, "P06_C99");
        inputChannelPhone.getBehaviorRecord().setNewValue(inputChannelPhone.getText());
        uploadBehaviorRecords(inputPhBankName,
                inputPhPassword,
                inputAgencyName,
                inputAgencyFee,
                inputChannel,
                inputChannelName,
                inputChannelEmail,
                selectFlag == TYPE_BANK ? inputPhBankNum : inputChannelPhone
        );
        savePayInfo();
        if (null != timer) {
            timer.cancel();
        }
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }

        super.onDestroy();
    }

    private void initData() {
        initAmount = LoanInfoPhUtils.getAmount();
        initTerm = LoanInfoPhUtils.getApplyTerm();
        bfModel = BehaviorPhUtils.setEnterPageModify("P06_C00", getBaseContext());
        MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        stopCount();
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (EventTagPh.APPLY_SUCCESS.equals(tagEvent.getTag())) {
                    finish();
                }
            }
        });
        spPhUtil = SharedPreferencesPhUtil.getInstance(this);
        bankNamePh = spPhUtil.getString(PhConstants.PhPayInfo.BANK_NAME);
        bankCodePh = spPhUtil.getString(PhConstants.PhPayInfo.BANK_CODE);
        bankCardNum = spPhUtil.getString(PhConstants.PhPayInfo.BANK_NUM);
//        Customer customer = spPhUtil.getCustomer();
//        if (customer == null) return;
//        if (TextUtils.isEmpty(userName)) {
//            userName = customer.getName();
//        }
        getUserInfo();
    }

    private void getUserInfo() {
        NetworkPhRequest.getUserInfo(TokenUtils.getToken(activity), new PhSubscriber<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel userInfoModel) {
                super.onNext(userInfoModel);
                String code = userInfoModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    UserInfoModel.UserInfo data = userInfoModel.getData();
                    User user = data.getUser();
                    userPhone = user.getTelephone();
                    userName = user.getFullname();
                    email = user.getEmail();
                    tvPhUserName.setText(userName);
                    //如果是首贷，则取手机号然后在前面增加0，显示给用户，如果是复贷，则按接口返回的显示
                    if (!TextUtils.isEmpty(userPhone)) {
//                        inputChannelPhone.setText(user.isReload() ? userPhone : "0" + userPhone);
                        inputChannelPhone.setText(userPhone);
                    }
                    inputChannelName.setText(userName);
                    inputChannelEmail.setText(email);

                    // 复贷
                    if (user.getOrder() != null && user.getOrder().user != null && user.getOrder().user.bank_card != null) {
                        OrderListModel.OrderList.Order.UserData.BankCardData cardBean = user.getOrder().user.bank_card;
                        String paymentType = cardBean.payment_type;
                        if (TextUtils.equals(paymentType, "bank")) {
                            String bankName = cardBean.bank_name;
                            String bankAccount = cardBean.account_no;

                            if (!TextUtils.isEmpty(bankName)) {
                                bankNamePh = bankName;
                                bankCodePh = bankName;
                                inputPhBankName.setText(bankName);
                            }

                            if (!TextUtils.isEmpty(bankAccount)) {
                                inputPhBankNum.setText(bankAccount);
                                inputPhBankNum.getEt_content().setSelection(bankAccount.length());
                            }
                        } else if (TextUtils.equals(paymentType, "other")) {
                            channelName = cardBean.channel;
                            userPhone = cardBean.account_no;

                            if (!TextUtils.isEmpty(channelName)) {
                                inputChannel.setText(channelName);
                            }
                        }
                    }
                }
            }
        });
    }


    private void initView() {
        rgSelectPay.setVisibility(View.VISIBLE);
        rgSelectPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (payMethodBehavior == null) {
                    payMethodBehavior = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P06_PaymentMethord");
                }
                payMethodBehavior.setOldValue(payMethodBehavior.getNewValue());
                if (i == R.id.rb_bank) {
                    payMethodBehavior.setNewValue("Bank");
                    BehaviorPhUtils.setChangeModify(bfModel, "P06_C02_PAY_TYPE", "no", "yes");
                    userAgencyInfoLayout.setVisibility(View.GONE);
                    channelInfoLayout.setVisibility(View.GONE);
                    userNameLayout.setVisibility(View.VISIBLE);
                    userBankInfoLayout.setVisibility(View.VISIBLE);
                    selectFlag = TYPE_BANK;
                }
//                todo
//                else if (i == R.id.rb_cash) {
//                    payMethodBehavior.setNewValue("Cash");
//                    NoCardHintDialog dialog = new NoCardHintDialog();
//                    dialog.setOnClick(new NoCardHintDialog.OnClick() {
//                        @Override
//                        public void OnClick() {
//                            rbBank.setChecked(true);
//                        }
//                    });
//                    dialog.setOnCancelClick(new NoCardHintDialog.OnCancelClick() {
//                        @Override
//                        public void OnCancelClick() {
//                            BehaviorPhUtils.setChangeModify(bfModel, "P06_C02_PAY_TYPE", "yes", "no");
//                        }
//                    });
//                    dialog.show(getSupportFragmentManager(), "1");
//                    userAgencyInfoLayout.setVisibility(View.VISIBLE);
//                    userBankInfoLayout.setVisibility(View.GONE);
//                    channelInfoLayout.setVisibility(View.GONE);
//                    selectFlag = TYPE_CASH;
                else if (i == R.id.rb_other) {
                    payMethodBehavior.setNewValue("Other");
                    userAgencyInfoLayout.setVisibility(View.GONE);
                    userBankInfoLayout.setVisibility(View.GONE);
                    channelInfoLayout.setVisibility(View.VISIBLE);
                    userNameLayout.setVisibility(View.GONE);
                    selectFlag = TYPE_CHANNEL;
                }
            }
        });
        rbBank.setChecked(true);
        inputAgencyFee.setEnabled(false);
        inputChannelName.setEnabled(false);
        inputChannelEmail.setEnabled(false);
        inputChannelPhone.setEnabled(true);
        inputChannelPhone.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && !s.toString().startsWith("0")) {
                    inputChannelPhone.setText("" + s);
                    inputChannelPhone.getEt_content().setSelection(inputChannelPhone.getText().length());
                }
            }
        });
        tipsTv.setText(TextColorUtil.getColorTextSpanned(R.color.red, getString(R.string.phone_change_tips), 0, 5));
        tvPhUserPhone.setText(String.format(getString(R.string.your_user_phone), userPhone));
        tvPhUserName.setText(userName);
        inputPhBankName.setText(bankNamePh);
        if (!TextUtils.isEmpty(bankCardNum)) {
            inputPhBankNum.setText(bankCardNum);
            inputPhBankNum.getEt_content().setSelection(bankCardNum.length());
        }
        tvPhSmsCodeTitle.setText(getFirstWordRed(getString(R.string.code)));
        if (TokenUtils.TokenCheck(activity)) {
            llPhRegister.setVisibility(View.GONE);
        }
//        setTvUpdatePhoneStyle();
        setTvAgreementStyle();
    }

    private void initListener() {
        inputPhBankNum.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s == null || s.length() == 0) {
                    return;
                }
                if (TextUtils.isEmpty(bankNamePh)) {
                    return;
                }
                checkBankCardNum(bankNamePh, s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        checkboxAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    tvAgreementHint.setVisibility(View.GONE);
//                }
//            }
//        });
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

    private void initBehavior() {

        // 银行卡号
        AFPhListener.getInstance().addEditTextListener(inputPhBankNum.getEt_content(), bfModel, "P06_C04_I_BANKCARDNO");
        // 密码
        AFPhListener.getInstance().addEditTextListener(inputPhPassword.getEt_content(), bfModel, "P06_C05_I_PASSWORD");
        // 验证码
        AFPhListener.getInstance().addEditTextListener(etPhSmsCode, bfModel, "P06_C06_I_VALIDATECODE");
        getFocus(inputPhBankNum);
        getFocus(inputPhPassword);
        getFocus(etPhSmsCode);
    }

    private void setTvAgreementStyle() {
        agreementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAgreementDialog(false, null);
            }
        });
    }


    private void showAgreementDialog(final boolean isModify, final ApplyReqPhModel apply) {
//        String accessToken = spPhUtil.getString(PhConstants.PhUser.ACCESS_TOKEN);
//        long applyId = spPhUtil.getLong(PhConstants.PhUser.APPLY_ID, 0L);
//        if (applyId == 0) {
//            return;
//        }
//        NetworkPhRequest.getContractPreview(applyId, accessToken, new PhSubscriber<BasePhModel>() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                showLoadingDialog();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                closeLoadingDialog();
//                Log.e("111", e.getMessage());
//                showToast(getString(R.string.error_request_fail));
//            }
//
//            @Override
//            public void onNext(final BasePhModel basePhModel) {
//                super.onNext(basePhModel);
//                closeLoadingDialog();
//                String code = basePhModel.getCode();
//                if (CODE_SUCCESS.equals(code)) {
//                    agreementDialog = new LoanAgreementDialog();
//                    agreementDialog.setLoanContractUrl(basePhModel.getData().toString());
//                    agreementDialog.setOnClick(new LoanAgreementDialog.OnClick() {
//                        @Override
//                        public void OnClick() {
//                            if (isModify) {
//                                modifyAmountDialog.dismiss();
//                                commit(apply);
//                            } else {
//                                getAgreementBehavior(true);
//
//                            }
//                        }
//                    });
//                    agreementDialog.setOnCancelClick(new LoanAgreementDialog.OnCancelClick() {
//                        @Override
//                        public void OnCancelClick() {
//                            if (!isModify) {
//                                getAgreementBehavior(false);
//                            } else {
//                                apply.setApplyAmount(initAmount);
//                                apply.setLoanTerms(initTerm);
////                                confirmAmount(false, apply);
//                            }
//                        }
//                    });
//                    agreementDialog.show(getSupportFragmentManager(), "1");
//                } else {
//                    showToast(basePhModel.getMsg());
//                }
//
//            }
//        });
    }

    private void getAgreementBehavior(boolean isAgree) {
        recordPhModel = new RecordPhModel();
        recordPhModel.setId("P06_C07_B_CONTRACT");
        recordPhModel.setNewValue(isAgree ? "Agree" : "Disagree");
        recordPhModel.setStartTime(BehaviorPhUtils.format.format(new Date(agreementDialog.getInitTime())));
        recordPhModel.setEndTime(BehaviorPhUtils.format.format(new Date(agreementDialog.getEndTime())));
        bfModel.getRecords().add(recordPhModel);
//        checkboxAgreement.setChecked(isAgree);
    }

    private void setTvUpdatePhoneStyle() {
        bankCardNum = inputPhBankNum.getText();
        final String text = getString(R.string.update_user_phone);
        SpannableString spanText = new SpannableString(text);

        int start = text.indexOf(",") + 1;
        int end = text.length();
        spanText.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.blue)); //设置文件颜色
                ds.setUnderlineText(true); //设置下划线
            }

            @Override
            public void onClick(@NonNull View view) {
                RxBus.getDefault().post(new TagEvent(EventTagPh.LOAN));
                savePayInfo();
                finish();
            }
        }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPhUpdatePhone.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tvPhUpdatePhone.setText(spanText);
        tvPhUpdatePhone.setMovementMethod(LinkMovementMethod.getInstance()); //开始响应点击事件
    }

    private void savePayInfo() {
        bankCardNum = inputPhBankNum.getText();
        spPhUtil.saveString(PhConstants.PhPayInfo.BANK_NAME, bankNamePh);
        spPhUtil.saveString(PhConstants.PhPayInfo.BANK_CODE, bankCodePh);
        spPhUtil.saveString(PhConstants.PhPayInfo.BANK_NUM, bankCardNum);
    }

    @Override
    public void initTitleBar() {
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        setTitleBack(R.string.pay_info_title);
        llPhInfo.setStatus(5);
    }

    @OnClick({R.id.input_bank_name, R.id.tv_get_code, R.id.tv_submit, R.id.input_agency_name,
            R.id.tv_example, R.id.iv_take_photo, R.id.input_channel})
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
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.tv_submit:
//                原始提交
//                submit();
//                提交延后
                PaymentMethod();
                break;
            case R.id.tv_example:
                toWorkCardExample();
                break;
            case R.id.iv_take_photo:
                takePhoto();
                break;
            case R.id.input_channel:
                selectChannel();
                break;
        }
        inputPhBankNum.inputClearFocus();
        inputPhPassword.inputClearFocus();
        inputClearFocus(etPhSmsCode);
    }

    private void PaymentMethod() {
        Dialog dialog = new Dialog(PayInfoPhActivity.this);
        //填充对话框的布局
        View inflate = LayoutInflater.from(PayInfoPhActivity.this).inflate(R.layout.dialog_payment_method, null);
        //初始化控件
        CheckBox cx_no = inflate.findViewById(R.id.cx_no);
        CheckBox cx_yes = inflate.findViewById(R.id.cx_yes);
        CheckBox cx_VF = inflate.findViewById(R.id.cx_VF);
        CheckBox cx_OG = inflate.findViewById(R.id.cx_OG);
        CheckBox cx_FW = inflate.findViewById(R.id.cx_FW);
        CheckBox cx_BW = inflate.findViewById(R.id.cx_BW);
        CheckBox cx_OTHER = inflate.findViewById(R.id.cx_OTHER);
        EditText number1 = inflate.findViewById(R.id.et_number1);
        EditText number2 = inflate.findViewById(R.id.et_number2);
        EditText number3 = inflate.findViewById(R.id.et_number3);
        EditText number4 = inflate.findViewById(R.id.et_number4);
        EditText number5 = inflate.findViewById(R.id.et_number5);
        Button bt_Submit = inflate.findViewById(R.id.bt_submit);
        LinearLayout inputtype = inflate.findViewById(R.id.lin_InputType);

        cx_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cx_no.setChecked(true);
                cx_yes.setChecked(false);
                inputtype.setVisibility(View.GONE);
                bt_Submit.setVisibility(View.VISIBLE);
            }
        });
        cx_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cx_yes.setChecked(true);
                cx_no.setChecked(false);
                inputtype.setVisibility(View.VISIBLE);
                bt_Submit.setVisibility(View.VISIBLE);
            }
        });
        bt_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone1 = number1.getText().toString();
                String phone2 = number2.getText().toString();
                String phone3 = number3.getText().toString();
                String phone4 = number4.getText().toString();
                String phone5 = number5.getText().toString();
                if (cx_no.isChecked()) {
                    submit();
                } else {
                    if (cx_VF.isChecked() || cx_OG.isChecked() || cx_FW.isChecked() || cx_BW.isChecked() || cx_OTHER.isChecked()) {
                        if (!phone1.isEmpty() | !phone2.isEmpty() | !phone3.isEmpty() | !phone4.isEmpty() | !phone5.isEmpty()) {
                            NewSubmit(phone1, phone2, phone3, phone4, phone5);
                        } else {
                            ToastUtils.showLong(getString(R.string.a308));
                        }
                    } else {
                        ToastUtils.showLong(getString(R.string.a307));
                    }
                }
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //设置Dialog宽高
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    private void NewSubmit(String phone1, String phone2, String phone3, String phone4, String phone5) {
        //点击按钮
        BehaviorPhUtils.setClickModify(bfModel, "P06_C07_B_NEXT");
//        String password = inputPhPassword.getText();
        bankNamePh = inputPhBankName.getText();
        String bankCardNum = inputPhBankNum.getText();
//        String smsCode = etPhSmsCode.getText().toString();

        if (selectFlag == TYPE_CASH) {
            if (TextUtils.isEmpty(workCardPath)) {
                showToast(getString(R.string.a9));
                return;
            }
            if (TextUtils.isEmpty(agencyName)) {
                inputAgencyName.setVerify(R.string.please_select);
                return;
            } else {
                inputAgencyName.setVerify("");
            }
        } else if (selectFlag == TYPE_BANK) {
            if (TextUtils.isEmpty(bankNamePh)) {
                inputPhBankName.setVerify(R.string.verify_bankname);
                return;
            } else {
                inputPhBankName.setVerify("");
            }
            if (TextUtils.isEmpty(bankCardNum)) {
                inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                return;
            } else {
                inputPhBankNum.setVerify("");
            }
            if (checkBankCardNum(bankNamePh, bankCardNum)) {
                return;
            } else {
                inputPhBankNum.setVerify("");
            }
        } else {
            if (TextUtils.isEmpty(channelName)) {
                inputChannel.setVerify(R.string.please_select);
                return;
            } else {
                inputChannel.setVerify("");
            }

            if (TextUtils.isEmpty(inputChannelPhone.getText())) {
                inputChannelPhone.setVerify(getString(R.string.a10));
                return;
            } else if (inputChannelPhone.getText().length() < 11) {
                inputChannelPhone.setVerify(getString(R.string.a11));
                return;
            } else {
                inputChannelPhone.setVerify("");
            }
        }
//        if (!TokenUtils.TokenCheck(activity)) {
//            if (!VerifyUtil.isValidPassword(password)) {
//                inputPhPassword.setVerify(R.string.verify_password);
//                return;
//            } else {
//                inputPhPassword.setVerify("");
//            }
//        }

//        if (!checkboxAgreement.isChecked()) {
//            tvAgreementHint.setVisibility(View.VISIBLE);
//            return;
//        }
        BehaviorPhUtils.setSensorValue(bfModel, "P06_C20_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        final ApplyReqPhModel applyPh = new ApplyReqPhModel();
//        applyPh.setApplyId(LoanInfoPhUtils.getApplyId());
//        List<Integer> imageList = new ArrayList<>();
        if (selectFlag == TYPE_CASH) {
            applyPh.setInstitution_name(agencyName);
            applyPh.setType("cash");
//            applyPh.setOnlinePay("N");
//            imageList.add(workCardImageId);
//            applyPh.setImages(imageList);
        } else if (selectFlag == TYPE_BANK) {
            applyPh.setBank_code(bankCodePh);
            applyPh.setBank_no(bankCardNum);
            applyPh.setType("bank");
        } else {
            applyPh.setType("other");
            String phone = inputChannelPhone.getText();
            /*if (!phone.startsWith("0") && phone.length() == 10) {
                phone = "0" + phone;
            }*/
            applyPh.setBank_code(phone);
            applyPh.setChannel_name(channelName);
        }
//        applyPh.setPassword(password);
//        applyPh.setTelephone(userPhone);
//        applyPh.setCaptcha(smsCode);
        applyPh.setToken(TokenUtils.getToken(activity));
        applyPh.setStep_name("payInfo");
        ApplyReqPayMentTypeBody applyReqPayMentTypeBody = new ApplyReqPayMentTypeBody();
        if (!phone1.isEmpty()) {
            applyReqPayMentTypeBody.setVodafone(phone1);
        } else {
            applyReqPayMentTypeBody.setVodafone("");
        }
        if (!phone2.isEmpty()) {
            applyReqPayMentTypeBody.setOrange(phone2);
        } else {
            applyReqPayMentTypeBody.setOrange("");
        }
        if (!phone3.isEmpty()) {
            applyReqPayMentTypeBody.setMyFawry(phone3);
        } else {
            applyReqPayMentTypeBody.setMyFawry("");
        }
        if (!phone4.isEmpty()) {
            applyReqPayMentTypeBody.setBankWallet(phone4);
        } else {
            applyReqPayMentTypeBody.setBankWallet("");
        }
        if (!phone5.isEmpty()) {
            applyReqPayMentTypeBody.setOther(phone5);
        } else {
            applyReqPayMentTypeBody.setOther("");
        }
        applyPh.setOther_payment(jsonbody(applyReqPayMentTypeBody));
//        commit(applyPh);
        pNextRecord = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P06_C_Submit", true);
        if ("cash".equals(applyPh.getType())) {
            commitCash(applyPh);
            return;
        }
        NetworkPhRequest.BindingPayMent(applyPh, new PhSubscriber<BasePhModel>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel applyModel) {
                super.onNext(applyModel);
                closeLoadingDialog();
                String code = applyModel.getCode();
                pNextRecord.setEndTime();
                if (CODE_SUCCESS.equals(code)) {
//                    ApplyModel.ApplyBean body = applyModel.getData();
                    Intent intent = new Intent(activity, ConfirmLoanActivity.class);
//                    intent.putExtra(PhConstants.PRODUCT, body.getProduct());
//                    intent.putExtra(PhConstants.APPLY_INFO, applyPh);
                    NetworkPhRequest.getUserStep(applyPh, new PhSubscriber<>());
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_PAY_INFO_SIGN, activity, TokenUtils.getOrderId(activity));
                    startActivity(intent);
                    //BehaviorPhUtils.saveBehaviorReqModel(bfModel);
                    finish();
                }

//                } else if (status != null && "128".equals(status.getCode())) {
//                    ApplyModel.ApplyBean body = applyModel.getBody();
//                    showModifyAmountDialog(body, true, applyPh);
//                }
                else {
                    showToast(applyModel.getMsg());
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

    private void selectChannel() {
        NetworkPhRequest.getChannelList(new PhSubscriber<ChannelListModel>() {

            @Override
            public void onNext(ChannelListModel channelListModel) {
                super.onNext(channelListModel);
                String code = channelListModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    List<ChannelListModel.Channel> data = channelListModel.getData();
                    List<DictionaryPhModel.Dictionary> list = new ArrayList<>();
                    for (ChannelListModel.Channel channel : data) {
                        DictionaryPhModel.Dictionary dictionary = new DictionaryPhModel.Dictionary();
                        dictionary.setName(channel.getName());
                        dictionary.setCode(channel.getName());
                        list.add(dictionary);
                    }
                    popupWindow = new SelectListPopupWindow(activity, list, new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                            if (dictionary != null) {
                                channelName = dictionary.getName();
                                inputChannel.setText(dictionary.getName());
                                if (popupWindow != null) popupWindow.dismiss();
                            }
                        }
                    });
                    if (popupWindow.isShowing())
                        popupWindow.dismiss();
                    else {
                        popupWindow.showPopupWindow(inputChannel.getTv_content());
                    }
                }
            }
        });
    }

    private void toWorkCardExample() {
        WorkCardExampleDialog dialog = new WorkCardExampleDialog();
        dialog.show(getSupportFragmentManager(), "1");
    }

    private void takePhoto() {
        Intent intent = new Intent(activity, PhCameraActivity.class);
        intent.putExtra(PhCameraActivity.TAKE_TYPE, PhCameraActivity.TYPE_WORK);
        intent.putExtra(PhCameraActivity.CARD_TYPE, "work");
        activity.startActivityForResult(intent, TAKE_WORK_CARD);
        if (p06CompanyID != null) {
            mBehaviorRecord.getRecords().remove(p06CompanyID);
        }
        p06CompanyID = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P06_CompanyID");
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

    public void getFocus(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getEtFocus(editText);
                return false;
            }
        });
    }

    public void getEtFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    private void inputClearFocus(EditText editText) {
        editText.clearFocus();
        editText.setFocusable(false);
    }


    private void selectBank() {
        showPopupWindow("BANK", "bank", inputPhBankName.getTv_content());
    }

    private void selectAgency() {
        NetworkPhRequest.getInstitutionList(new PhSubscriber<ChannelListModel>() {

            @Override
            public void onNext(ChannelListModel channelListModel) {
                super.onNext(channelListModel);
                String code = channelListModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    List<ChannelListModel.Channel> data = channelListModel.getData();
                    List<DictionaryPhModel.Dictionary> list = new ArrayList<>();
                    for (ChannelListModel.Channel channel : data) {
                        DictionaryPhModel.Dictionary dictionary = new DictionaryPhModel.Dictionary();
                        dictionary.setName(channel.getName());
                        dictionary.setCode(channel.getFee());
                        list.add(dictionary);
                    }
                    popupWindow = new SelectListPopupWindow(activity, list, new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                            if (dictionary != null) {
                                agencyName = dictionary.getName();
                                inputAgencyName.setText(dictionary.getName());
                                inputAgencyFee.setText(dictionary.getCode());
                                if (popupWindow != null) popupWindow.dismiss();
                            }
                        }
                    });
                    if (popupWindow.isShowing())
                        popupWindow.dismiss();
                    else {
                        popupWindow.showPopupWindow(inputAgencyName.getTv_content());
                    }
                }
            }
        });
    }

    private void getCode() {
        //点击按钮
        BehaviorPhUtils.setClickModify(bfModel, "P06_C07_B_GETCODE");
        getSmsCode();
    }

    private void getSmsCode() {
        NetworkPhRequest.getSmsCode(userPhone, "REGISTER", new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel smsCodePhModel) {
                super.onNext(smsCodePhModel);
                startCount();
                String code = smsCodePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    ToastUtils.showShort(smsCodePhModel.getMsg());
                } else {
                    showToast(smsCodePhModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showToast(R.string.error_request_fail);
            }
        });
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
        if (requestCode == 3000) {
            boolean permissionFlag = (result == PackageManager.PERMISSION_GRANTED);
            if (result == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, getString(R.string.error_permission), Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
            isToast = true;
            if (permissionFlag) {
                getSmsCode();
            }
        }
    }

    private void submit() {
        //点击按钮
        BehaviorPhUtils.setClickModify(bfModel, "P06_C07_B_NEXT");
//        String password = inputPhPassword.getText();
        bankNamePh = inputPhBankName.getText();
        String bankCardNum = inputPhBankNum.getText();
//        String smsCode = etPhSmsCode.getText().toString();

        if (selectFlag == TYPE_CASH) {
            if (TextUtils.isEmpty(workCardPath)) {
                showToast(getString(R.string.a9));
                return;
            }
            if (TextUtils.isEmpty(agencyName)) {
                inputAgencyName.setVerify(R.string.please_select);
                return;
            } else {
                inputAgencyName.setVerify("");
            }
        } else if (selectFlag == TYPE_BANK) {
            if (TextUtils.isEmpty(bankNamePh)) {
                inputPhBankName.setVerify(R.string.verify_bankname);
                return;
            } else {
                inputPhBankName.setVerify("");
            }
            if (TextUtils.isEmpty(bankCardNum)) {
                inputPhBankNum.setVerify(R.string.verify_bankcardnum);
                return;
            } else {
                inputPhBankNum.setVerify("");
            }
            if (checkBankCardNum(bankNamePh, bankCardNum)) {
                return;
            } else {
                inputPhBankNum.setVerify("");
            }
        } else {
            if (TextUtils.isEmpty(channelName)) {
                inputChannel.setVerify(R.string.please_select);
                return;
            } else {
                inputChannel.setVerify("");
            }

            if (TextUtils.isEmpty(inputChannelPhone.getText())) {
                inputChannelPhone.setVerify(getString(R.string.a10));
                return;
            } else if (inputChannelPhone.getText().length() < 11) {
                inputChannelPhone.setVerify(getString(R.string.a11));
                return;
            } else {
                inputChannelPhone.setVerify("");
            }
        }
//        if (!TokenUtils.TokenCheck(activity)) {
//            if (!VerifyUtil.isValidPassword(password)) {
//                inputPhPassword.setVerify(R.string.verify_password);
//                return;
//            } else {
//                inputPhPassword.setVerify("");
//            }
//        }

//        if (!checkboxAgreement.isChecked()) {
//            tvAgreementHint.setVisibility(View.VISIBLE);
//            return;
//        }
        BehaviorPhUtils.setSensorValue(bfModel, "P06_C20_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        final ApplyReqPhModel applyPh = new ApplyReqPhModel();
//        applyPh.setApplyId(LoanInfoPhUtils.getApplyId());
//        List<Integer> imageList = new ArrayList<>();
        if (selectFlag == TYPE_CASH) {
            applyPh.setInstitution_name(agencyName);
            applyPh.setType("cash");
//            applyPh.setOnlinePay("N");
//            imageList.add(workCardImageId);
//            applyPh.setImages(imageList);
        } else if (selectFlag == TYPE_BANK) {
            applyPh.setBank_code(bankCodePh);
            applyPh.setBank_no(bankCardNum);
            applyPh.setType("bank");
        } else {
            applyPh.setType("other");
            String phone = inputChannelPhone.getText();
            /*if (!phone.startsWith("0") && phone.length() == 10) {
                phone = "0" + phone;
            }*/
            applyPh.setBank_code(phone);
            applyPh.setChannel_name(channelName);
        }
//        applyPh.setPassword(password);
//        applyPh.setTelephone(userPhone);
//        applyPh.setCaptcha(smsCode);
        applyPh.setToken(TokenUtils.getToken(activity));
        applyPh.setStep_name("payInfo");
        commit(applyPh);
    }

    private void commit(final ApplyReqPhModel applyPh) {
        pNextRecord = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P06_C_Submit", true);
        if ("cash".equals(applyPh.getType())) {
            commitCash(applyPh);
            return;
        }
        NetworkPhRequest.submitApplyInfo(applyPh, new PhSubscriber<BasePhModel>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel applyModel) {
                super.onNext(applyModel);
                closeLoadingDialog();
                String code = applyModel.getCode();
                pNextRecord.setEndTime();
                if (CODE_SUCCESS.equals(code)) {
//                    ApplyModel.ApplyBean body = applyModel.getData();
                    Intent intent = new Intent(activity, ConfirmLoanActivity.class);
//                    intent.putExtra(PhConstants.PRODUCT, body.getProduct());
//                    intent.putExtra(PhConstants.APPLY_INFO, applyPh);
                    NetworkPhRequest.getUserStep(applyPh, new PhSubscriber<>());
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_PAY_INFO_SIGN, activity, TokenUtils.getOrderId(activity));
                    startActivity(intent);
                    //BehaviorPhUtils.saveBehaviorReqModel(bfModel);
                    finish();
                }

//                } else if (status != null && "128".equals(status.getCode())) {
//                    ApplyModel.ApplyBean body = applyModel.getBody();
//                    showModifyAmountDialog(body, true, applyPh);
//                }
                else {
                    showToast(applyModel.getMsg());
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

    private void commitCash(final ApplyReqPhModel applyPh) {
        showLoadingDialog();
        ImagePhReqModel imageReqModel = new ImagePhReqModel();
        imageReqModel.setApplyId(TokenUtils.getApplyId(activity));
        imageReqModel.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
        imageReqModel.setImagePath(workCardPath);
        imageReqModel.setImageType("WORK_CARD");
        NetworkPhRequest.submitApplyFileInfo(imageReqModel, applyPh, new Callback<BasePhModel>() {
            @Override
            public void onResponse(Call<BasePhModel> call, Response<BasePhModel> response) {
                closeLoadingDialog();
                pNextRecord.setEndTime();
                BasePhModel res = response.body();
                String code = res.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    Intent intent = new Intent(activity, ConfirmLoanActivity.class);
                    NetworkPhRequest.getUserStep(applyPh, new PhSubscriber<>());
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_PAY_INFO_SIGN, activity, TokenUtils.getOrderId(activity));
                    startActivity(intent);
                    //BehaviorPhUtils.saveBehaviorReqModel(bfModel);
                    finish();
                } else {
                    showToast(res.getMsg());
                }
            }

            @Override
            public void onFailure(Call<BasePhModel> call, Throwable throwable) {
                closeLoadingDialog();
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
//                confirmAmount(true, apply);
            }
        });
        modifyAmountDialog.setCancelListener(new ModifyAmountDialog.OnCancelClick() {
            @Override
            public void OnCancelClick() {
                maxAmount = modifyAmountDialog.getMaxAmount();
                productCode = modifyAmountDialog.getProductCode();
                apply.setMaxApplyAmount(maxAmount);
                apply.setProductCode(productCode);
                commit(apply);
            }
        });
        modifyAmountDialog.show(getSupportFragmentManager(), "2");
    }

//    private void confirmAmount(final boolean showDialog, final ApplyReqPhModel apply) {
//        NetworkPhRequest.updateAmount(apply, new PhSubscriber<BasePhModel>() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                showLoadingDialog();
//            }
//
//            @Override
//            public void onNext(BasePhModel baseModel) {
//                super.onNext(baseModel);
//                closeLoadingDialog();
//                if ("000".equals(baseModel.getStatus().getCode())) {
//                    if (showDialog) {
//                        showAgreementDialog(true, apply);
//                    }
//                } else if (baseModel.getStatus() != null) {
//                    showToast(baseModel.getStatus().getMsg());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                closeLoadingDialog();
//            }
//        });
//    }

    private void uploadAppInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AppPhInfo> appPhInfos = new ArrayList<>();
                for (AppUtils.AppInfo appInfo : AppUtils.getAppsInfo()) {
                    AppPhInfo appPhInfoReqModel = new AppPhInfo();
                    appPhInfoReqModel.setAppName(appInfo.getName());
                    appPhInfoReqModel.setPkgName(appInfo.getPackageName());
                    appPhInfoReqModel.setPackagePath(appInfo.getPackagePath());
                    appPhInfoReqModel.setVersionCode(appInfo.getVersionCode());
                    appPhInfoReqModel.setVersionName(appInfo.getVersionName());
                    if (!appInfo.isSystem()) {
                        appPhInfos.add(appPhInfoReqModel);
                    }
                }
                AppInfoReqModel infoReqBean = new AppInfoReqModel();
                infoReqBean.setApplyId(spPhUtil.getLong(PhConstants.PhUser.APPLY_ID, 0L));
                infoReqBean.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
                infoReqBean.setAppPhInfos(appPhInfos);
//                NetworkPhRequest.uploadAppInfo(infoReqBean, new PhSubscriber<BasePhModel>() {
//                    @Override
//                    public void onNext(BasePhModel basePhModel) {
//                        super.onNext(basePhModel);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                    }
//                });
            }
        }).start();

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
                            if (dictionary != null) {
                                switch (value) {
                                    case "bank":
                                        // 银行名称
                                        BehaviorPhUtils.setChangeModify(bfModel, "P06_C01_S_BANKNAME", bankCodePh, dictionary.getCode());
                                        bankNamePh = dictionary.getName();
                                        bankCodePh = dictionary.getCode();
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
//                                                String c = basePhModel.getCode();
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
                                if (popupWindow != null) popupWindow.dismiss();
                            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_WORK_CARD && resultCode == PhCameraActivity.RESULT_CODE && data != null) {
            p06CompanyID.setEndTime();
            p06CompanyID.setOldValue(workCardPath);
            workCardPath = data.getStringExtra(PhCameraActivity.IMAGE_PATH);
            p06CompanyID.setNewValue(workCardPath);
            Bitmap bitmap = BitmapFactory.decodeFile(workCardPath);
            Glide.with(activity).load(bitmap).into(ivWorkPreview);
            /*ImagePhReqModel imageReqModel = new ImagePhReqModel();
            imageReqModel.setApplyId(TokenUtils.getApplyId(activity));
            imageReqModel.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
            imageReqModel.setImagePath(workCardPath);
            imageReqModel.setImageType("WORK_CARD");
            NetworkPhRequest.applyUploadOnePhoto(imageReqModel, new Callback<PhotoImagePhModel>() {
                @Override
                public void onResponse(@NotNull Call<PhotoImagePhModel> call, @NotNull Response<PhotoImagePhModel> response) {
                    PhotoImagePhModel respBean = response.body();
                    closeLoadingDialog();
                    if (respBean != null && respBean.getCode() != null && CODE_SUCCESS.equals(respBean.getCode())) {
                        PhotoImagePhModel.PhotoImage body = respBean.getData();
                        workCardImageId = body.getImageId();
                    } else if (respBean != null && respBean.getCode() != null) {
                        showToast(respBean.getMsg());
                    }
                }

                @Override
                public void onFailure(Call<PhotoImagePhModel> call, Throwable t) {
                    closeLoadingDialog();
                    showToast(R.string.error_request_fail);
                }
            });*/
        }
    }

    /**
     * 发送验证码按钮开始倒计时
     */
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
                if (tvPhGetCode != null) {
                    tvPhCode.setVisibility(View.GONE);
                    tvPhGetCode.setText(PhUtils.format(getString(R.string.get_code_hint), (millisUntilFinished / 1000)));
                }
            }

            @Override
            public void onFinish() {
                if (tvPhGetCode != null) {
                    tvPhGetCode.setEnabled(true);
                    tvPhGetCode.setClickable(true);
                    tvPhGetCode.setTextColor(getResources().getColor(R.color.white));
                    tvPhGetCode.setText(R.string.reacquire_code);
                    tvPhCode.setVisibility(View.VISIBLE);
                }
                isCountFinished = true;
            }
        };
        if (tvPhGetCode != null) {
            tvPhGetCode.setEnabled(false);
            tvPhGetCode.setClickable(false);
        }
        timer.start();
    }

    private void stopCount() {
        if (null != timer) {
            timer.cancel();
        }
        if (tvPhGetCode != null) {
            tvPhGetCode.setEnabled(true);
            tvPhGetCode.setClickable(true);
            tvPhGetCode.setText(R.string.get_code);
        }
        isCountFinished = true;
    }

    private SpannableString getFirstWordRed(String text) {
        SpannableString style = new SpannableString(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        return style;
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

    /**
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logCompleteRegistrationEvent() {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD, "COMPLETED_REGISTRATION");
        AppEventsLogger.newLogger(activity).logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, params);
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

    @Override
    public void onBackPressed() {
        showSurveyDialog();
    }

    private void showSurveyDialog() {
        SurveyDialog dialog = new SurveyDialog();
        dialog.setStep("5");
        dialog.setLeaveListener(new SurveyDialog.OnLeaveClick() {
            @Override
            public void OnLeaveClick(String content) {
                if (TextUtils.isEmpty(content)) {
                    showToast("Please select");
                    return;
                }
                NetworkPhRequest.submitUserSurvey(TokenUtils.getToken(activity), "5",
                        content, new PhSubscriber<BasePhModel>() {
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
                                    dialog.dismiss();
                                    finish();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                closeLoadingDialog();
                            }
                        });
            }
        });
        dialog.show(getSupportFragmentManager(), "1");
    }

    /*
     * 实体类转换JSON
     * */
    public String jsonbody(Object object) {
        return new Gson().toJson(object, Object.class);
    }

}
