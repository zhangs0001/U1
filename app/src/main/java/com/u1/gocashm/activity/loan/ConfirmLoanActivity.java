package com.u1.gocashm.activity.loan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.adapter.LoanTermAdapter;
import com.u1.gocashm.model.request.AppInfoReqModel;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.request.DdiReqModel;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.request.RecordPhModel;
import com.u1.gocashm.model.request.ScheduleCalcPhReqModel;
import com.u1.gocashm.model.request.UpdateOrderReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.CouponModel;
import com.u1.gocashm.model.response.DDIRespModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.model.response.LoanResultPhModel;
import com.u1.gocashm.model.response.LoanTermModel;
import com.u1.gocashm.model.response.LocationModel;
import com.u1.gocashm.model.response.ProductPhModel;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.ApkTool;
import com.u1.gocashm.util.AppPhUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.GpsPhUtils;
import com.u1.gocashm.util.GsonPhHelper;
import com.u1.gocashm.util.PhUtils;
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
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.dialog.CreditRatingDialog;
import com.u1.gocashm.view.dialog.HaveCouponDialog;
import com.u1.gocashm.view.dialog.LoanAgreementDialog;
import com.u1.gocashm.view.dialog.SurveyDialog;
import com.u1.gocashm.view.popupwindow.SelectCouponPopupWindow;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.shuzilm.core.Listener;
import cn.shuzilm.core.Main;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2019/12/11 0011 下午 1:58
 */
public class ConfirmLoanActivity extends BasePhTitleBarActivity {

    @BindView(R.id.tv_loan_amount)
    TextView tvLoanAmount;
    @BindView(R.id.bs_loan_amount)
    BubbleSeekBar bsLoanAmount;
    @BindView(R.id.tv_loan_amount_min)
    TextView tvLoanAmountMin;
    @BindView(R.id.tv_loan_amount_max)
    TextView tvLoanAmountMax;
    @BindView(R.id.tv_loan_messages)
    TextView tvLoanMessages;
    @BindView(R.id.loan_term_recyclerView)
    RecyclerView loanTermRecyclerView;
    @BindView(R.id.checkbox_agreement)
    CheckBox checkboxAgreement;
    @BindView(R.id.tv_agreement_hint)
    TextView tvAgreementHint;
    @BindView(R.id.input_phone_time)
    InputView inputPhoneTime;
    @BindView(R.id.input_coupon)
    InputView inputCoupon;

    @BindView(R.id.tv_dalily_cost_amount)
    TextView tv_dalily_cost_amount;
    @BindView(R.id.id_inltial_amount_due)
    TextView id_inltial_amount_due;
    @BindView(R.id.id_discount_amount)
    TextView id_discount_amount;

    @BindView(R.id.tv_use_coupon_title)
    View tv_use_coupon_title;
    @BindView(R.id.id_loan_calc_layout_2)
    View id_loan_calc_layout_2;

    @BindView(R.id.tv_loan_second_due_amount)
    TextView tv_loan_second_due_amount;
    @BindView(R.id.tv_loan_second_due_date)
    TextView tv_loan_second_due_date;

    @BindView(R.id.tv_loan_first_due_amount)
    TextView tv_loan_first_due_amount;
    @BindView(R.id.tv_loan_first_due_date)
    TextView tv_loan_first_due_date;

    @BindView(R.id.tv_loan_totalamount)
    TextView tvLoanTotalamount;
    @BindView(R.id.tv_loan_duedate)
    TextView tvLoanDuedate;


    private Activity activity;
    private ProductPhModel.Product mProduct;
    private LoanTermAdapter termAdapter;
    private Integer term;
    private Integer amount;
    private SharedPreferencesPhUtil spUtils;
    private MySensorListener sensorListener;
    private SensorManager MyManage; //新建sensor的管理器
    private IntentFilter ifilter;
    private boolean isCharging;
    private int level;
    private DeviceInfo deviceInfo;
    private BehaviorPhReqModel bfModel;
    private ApplyReqPhModel apply;
    private LoanAgreementDialog agreementDialog;
    private RecordPhModel recordPhModel;
    private SelectListPopupWindow popupWindow;
    private SelectCouponPopupWindow counponPopupWindow;
    private String phoneTimeCode;
    private CouponModel.Coupon mSelectCoupon;
    private List<CouponModel.Coupon> mCoupons = new ArrayList<>();
    private Integer minAmount;
    private Integer maxAmount;
    private Integer amountStep;
    private String nativePhone;
    private String accessToken;
    private boolean isContinue;

