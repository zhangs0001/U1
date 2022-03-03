package com.u1.gocashm.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AppsFlyerLib;
import com.blankj.utilcode.util.ToastUtils;
import com.facebook.appevents.AppEventsLogger;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.MainPhActivity;
import com.u1.gocashm.activity.WebPhActivity;
import com.u1.gocashm.activity.loan.ContinuedLoanPhActivity;
import com.u1.gocashm.activity.loan.LoanIdentityPhActivity;
import com.u1.gocashm.adapter.LoanTermAdapter;
import com.u1.gocashm.fragment.base.BasePhFragment;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.ContinueLoanPhReqModel;
import com.u1.gocashm.model.request.JpushReqModel;
import com.u1.gocashm.model.request.RecordPhModel;
import com.u1.gocashm.model.request.ScheduleCalcPhReqModel;
import com.u1.gocashm.model.response.ApplyIndexPhModel;
import com.u1.gocashm.model.response.ContinueLoanPhModel;
import com.u1.gocashm.model.response.GoogleGpsPhModel;
import com.u1.gocashm.model.response.LoanCountPhModel;
import com.u1.gocashm.model.response.LoanTermModel;
import com.u1.gocashm.model.response.NoticeModel;
import com.u1.gocashm.model.response.ProductPhModel;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.AppPhUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.FireBaseUtil;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.util.listener.MyPhoneStateListener;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.util.model.DeviceInfo;
import com.u1.gocashm.view.dialog.VirusDialog;
import com.u1.gocashm.view.dialog.WhiteListDialog;
import com.u1.gocashm.view.dialog.WhiteListShowDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static android.content.Context.SENSOR_SERVICE;
import static android.view.View.VISIBLE;
import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by jishudiannao on 2018/9/3.
 */

public class LoanPhFragment extends BasePhFragment {

    @BindView(R.id.sb_loan_balance)
    SeekBar sbPhBalance;
    @BindView(R.id.tv_loan_amountmin)
    TextView tvPhAmountMin;
    @BindView(R.id.tv_loan_amountmax)
    TextView tvPhAmountMax;
    @BindView(R.id.tv_loan_amount)
    TextView tvPhAmount;
    @BindView(R.id.tv_loan_duedate)
    TextView tvPhDueDate;
    @BindView(R.id.tv_loan_totalamount)
    TextView tvPhTotalAmount;
//    @BindView(R.id.et_loan_name1)
//    EditText etPhName1;
//    @BindView(R.id.et_loan_name2)
//    EditText etPhName2;
//    @BindView(R.id.et_loan_name3)
//    EditText etPhName3;
//    @BindView(R.id.et_loan_phone)
//    EditText etPhPhone;
    @BindView(R.id.tv_loan_messages)
    TextView tvPhMessage;
    @BindView(R.id.ll_loan_term)
    LinearLayout llPhTerm;
//    @BindView(R.id.tv_loan_name1)
//    TextView tvPhName1;
//    @BindView(R.id.tv_loan_name3)
//    TextView tvPhName3;
//    @BindView(R.id.tv_loan_phone)
//    TextView tvPhPhone;
//    @BindView(R.id.ll_loan_info)
//    LinearLayout llPhInfo;
    @BindView(R.id.tv_loan_activity_hint)
    TextView tvLoanActivityHint;
    @BindView(R.id.get_amount)
    LinearLayout getAmount;
    @BindView(R.id.tv_loan_total_amount)
    TextView tvLoanTotalAmount;
    @BindView(R.id.tv_apply_date)
    TextView tvApplyDate;
    @BindView(R.id.tv_first_period_date)
    TextView tvFirstPeriodDate;
    @BindView(R.id.tv_first_period_amount)
    TextView tvFirstPeriodAmount;
    @BindView(R.id.tv_second_period_date)
    TextView tvSecondPeriodDate;
    @BindView(R.id.tv_second_period_amount)
    TextView tvSecondPeriodAmount;
    @BindView(R.id.ll_repay_info)
    LinearLayout llRepayInfo;
    @BindView(R.id.loan_term_recyclerView)
    RecyclerView loanTermRecyclerView;
    @BindView(R.id.ll_loan_preview)
    LinearLayout llLoanPreview;
    @BindView(R.id.ll_loan_layout)
    LinearLayout llLoanLayout;
    Unbinder unbinder;
    private View view;
    private ViewGroup parent;
    private ProductPhModel.Product mProduct;
    private int amountPh, termPh;
    private int amountStep;
    private int loanCount, maxAmountPh;
    private Drawable newThumb;
    private boolean flag_apply;
    // 行为数据model
    private BehaviorPhReqModel bfModel;
    private Disposable disposable;
    private String firstName;
    private String middleName;
    private String lastName;
    private WhiteListShowDialog whiteListShowDialog;
    private boolean isConfirmWhiteList;
    private String phone;
    private String loanStatus;
    private String[] gps;
    private RecordPhModel recordPhModel;
    private ScheduleCalcPhModel.ScheduleCalc scheduleCalc;
    private LoanTermAdapter termAdapter;
    private SensorManager MyManage;
    private MySensorListener sensorListener;
    private String name;
    private IntentFilter ifilter;
    private boolean isCharging;
    private int level;
    private DeviceInfo deviceInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("zhangs", "onCreateView: LoanPhFragment" );
        // 启动首页
        bfModel = BehaviorPhUtils.setEnterPageModify("P01_C00", getActivity());
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_loan, null);
            unbinder = ButterKnife.bind(this, view);
            initData();
            initView();
            initRecyclerView();
        } else {
            parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        loanTermRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
//        loanCount = 0;
//        getIsContinuedLoan();
//        llPhInfo.setVisibility(TokenUtils.TokenCheck(getActivity()) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getIsContinuedLoan();
            showNoticeDialog();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        maxAmountPh = 12000;
        loanCount = 0;
//        getIsContinuedLoan();
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (tagEvent.getTag().equals(EventTagPh.APPLY_SUCCESS)) {
//                    if (etPhName1 != null && etPhName2 != null && etPhName3 != null && etPhPhone != null) {
//                        etPhName1.setText("");
//                        etPhName2.setText("");
//                        etPhName3.setText("");
//                        etPhPhone.setText("");
//                    }
                }
            }
        });
        gps = new String[1];
        NetworkPhRequest.getGoogleGps(new PhSubscriber<GoogleGpsPhModel>() {

            @Override
            public void onNext(GoogleGpsPhModel gpsModel) {
                super.onNext(gpsModel);
                GoogleGpsPhModel.LocationBean location1 = gpsModel.getLocation();
                if (location1 != null) {
                    gps[0] = location1.getLat() + "," + location1.getLng();
                }
            }
        });
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getActivity().registerReceiver(null, ifilter);
        level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        MyPhoneStateListener myphonelister = new MyPhoneStateListener();
        TelephonyManager Tel = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        Tel.listen(myphonelister, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS); //实现监听
        deviceInfo = new DeviceInfo();
        MyManage = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        firstName = SharedPreferencesPhUtil.getInstance(getActivity()).getString(PhConstants.PhUser.FIRST_NAME);
    }

    private void initView() {
        overshootAnimation(getAmount);
        initSeekBar();
        tvLoanActivityHint.setText(getString(R.string.loan_activity_hint1));
        tvLoanActivityHint.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvLoanActivityHint.setSingleLine(true);
        tvLoanActivityHint.setFocusable(true);
        tvLoanActivityHint.setFocusableInTouchMode(true);
        tvLoanActivityHint.setSelected(true);
        tvLoanActivityHint.setMarqueeRepeatLimit(-1);
        sbPhBalance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 更改贷款金额
                if (mProduct != null) {
                    BehaviorPhUtils.setChangeModify(bfModel, "P01_C02_S_LOANAMT", amountPh + "", (mProduct.minAmount + amountStep * progress) + "");
//                    amountPh = mProduct.minAmount + amountStep * progress;
                    tvPhAmount.setText(String.format(getActivity().getString(R.string.php_logo), PhUtils.setMoney(amountPh)));
//                    if (loanCount == 0) {
                    Rect bounds = sbPhBalance.getProgressDrawable().getBounds();
//                    if (amountPh <= mProduct.maxAmount) {
//                        sbPhBalance.setSecondaryProgress(0);
//                        sbPhBalance.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar));
////                            sbPhBalance.setThumb(newThumb_orange);
//                        tvPhMessage.setVisibility(View.INVISIBLE);
//                    } else {
//                        sbPhBalance.setSecondaryProgress((mProduct.maxAmount - mProduct.minAmount) / amountStep);
//                        sbPhBalance.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_secondary));
////                            sbPhBalance.setThumb(newThumb_orange);
//                        tvPhMessage.setText(PhUtils.format(getString(R.string.loan_amount_message), (PhUtils.setMoney(mProduct.maxAmount))));
//                        tvPhMessage.setVisibility(VISIBLE);
//                    }
                    sbPhBalance.getProgressDrawable().setBounds(bounds);
//                    }
                    if (termPh != 0) {
                        scheduleCalcPh();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        etPhName1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    String inputString = s.toString();
//                    String firstLetterCapString = WordPhUtil.capitalize(inputString);
//                    if (!firstLetterCapString.equals(inputString)) {
//                        etPhName1.setText(firstLetterCapString);
//                        etPhName1.setSelection(etPhName1.getText().length());
//                    }
//                    addNameErrMsg(inputString, 1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        etPhName2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    String inputString = s.toString();
//                    String firstLetterCapString = WordPhUtil.capitalize(inputString);
//                    if (!firstLetterCapString.equals(inputString)) {
//                        etPhName2.setText(firstLetterCapString);
//                        etPhName2.setSelection(etPhName2.getText().length());
//                    }
//                    addNameErrMsg(inputString, 2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        etPhName3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    String inputString = s.toString();
//                    String firstLetterCapString = WordPhUtil.capitalize(inputString);
//                    if (!firstLetterCapString.equals(inputString)) {
//                        etPhName3.setText(firstLetterCapString);
//                        etPhName3.setSelection(etPhName3.getText().length());
//                    }
//                    addNameErrMsg(inputString, 3);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        etPhPhone.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
//                    etPhPhone.setText(sb.toString());
//                    etPhPhone.setSelection(index);
//                }
//                addPhoneErrMsg(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//        // 姓名输入框监听
//        AFPhListener.getInstance().addEditTextListener(etPhName1, bfModel, "P01_C04_I_NAME");
//        AFPhListener.getInstance().addEditTextListener(etPhName2, bfModel, "P01_C04_I_NAME");
//        AFPhListener.getInstance().addEditTextListener(etPhName3, bfModel, "P01_C04_I_NAME");
        // 手机号输入框监听
//        AFPhListener.getInstance().addEditTextListener(etPhPhone, bfModel, "P01_C05_I_PHONE");
//        getFocus(etPhName1);
//        getFocus(etPhName2);
//        getFocus(etPhName3);
//        getFocus(etPhPhone);
    }

    @OnClick({R.id.bt_loan_apply, R.id.tv_loan_agreement, R.id.get_amount})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_loan_apply:
                // 申请按钮
                BehaviorPhUtils.setClickModify(bfModel, "P01_C06_B_APPLY");
                applyIndexPh();
                break;
            case R.id.tv_loan_agreement:
                // 服务协议按钮
                BehaviorPhUtils.setClickModify(bfModel, "P01_C07_I_SERVICE");
                Intent intent = new Intent(getActivity(), WebPhActivity.class);
                intent.putExtra(PhConstants.WEB_TITLE, getResources().getString(R.string.agreement_title));
                intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_AGREEMENT);
                startActivity(intent);
                break;
            case R.id.get_amount:
                showEstimateDialog();
                break;
        }
    }

    private void showEstimateDialog() {
        final WhiteListDialog dialog = new WhiteListDialog();
        dialog.setOnClick(new WhiteListDialog.OnClick() {
            @Override
            public void OnClick() {
                final String phoneNum = dialog.getPhoneNum();
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtils.showShort(R.string.login_phone_hint);
                    return;
                }
                if (!VerifyUtil.checkPhone(phoneNum)) {
                    ToastUtils.showShort(R.string.error_phone_no_9);
                    return;
                }
                NetworkPhRequest.getProduct(TokenUtils.getToken(getActivity()), new PhSubscriber<ProductPhModel>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoadingDialog();
                    }

                    @Override
                    public void onNext(ProductPhModel productPhModel) {
                        super.onNext(productPhModel);
                        closeLoadingDialog();
                        String code = productPhModel.getCode();
                        if (CODE_SUCCESS.equals(code)) {
                            mProduct = productPhModel.getData();
                            showView();
//                            if (amountPh == mProduct.maxAmount) {
//                                Rect bounds = sbPhBalance.getProgressDrawable().getBounds();
//                                sbPhBalance.setSecondaryProgress(0);
//                                sbPhBalance.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar));
//                                tvPhMessage.setVisibility(View.INVISIBLE);
//                                sbPhBalance.getProgressDrawable().setBounds(bounds);
//                            }
//                            etPhPhone.setText(phoneNum);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        closeLoadingDialog();
                        ToastUtils.showShort(R.string.error_request_fail);
                        dialog.dismiss();

                    }
                });
            }
        });
        dialog.show(getFragmentManager(), "1");
    }

    private void getIsContinuedLoan() {
        NetworkPhRequest.getIsContinuedLoan(new PhSubscriber<LoanCountPhModel>() {
            @Override
            public void onNext(LoanCountPhModel loanCountPhModel) {
                super.onNext(loanCountPhModel);
                String code = loanCountPhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    loanCount = loanCountPhModel.getData().loanCount;
                    if (loanCount == 1) {
                        tvLoanActivityHint.setText(getString(R.string.loan_activity_hint2));
                    } else if (loanCount >= 2) {
                        tvLoanActivityHint.setText(getString(R.string.loan_activity_hint3));
                    }
                } else {
                    llLoanLayout.setVisibility(View.GONE);
                    llLoanPreview.setVisibility(VISIBLE);
                    tvLoanActivityHint.setText(getString(R.string.loan_activity_hint1));
                    loanCount = 0;
                }
                getProduct(loanCount);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                llLoanLayout.setVisibility(View.GONE);
                llLoanPreview.setVisibility(VISIBLE);
                loanCount = 0;
                getProduct(loanCount);
            }
        });
    }

    private void showNoticeDialog() {
        NetworkPhRequest.getNotice(new PhSubscriber<NoticeModel>() {

            @Override
            public void onNext(NoticeModel noticeModel) {
                super.onNext(noticeModel);
                String code = noticeModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    NoticeModel.Notice body = noticeModel.getData();
                    if (body != null) {
                        VirusDialog dialog = new VirusDialog();
                        dialog.setTitle(body.getTitle());
                        dialog.setContent(body.getNotification());
                        dialog.show(getFragmentManager(), "1");
                    }
                }
            }
        });
    }

    private void getProduct(int loanCount) {
        MainPhActivity activity = (MainPhActivity) getActivity();
        if (activity != null) {
            loanStatus = activity.getLoanStatus();
            if (TextUtils.isEmpty(loanStatus)) {
                loanStatus = "REGISTER";
            }
        }
//        llPhInfo.setVisibility(TokenUtils.TokenCheck(getActivity()) ? View.GONE : VISIBLE);
//        if (TextUtils.isEmpty(phone)) {
        llRepayInfo.setVisibility(loanCount > 0 ? VISIBLE : View.GONE);
        llLoanLayout.setVisibility(loanCount > 0 ? VISIBLE : View.GONE);
        llLoanPreview.setVisibility(loanCount > 0 ? View.GONE : VISIBLE);
        if ("CANCEL".equals(loanStatus)) {
            llLoanPreview.setVisibility(View.GONE);
            llLoanLayout.setVisibility(VISIBLE);
            llRepayInfo.setVisibility(VISIBLE);
        } else if ("REJECTED".equals(loanStatus) || "APPLY".equals(loanStatus)) {
            llLoanPreview.setVisibility(VISIBLE);
            llLoanLayout.setVisibility(View.GONE);
            llRepayInfo.setVisibility(View.GONE);
        }
//        else if ("REGISTER".equals(loanStatus)) {
//            llPhInfo.setVisibility(VISIBLE);
//        }
//        if (TextUtils.isEmpty(firstName)) {
//            llPhInfo.setVisibility(VISIBLE);
//        }
        phone = TokenUtils.getPhone(getActivity());
//        if (!TextUtils.isEmpty(phone)) {
//            etPhPhone.setText(phone);
//        }
//        }
//        llPhInfo.setVisibility(loanCount == 0 ? View.VISIBLE : View.GONE);
        NetworkPhRequest.getProduct(TokenUtils.getToken(getActivity()), new PhSubscriber<ProductPhModel>() {
            @Override
            public void onNext(ProductPhModel productPhModel) {
                super.onNext(productPhModel);
                String code = productPhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    mProduct = productPhModel.getData();
//                    amountStep = mProduct.getAmountStep();
                    showView();
                    setRecyclerView();
                }
            }
        });
    }

    private void setRecyclerView() {
        List<LoanTermModel> list = new ArrayList<>();
        for (LoanTermModel termModel : mProduct.getLoanTerms()) {
            if (termModel.isAvailable()) {
                list.add(termModel);
            }
        }
        if (list.size() != 0) {
            mProduct.getLoanTerms().get(mProduct.getLoanTerms().indexOf(list.get(list.size() - 1))).setSelected(true);
        }
        termAdapter = new LoanTermAdapter(R.layout.item_loan_term_layout, mProduct.getLoanTerms());
        termAdapter.setOnItemClick(new LoanTermAdapter.ItemClick() {
            @Override
            public void ItemClick(int loanTerm) {
                termPh = loanTerm;
                showLoadingDialog();
                scheduleCalcPh();
            }
        });
        loanTermRecyclerView.setAdapter(termAdapter);
        termPh = termAdapter.getLoanTerm();
        scheduleCalcPh();
    }

    private void scheduleCalcPh() {
        ScheduleCalcPhReqModel model = new ScheduleCalcPhReqModel();
//        model.loanAmt = amountPh + "";
//        model.loanCode = mProduct.loanCode;
//        model.loanDay = termPh + "";
//        model.productCode = mProduct.productCode;
        NetworkPhRequest.scheduleCalc(model, new PhSubscriber<ScheduleCalcPhModel>() {
            @Override
            public void onNext(ScheduleCalcPhModel scheduleCalcPhModel) {
                super.onNext(scheduleCalcPhModel);
                closeLoadingDialog();
                String code = scheduleCalcPhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    scheduleCalc = scheduleCalcPhModel.getData();
//                    if (scheduleCalc.schedules.size() > 1) {
//                        llRepayInfo.setVisibility(View.GONE);
//                        List<ScheduleCalcPhModel.ScheduleCalc.Schedule> scheduleList = scheduleCalc.schedules;
//                        ScheduleCalcPhModel.ScheduleCalc.Schedule schedule1 = scheduleList.get(0);
//                        ScheduleCalcPhModel.ScheduleCalc.Schedule schedule2 = scheduleList.get(1);
//                        tvLoanTotalAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(scheduleCalc.actualAmount)));
//                        tvApplyDate.setText(scheduleCalc.applyDate);
//                        tvFirstPeriodAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(schedule1.totalAmt)));
//                        tvFirstPeriodDate.setText(schedule1.loanPmtDueDate);
//                        tvSecondPeriodAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(schedule2.totalAmt)));
//                        tvSecondPeriodDate.setText(schedule2.loanPmtDueDate);
//                    } else {
//                        llRepayInfo.setVisibility(View.GONE);
//                        tvPhDueDate.setText(scheduleCalcPhModel.getData().loanPmtDueDate);
//                        tvPhTotalAmount.setText(String.format(getActivity().getString(R.string.php_logo), PhUtils.setMoney(scheduleCalcPhModel.getData().totalAmt)));
//                    }
                }
                if (flag_apply) {
                    flag_apply = false;
                    applyIndexPh();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

    private void applyIndexPh() {
        if (mProduct == null) {
            ToastUtils.showShort(R.string.error_request_fail);
            return;
        }
        MainPhActivity activity = (MainPhActivity) getActivity();
        if (activity != null) {
            loanStatus = activity.getLoanStatus();
        }
        if ("APPLY".equals(loanStatus) || "REJECTED".equals(loanStatus) || "REGISTER".equals(loanStatus)) {
//            if (amountPh > mProduct.maxAmount) {
//                flag_apply = true;
//                sbPhBalance.setProgress((mProduct.maxAmount - mProduct.minAmount) / amountStep);
//                return;
//            }
//            if (llPhInfo.getVisibility() == VISIBLE) {
////                firstName = etPhName1.getText().toString().trim();
////                middleName = etPhName2.getText().toString().trim();
////                lastName = etPhName3.getText().toString().trim();
////                inputClearFocus(etPhName1);
////                inputClearFocus(etPhName2);
////                inputClearFocus(etPhName3);
//                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
//                    addNameErrMsg(firstName, 1);
//                    addNameErrMsg(lastName, 3);
//                    if (tvPhName1.getVisibility() == VISIBLE || tvPhName3.getVisibility() == VISIBLE)
//                        return;
//                }
//                if (!TextUtils.isEmpty(firstName)) {
//                    if (TextUtils.isEmpty(middleName)) {
//                        name = firstName + " " + lastName;
//                    } else {
//                        name = firstName + " " + middleName + " " + lastName;
//                    }
//                }
//            }
            final ApplyReqPhModel model = new ApplyReqPhModel();
            if (loanCount != 0) {
                model.setApplyId(TokenUtils.getApplyId(getActivity()));
            }
            model.setApplyAmount(amountPh);
            model.setLoanTerms(termPh);
//            model.setMaxApplyAmount(mProduct.maxAmount);
            model.setProductCode(mProduct.productCode);
            model.setName(name);
            model.setFirstname(firstName);
            model.setMiddlename(middleName);
            model.setLastname(lastName);
            model.setWhiteListConfirmFlag(isConfirmWhiteList ? "Y" : "N");
            model.setSource("APP");
            model.setChannel("UPESO");
            model.setUserId(TokenUtils.getUserId(getActivity()));
            model.setAppsFlyerId(AppsFlyerLib.getInstance().getAppsFlyerUID(getActivity()));
            String gpsInfo = AppPhUtils.getGpsInfo();
            if (!"0,0".equals(gpsInfo)) {
                model.setGps(gpsInfo);
                next(model);
            } else {
                model.setGps(gps[0]);
                next(model);
            }
        } else {
            loanContinuePh();
        }
    }

    private void next(ApplyReqPhModel model) {
        BehaviorPhUtils.setSensorValue(bfModel, "P01_C08_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        NetworkPhRequest.applyIndex(model, new PhSubscriber<ApplyIndexPhModel>() {
            @Override
            public void onNext(ApplyIndexPhModel applyIndexPhModel) {
                super.onNext(applyIndexPhModel);
                closeLoadingDialog();
                String code = applyIndexPhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    SharedPreferencesPhUtil instance = SharedPreferencesPhUtil.getInstance(getActivity());
                    AppEventsLogger.newLogger(getActivity()).logEvent(PhConstants.FaceBookEvent.EVENT_LOAN_APPLY);
                    if (instance.getLong(PhConstants.AGREEMENT_INIT_TIME, 0L) != 0L) {
                        recordPhModel = new RecordPhModel();
                        recordPhModel.setId("P01_C10_B_AGREEMENT");
                        recordPhModel.setStartTime(BehaviorPhUtils.format.format(new Date(instance.getLong(PhConstants.AGREEMENT_INIT_TIME, 0L))));
                        recordPhModel.setEndTime(BehaviorPhUtils.format.format(new Date(instance.getLong(PhConstants.AGREEMENT_END_TIME, 0L))));
                        bfModel.getRecords().add(recordPhModel);
                    }
                    bfModel.setApplyId(applyIndexPhModel.getData().applyId + "");
                    BehaviorPhUtils.saveBehaviorReqModel(bfModel);
//                    String totalAmount = tvPhTotalAmount.getText().toString().replace("₱ ", "");
                    instance.setCalcModel(scheduleCalc);
//                    instance.saveLoanInfo(amountPh, scheduleCalc.loanPmtDueDate, String.valueOf(scheduleCalc.totalAmt), applyIndexPhModel.getData().applyId, termPh, mProduct.productCode, phone);
                    instance.saveString(PhConstants.APPLYNAME, name);
                    instance.saveString(PhConstants.PhUser.FIRST_NAME, firstName);
                    instance.saveInt(PhConstants.LOAN_COUNT, loanCount);
                    AppsFlyerLib.getInstance().setCustomerUserId(String.valueOf(applyIndexPhModel.getData().applyId));
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_LOAN_APPLY, getActivity(), TokenUtils.getOrderId(getActivity()));
                    FireBaseUtil.fireBaseEvent(getActivity(), PhConstants.FaceBookEvent.EVENT_LOAN_APPLY, applyIndexPhModel.getData().applyId);
                    uploadJpushInfo("");
                    Intent intent = new Intent(getActivity(), LoanIdentityPhActivity.class);
                    startActivity(intent);
                } else  {
                    ToastUtils.showShort(applyIndexPhModel.getMsg());
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

//    private void addNameErrMsg(String name, int type) {
//        if (type == 1) {
//            tvPhName1.setVisibility(name.isEmpty() ? VISIBLE : View.GONE);
//        } else if (type == 3) {
//            tvPhName3.setVisibility(name.isEmpty() ? VISIBLE : View.GONE);
//        }
//    }
//
//    private void addPhoneErrMsg(String phone) {
//        if (TextUtils.isEmpty(phone)) {
//            tvPhPhone.setVisibility(VISIBLE);
//            tvPhPhone.setText(R.string.login_phone_hint);
//        } else if (!VerifyUtil.checkPhone(phone.replaceAll(" ", ""))) {
//            tvPhPhone.setVisibility(VISIBLE);
//            tvPhPhone.setText(R.string.error_phone_no_9);
//        } else {
//            tvPhPhone.setVisibility(View.GONE);
//        }
//    }

    private void initSeekBar() {
        Resources res = getResources();
        Drawable thumb = res.getDrawable(R.drawable.seekbar_btn);
//        Drawable thumb_orange = res.getDrawable(R.drawable.seekbar_btn_orange);
        int h = PhUtils.dip2px(getActivity(), 16) * 2;
        int w = h;
        Bitmap bmpOrg = ((BitmapDrawable) thumb).getBitmap();
        Bitmap bmpScaled = Bitmap.createScaledBitmap(bmpOrg, w, h, true);
//        Bitmap bmpOrg_orange = ((BitmapDrawable) thumb_orange).getBitmap();
//        Bitmap bmpScaled_orange = Bitmap.createScaledBitmap(bmpOrg_orange, w, h, true);
        newThumb = new BitmapDrawable(res, bmpScaled);
        newThumb.setBounds(0, 0, newThumb.getIntrinsicWidth(), newThumb.getIntrinsicHeight());
//        newThumb_orange = new BitmapDrawable(res, bmpScaled_orange);
//        newThumb_orange.setBounds(0, 0, newThumb_orange.getIntrinsicWidth(), newThumb_orange.getIntrinsicHeight());
        Drawable newThumb2 = new BitmapDrawable(res, bmpScaled);
        newThumb2.setBounds(0, 0, newThumb2.getIntrinsicWidth(), newThumb2.getIntrinsicHeight());
        sbPhBalance.setThumb(newThumb);
    }

    private void showView() {
        tvPhAmountMin.setText(PhUtils.setMoney(mProduct.minAmount));
        tvPhAmountMax.setText(PhUtils.setMoney(maxAmountPh));
//        sbPhBalance.setMax((maxAmountPh - mProduct.minAmount) / amountStep);
//
//        sbPhBalance.setProgress((mProduct.maxAmount - mProduct.minAmount) / amountStep);
//        scheduleCalcPh();
    }

    private void loanContinuePh() {
//        if (amountPh > mProduct.maxAmount) {
//            flag_apply = true;
//            sbPhBalance.setProgress((mProduct.maxAmount - mProduct.minAmount) / amountStep);
//            return;
//        }
        final String token = TokenUtils.getToken(getActivity());
        final ContinueLoanPhReqModel loanReqModel = new ContinueLoanPhReqModel();
        loanReqModel.setLoanAmt(BigDecimal.valueOf(amountPh));
        loanReqModel.setLoanTerms(termPh);
        loanReqModel.setLoanCount(loanCount);
//        loanReqModel.setMaxApplyAmount(mProduct.maxAmount);
        loanReqModel.setProductCode(mProduct.productCode);
        loanReqModel.setSource("APP");
        loanReqModel.setChannel("UPESO");
        String gpsInfo = AppPhUtils.getGpsInfo();
        if (!"0,0".equals(gpsInfo)) {
            loanReqModel.setGps(gpsInfo);
            nextLoan(token, loanReqModel);
        } else {
            loanReqModel.setGps(gps[0]);
            nextLoan(token, loanReqModel);
        }
    }

    private void nextLoan(String token, ContinueLoanPhReqModel loanReqModel) {
        NetworkPhRequest.continuedLoan(token, loanReqModel, new PhSubscriber<ContinueLoanPhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(ContinueLoanPhModel continueLoanModel) {
                super.onNext(continueLoanModel);
                closeLoadingDialog();
                String code = continueLoanModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    AppEventsLogger.newLogger(getActivity()).logEvent(PhConstants.FaceBookEvent.EVENT_LOAN_CONTINUE);
                    ContinueLoanPhModel.Body body = continueLoanModel.getData();
                    SharedPreferencesPhUtil preferencesUtil = SharedPreferencesPhUtil.getInstance(getContext());
//                    preferencesUtil.saveLoanInfo(amountPh, scheduleCalc.loanPmtDueDate, String.valueOf(scheduleCalc.totalAmt), body.getApply().getApplyId(), termPh, mProduct.productCode, body.getApply().getPhone());
                    preferencesUtil.saveLong(PhConstants.PhUser.APPLY_ID, body.getApply().getApplyId());
                    preferencesUtil.setCalcModel(scheduleCalc);
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_LOAN_CONTINUE, getActivity(), TokenUtils.getOrderId(getActivity()));
                    FireBaseUtil.fireBaseEvent(getActivity(), PhConstants.FaceBookEvent.EVENT_LOAN_CONTINUE, body.getApply().getApplyId());
                    uploadJpushInfo(body.getApply().getIdcard());
                    startActivity(new Intent(getActivity(), ContinuedLoanPhActivity.class));
                } else {
                    PhUtils.showShortToast(getContext(), continueLoanModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

    private void inputClearFocus(EditText editText) {
        editText.clearFocus();
        editText.setFocusable(false);
    }

    @SuppressLint("ClickableViewAccessibility")
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

//    private void updateDeviceInfo(long applyId, String idCard, String phone) {
//        String advertisId = "";
//        deviceInfo.setBatteryStatus(isCharging ? "Charging" : "unCharging");
//        deviceInfo.setBatteryPower(String.valueOf(level));
//        deviceInfo.setSignalStrength(SharedPreferencesPhUtil.getInstance(getActivity()).getString(PhConstants.PHONE_DBM));
//        try {
//            AppPhUtils.updateDevice(deviceInfo, getActivity(), advertisId, AppPhUtils.getScreenWidthAndHeight(getActivity()),
//                    sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void uploadJpushInfo(String idcard) {
        JpushReqModel model = new JpushReqModel();
        model.setRegistrationId(JPushInterface.getRegistrationID(getActivity()));
        model.setDeviceType("android");
        model.setCustId(idcard);
        model.setDeviceId(DevicePhUtil.getDeviceUnionID(getActivity()));
        model.setAppName(getString(R.string.app_name));
        UploadInfoUtils.saveJpushInfo(model);
    }

    @Override
    public void onDestroyView() {
        BehaviorPhUtils.setDestroyModify(bfModel, "P01_C99");
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * @param view
     */
    public static void overshootAnimation(View view) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 10, -10);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);
    }
}