    /**
     * 服务费，取款手续费，放款金额
     */
    private TextView tvServiceFee;
    private TextView tvWithdrawalServiceFee;
    private TextView tvDisbursmentAmount;
    private BehaviorRecord p07CAgreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_loan);
        ButterKnife.bind(this);
        activity = this;
        initData();
        initView();
    }

    private void initData() {
//        product = (ProductPhModel.Product) getIntent().getSerializableExtra(PhConstants.PRODUCT);
//        apply = (ApplyReqPhModel) getIntent().getSerializableExtra(PhConstants.APPLY_INFO);
//        amount = product.maxAmount;
        mSelectCoupon = null;
        mCoupons.clear();
        spUtils = SharedPreferencesPhUtil.getInstance(activity);
        accessToken = spUtils.getString(PhConstants.PhUser.ACCESS_TOKEN);
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        MyPhoneStateListener myphonelister = new MyPhoneStateListener();
        TelephonyManager Tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            nativePhone = Tel.getLine1Number();
        }
        Tel.listen(myphonelister, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS); //实现监听
        deviceInfo = new DeviceInfo();
        MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        bfModel = BehaviorPhUtils.setEnterPageModify("P08_C00", activity);
        isContinue = spUtils.getBoolean(PhConstants.IS_CONTINUE);
        getProduct();
    }

    private void getProduct() {
        NetworkPhRequest.getProduct(TokenUtils.getToken(activity), new PhSubscriber<ProductPhModel>() {
            @Override
            public void onNext(ProductPhModel productRcModel) {
                super.onNext(productRcModel);
                String code = productRcModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    mProduct = productRcModel.getData();
                    amount = Double.valueOf(mProduct.maxAmount).intValue();
                    minAmount = Double.valueOf(mProduct.minAmount).intValue();
                    maxAmount = Double.valueOf(mProduct.maxAmount).intValue();
                    amountStep = Integer.parseInt(mProduct.getAmountStep());
                    tvLoanAmount.setText(getString(R.string.loan_how_much, PhUtils.setMoney(amount)));
                    tvLoanAmountMax.setText(getString(R.string.loan_how_much, PhUtils.setMoney(amount)));
                    tvLoanAmountMin.setText(getString(R.string.loan_how_much, PhUtils.setMoney(minAmount)));
                    showView();
                    setRecyclerView();
                }
            }
        });
    }

    private void initView() {
        tvServiceFee = findViewById(R.id.id_loan_calc_sf);
        tvWithdrawalServiceFee = findViewById(R.id.id_loan_calc_wsf);
        tvDisbursmentAmount = findViewById(R.id.id_loan_calc_da);

        initRecyclerView();
        initListener();
    }

    private void initListener() {
        bsLoanAmount.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                // 更改贷款金额
                if (mProduct != null) {
                    BehaviorPhUtils.setChangeModify(bfModel, "P08_C05_S_LOAN_AMOUNT", amount + "", (mProduct.minAmount + amountStep * progress) + "");
                    BehaviorPhUtils.setChangeModifyV2(mBehaviorRecord, "P07_S_LoanAmount", amount + "", (mProduct.minAmount + amountStep * progress) + "");

                    if (minAmount.equals(maxAmount)) {
                        amount = maxAmount;
                    } else {
                        amount = progress;
                    }
                    tvLoanAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(amount)));
                    int color;
                    if (amount > Double.parseDouble(mProduct.maxAmount)) {
                        color = ContextCompat.getColor(activity, R.color.orange);
                        tvLoanMessages.setText(PhUtils.format(getString(R.string.loan_amount_message), (PhUtils.setMoney(mProduct.maxAmount))));
                        tvLoanMessages.setVisibility(View.VISIBLE);
                    } else {
                        color = ContextCompat.getColor(activity, R.color.seek_orange);
                        tvLoanMessages.setVisibility(View.GONE);
                    }
                    bubbleSeekBar.setSecondTrackColor(color);
                    mSelectCoupon = null;
                    inputCoupon.getTv_content().setText(null);
                    scheduleCalc();
                }
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        p07CAgreement = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P07_C_Agreement");
        checkboxAgreement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                p07CAgreement.setStartTime();
                p07CAgreement.setOldValue(checkboxAgreement.isChecked() ? "Agree" : "Reject");
                return false;
            }
        });
        checkboxAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                p07CAgreement.setEndTime();
                p07CAgreement.setOldValue(isChecked ? "Agree" : "Reject");
                if (isChecked) {
                    tvAgreementHint.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showView() {
        bsLoanAmount.getConfigBuilder()
                .min(minAmount)
                .max(maxAmount)
                .progress(Integer.parseInt(mProduct.getMaxViewAmount()))
                .sectionCount((maxAmount - minAmount) / amountStep)
                .trackColor(ContextCompat.getColor(activity, R.color.text_main))
                .secondTrackColor(ContextCompat.getColor(activity, R.color.seek_orange))
                .seekStepSection()
                .touchToSeek()
                .hideBubble()
                .build();
        if (maxAmount.equals(minAmount)) {
            bsLoanAmount.setEnabled(false);
        }
        tvLoanAmountMin.setText(PhUtils.setMoney(mProduct.minAmount));
        tvLoanAmountMax.setText(PhUtils.setMoney(maxAmount));
    }

    private void setRecyclerView() {
        List<LoanTermModel> list = new ArrayList<>();
        for (LoanTermModel term : mProduct.getLoanTerms()) {
            LoanTermModel model = new LoanTermModel();
            model.setLoanTerm(term.getLoanTerm());
            model.setAvailable(true);
            list.add(model);
        }
        if (list.size() != 0) {
            list.get(list.size() - 1).setSelected(true);
            term = list.get(list.size() - 1).getLoanTerm();
        }
        termAdapter = new LoanTermAdapter(R.layout.item_loan_term_layout, list);
        termAdapter.setOnItemClick(new LoanTermAdapter.ItemClick() {
            @Override
            public void ItemClick(int loanTerm) {
                BehaviorPhUtils.setChangeModify(bfModel, "P08_C04_S_LOAN_TERM", term + "", termAdapter.getLoanTerm() + "");
                BehaviorPhUtils.setChangeModifyV2(mBehaviorRecord, "P07_S_LoanTerm", term + "", termAdapter.getLoanTerm() + "");
                term = loanTerm;
                scheduleCalc();
            }
        });
        loanTermRecyclerView.setAdapter(termAdapter);
        scheduleCalc();
    }


    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
        loanTermRecyclerView.setLayoutManager(layoutManager);
    }

    private void scheduleCalc() {
        if (term == null) {
            return;
        }

        if (mSelectCoupon == null) {
            //选择额度的时候清除之前已经选择的优惠券，并重新获取可用的优惠券
            showCouponPopupWindow(String.valueOf(amount), null);
        }

        ScheduleCalcPhReqModel model = new ScheduleCalcPhReqModel();
        model.loanAmt = String.valueOf(amount);
        model.loanCode = mProduct.loanCode;
        model.loanDay = String.valueOf(term);
        model.productCode = mProduct.productCode;
        model.token = TokenUtils.getToken(activity);
        model.coupon_id = mSelectCoupon == null ? "" : String.valueOf(mSelectCoupon.getCoupon_id());
        NetworkPhRequest.scheduleCalc(model, new PhSubscriber<ScheduleCalcPhModel>() {
            @Override
            public void onNext(ScheduleCalcPhModel scheduleCalcThModel) {
                super.onNext(scheduleCalcThModel);
                String code = scheduleCalcThModel.getCode();
                if (CODE_SUCCESS.equals(code)) {

                    List<ScheduleCalcPhModel.ScheduleCalc.RepaymentPlansBean> repaymentPlans = scheduleCalcThModel.getData().getRepaymentPlans();
                    if (repaymentPlans != null) {
                        try {
                            for (ScheduleCalcPhModel.ScheduleCalc.RepaymentPlansBean repayment_plan : repaymentPlans) {
                                if (repayment_plan.getInstallment_num() == 1) {
                                    tv_loan_first_due_date.setText(repayment_plan.getRepayDate());
                                    tv_loan_first_due_amount.setText(getString(R.string.loan_how_much) + repayment_plan.getRepayAmount());
                                } else if (repayment_plan.getInstallment_num() == 2) {
                                    tv_loan_second_due_date.setText(repayment_plan.getRepayDate());
                                    tv_loan_second_due_amount.setText(getString(R.string.loan_how_much) + repayment_plan.getRepayAmount());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    tvLoanDuedate.setText(scheduleCalcThModel.getData().getLoanPmtDueDate());
                    tvLoanTotalamount.setText(getString(R.string.loan_how_much) + scheduleCalcThModel.getData().getPeriod_amount());


                    tvLoanAmount.setText(getString(R.string.loan_how_much) + scheduleCalcThModel.getData().getLoan_amount());

                    tvServiceFee.setText(PhUtils.setMoney(scheduleCalcThModel.getData().getService_charge()));
                    tvWithdrawalServiceFee.setText(PhUtils.setMoney(scheduleCalcThModel.getData().getWithdrawalServiceCharge()));
                    tvDisbursmentAmount.setText(PhUtils.setMoney(scheduleCalcThModel.getData().getActual_mount()));

                    tv_dalily_cost_amount.setText(PhUtils.setMoney(scheduleCalcThModel.getData().getDalily_cost()));
                    try {
                        double replaymentAmout = 0;
                        for (ScheduleCalcPhModel.ScheduleCalc.RepaymentPlansBean repaymentPlan : scheduleCalcThModel.getData().getRepaymentPlans()) {
                            replaymentAmout += Double.parseDouble(repaymentPlan.getRepayAmount());
                        }
                        id_inltial_amount_due.setText(PhUtils.setMoney(replaymentAmout));
                    } catch (Exception e) {
                    }
                    id_discount_amount.setText(PhUtils.setMoney(scheduleCalcThModel.getData().getDiscount_amount()));
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(getString(R.string.error_request_fail));
            }
        });
    }

    @Override
    public void initTitleBar() {
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        setTitleBack(R.string.app_name);
    }

    @OnClick({R.id.agreement_layout, R.id.tv_loan_confirm, R.id.input_phone_time, R.id.input_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.agreement_layout:
                toAgreement();
                break;
            case R.id.tv_loan_confirm:
                submit();
                break;
            case R.id.input_phone_time:
                getPhoneTime();
                break;
            case R.id.input_coupon:
                showCouponPopupWindow(String.valueOf(amount), inputCoupon.getTv_content());
                break;
        }
    }

    private void updateAmount() {
        BehaviorPhUtils.setClickModify(bfModel, "P08_C02_I_LOAN_CONTRACT");
        UpdateOrderReqModel reqModel = new UpdateOrderReqModel();
        reqModel.setPrincipal(String.valueOf(amount));
        reqModel.setLoan_days(term);
        reqModel.setToken(accessToken);
        NetworkPhRequest.updateOrder(reqModel, new PhSubscriber<BasePhModel>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel baseModel) {
                super.onNext(baseModel);
                String code = baseModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    toAgreement();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

    private void toAgreement() {
        NetworkPhRequest.getContractPreview(String.valueOf(term), String.valueOf(amount), TokenUtils.getToken(activity), new PhSubscriber<BasePhModel>() {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                showToast(getString(R.string.error_request_fail));
            }

            @Override
            public void onNext(BasePhModel baseModel) {
                super.onNext(baseModel);
                closeLoadingDialog();
                String code = baseModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    agreementDialog = new LoanAgreementDialog();
                    agreementDialog.setLoanContractUrl(baseModel.getData().toString());
                    agreementDialog.setOnClick(new LoanAgreementDialog.OnClick() {
                        @Override
                        public void OnClick() {
                            getAgreementBehavior(true);
                        }
                    });
                    agreementDialog.setOnCancelClick(new LoanAgreementDialog.OnCancelClick() {
                        @Override
                        public void OnCancelClick() {
                            getAgreementBehavior(false);
                        }
                    });
                    agreementDialog.show(getSupportFragmentManager(), "1");
                } else {
                    showToast(baseModel.getMsg());
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

    private void getPhoneTime() {
        showPopupWindow("CAN_CONTACT_TIME ", "phoneTimeType", inputPhoneTime.getTv_content());
    }

    private void showPopupWindow(String code, final String value, final TextView textView) {
        NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
            @Override
            public void onNext(DictionaryPhModel dictionaryModel) {
                super.onNext(dictionaryModel);
                String modelCode = dictionaryModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    popupWindow = new SelectListPopupWindow(activity, dictionaryModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                            if (dictionary == null) {
                                return;
                            }
                            switch (value) {
                                case "phoneTimeType":
                                    // 证件类型
                                    phoneTimeCode = dictionary.getName();
                                    break;
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

    private void showCouponPopupWindow(final String amount, final TextView textView) {
        ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
        reqPhModel.setToken(TokenUtils.getToken(activity));
        reqPhModel.setLoan_amount(amount);
        NetworkPhRequest.getMyEffectiveCoupon(reqPhModel, new PhSubscriber<CouponModel>() {
            @Override
            public void onStart() {
                super.onStart();
                if (textView != null) {
                    showLoadingDialog();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (textView != null) {
                    closeLoadingDialog();
                }
            }

            @Override
            public void onNext(CouponModel couponModel) {
                super.onNext(couponModel);
                if (textView != null) closeLoadingDialog();
                String modelCode = couponModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    mCoupons.clear();
                    mCoupons.addAll(couponModel.getData());
                    if (mCoupons.isEmpty()) {
                        inputCoupon.setHint("No coupon yet");
                        inputCoupon.setSelEnabled(false);
                        tv_use_coupon_title.setVisibility(View.GONE);
                        id_loan_calc_layout_2.setVisibility(View.GONE);
                        inputCoupon.setVisibility(View.GONE);
                    } else {
                        tv_use_coupon_title.setVisibility(View.VISIBLE);
                        id_loan_calc_layout_2.setVisibility(View.VISIBLE);
                        inputCoupon.setVisibility(View.VISIBLE);
                        inputCoupon.setSelEnabled(true);
                        if (textView != null) {
                            counponPopupWindow = new SelectCouponPopupWindow(activity, mCoupons, new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    CouponModel.Coupon coupon = (CouponModel.Coupon) adapter.getItem(position);
                                    if (coupon == null) {
                                        return;
                                    }
                                    mSelectCoupon = coupon;
                                    textView.setText(coupon.getTitle());
                                    if (counponPopupWindow != null) counponPopupWindow.dismiss();

                                    scheduleCalc();
                                }
                            });
                            if (counponPopupWindow.isShowing())
                                counponPopupWindow.dismiss();
                            else {
                                counponPopupWindow.showPopupWindow(textView);
                            }
                        }
                    }
                }
            }
        });

    }

    private void submit() {
        if (amount > maxAmount) {
            bsLoanAmount.setProgress(maxAmount);
            return;
        }
        if (TextUtils.isEmpty(phoneTimeCode)) {
            inputPhoneTime.setVerify(R.string.get_phone_time_hint);
            return;
        } else {
            inputPhoneTime.setVerify("");
        }
        if (mSelectCoupon == null && !mCoupons.isEmpty()) {
            HaveCouponDialog dialog = new HaveCouponDialog();
            dialog.setConfirmListener(new HaveCouponDialog.OnConfrimClick() {
                @Override
                public void OnConfirmClick(String content) {
                    doSubmit();
                }
            });
            dialog.show(getSupportFragmentManager(), "1");
        } else {
            doSubmit();
        }


    }

    private void doSubmit() {
        if (!checkboxAgreement.isChecked()) {
            tvAgreementHint.setVisibility(View.VISIBLE);
            return;
        }
        BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P07_C_Submit");
        BehaviorPhUtils.setClickModify(bfModel, "P08_C03_I_SIGN");
        BehaviorPhUtils.setSensorValue(bfModel, "P08_C20_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        UpdateOrderReqModel reqModel = new UpdateOrderReqModel();
        reqModel.setToken(TokenUtils.getToken(activity));
        reqModel.setLoan_days(term);
        reqModel.setPrincipal(String.valueOf(amount));
        reqModel.setCan_contact_time(phoneTimeCode);
        reqModel.setLoan_reason(spUtils.getString(PhConstants.PhJobInfo.LOAN_INTENT));
        LocationInfo locationInfo = new LocationInfo();
        Location location = GpsPhUtils.getLocation();
        if (location != null) {
            locationInfo.setLatitude(String.valueOf(location.getLatitude()));
            locationInfo.setLongitude(String.valueOf(location.getLongitude()));
        }
        reqModel.setPosition(GsonPhHelper.getGson().toJson(locationInfo));
        ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
        reqPhModel.setStep_name("");
        reqPhModel.setToken(accessToken);
        NetworkPhRequest.sign(reqModel, new PhSubscriber<BasePhModel>() {

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
                    AppEventsLogger.newLogger(activity).logEvent(PhConstants.FaceBookEvent.EVENT_PAY_INFO_SIGN);
                    logCompleteRegistrationEvent();
                    // 保存行为数据
//                    ApplyModel.ApplyBean body = basePhModel.getData();
//                    Session session = body.getSession();
//                    Apply bodyApply = body.getApply();
                    SharedPreferencesPhUtil.getInstance(activity).clearApplyData();
                    NetworkPhRequest.getUserStep(reqPhModel, new PhSubscriber<>());
//                    if (session != null) {
//                        User userPh = session.getUser();
//                        TokenUtils.setToken(activity, session.getAccessToken());
//                        SharedPreferencesPhUtil.getInstance(activity).login(session.getAccessToken(), body.getApply().getApplyId(), userPh.getUserId(), userPh.getPhone(), bodyApply.getName(), bodyApply.getIdcard(), userPh.getFirstName());
                    deviceInfo.setBatteryStatus(isCharging ? "Charging" : "unCharging");
                    deviceInfo.setBatteryPower(String.valueOf(level));
                    deviceInfo.setSignalStrength(spUtils.getString(PhConstants.PHONE_DBM));
                    deviceInfo.setPhone(nativePhone);
                    ThreadUtil.submit(() -> AppPhUtils.uploadDeviceInfo(TokenUtils.getToken(activity), deviceInfo, activity, AppPhUtils.getScreenWidth(activity), AppPhUtils.getScreenHeight(activity)
                            , sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez()));
                    if (isContinue) {
                        AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_CONTINUE_SIGN, activity, TokenUtils.getOrderId(activity));
                    } else {
                        AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_FIRST_SIGN, activity, TokenUtils.getOrderId(activity));
                    }
                    ThreadUtil.submit(() -> uploadAppInfo());
                    RxBus.getDefault().post(new TagEvent(EventTagPh.APPLY_SUCCESS, mSelectCoupon));

                    ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
                    reqPhModel.setToken(TokenUtils.getToken(activity));
                    NetworkPhRequest.getLastOrder(reqPhModel, new PhSubscriber<LoanResultPhModel>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            showLoadingDialog();
                        }

                        @Override
                        public void onNext(LoanResultPhModel loanResultPhModel) {
                            super.onNext(loanResultPhModel);
                            closeLoadingDialog();
                            String code = loanResultPhModel.getCode();
                            if (code.equals(CODE_SUCCESS)) {
                                LoanResultPhModel.LoanResult data = loanResultPhModel.getData();
                                if (data.getIt_service_signed() == 0 && data.getIt_service_need() == 1) {
                                    CreditRatingDialog creditRatingDialog = new CreditRatingDialog();
                                    creditRatingDialog.setOnClick(() -> finish());
                                    creditRatingDialog.show(getSupportFragmentManager(), "1");
                                } else {
                                    finish();
                                }

                            } else {
                                finish();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            closeLoadingDialog();
                            finish();
                        }
                    });
                } else {
                    showToast(basePhModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                ToastUtils.showShort(R.string.error_request_fail);
            }
        });
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


    private void uploadAppInfo() {
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

    private void getDDIinfo(long applyId) {
        Main.getQueryID(activity, "Upeso_App", String.valueOf(applyId), 1, new Listener() {
            @Override
            public void handler(String s) {
                DdiReqModel reqModel = new DdiReqModel();
                reqModel.setDid(s);
//                reqModel.setPkg(BuildConfig.APPLICATION_ID);
//                reqModel.setVer(BuildConfig.VERSION_NAME);
                reqModel.setProtocol(2);
                NetworkPhRequest.getDDIinfo(reqModel, new PhSubscriber<DDIRespModel>() {
                    @Override
                    public void onNext(DDIRespModel ddiRespModel) {
                        super.onNext(ddiRespModel);
                    }
                });
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyManage.unregisterListener(sensorListener);
    }

    @Override
    public void onBackPressed() {
        showSurveyDialog();
    }

    private void showSurveyDialog() {
        SurveyDialog dialog = new SurveyDialog();
        dialog.setStep("6");
        dialog.setLeaveListener(new SurveyDialog.OnLeaveClick() {
            @Override
            public void OnLeaveClick(String content) {
                if (TextUtils.isEmpty(content)) {
                    showToast("Please select");
                    return;
                }
                NetworkPhRequest.submitUserSurvey(TokenUtils.getToken(activity), "6",
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

    @Override
    protected List<BehaviorRecord> getRecords() {
        return new ArrayList<BehaviorRecord>() {{
            add(inputPhoneTime.getBehaviorRecord());
            add(inputCoupon.getBehaviorRecord());
            add(p07CAgreement);
        }};
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P07_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P07_Leave";
    }

    @Override
    protected String getRecordBackKey() {
        return "P07_C_Back";
    }
}
