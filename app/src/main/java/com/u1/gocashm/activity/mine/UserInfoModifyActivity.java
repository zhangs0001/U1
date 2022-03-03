package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.activity.loan.IdCardPhActivity;
import com.u1.gocashm.activity.loan.JobInfoPhActivity;
import com.u1.gocashm.activity.loan.LoanAddressPhActivity;
import com.u1.gocashm.activity.loan.LoanIdentityPhActivity;
import com.u1.gocashm.activity.loan.PayInfoPhActivity;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.ScheduleCalcPhReqModel;
import com.u1.gocashm.model.response.Apply;
import com.u1.gocashm.model.response.ApplyModel;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.model.response.UserInfoDetailModel;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by jishudiannao on 2018/9/10.
 */

public class UserInfoModifyActivity extends BasePhTitleBarActivity {

    @BindView(R.id.ll_userinfomodify_identity)
    LinearLayout llPhIdentity;
    @BindView(R.id.ll_userinfomodify_address)
    LinearLayout llPhAddress;
    @BindView(R.id.ll_userinfomodify_job)
    LinearLayout llPhJob;
    @BindView(R.id.ll_userinfomodify_idcard)
    LinearLayout llPhIdcard;
    @BindView(R.id.ll_userinfomodify_pay)
    LinearLayout llPhPay;
    @BindView(R.id.tv_loan_amount)
    TextView tvPhAmount;
    @BindView(R.id.tv_loan_date)
    TextView tvPhDate;
    @BindView(R.id.tv_loan_payment)
    TextView tvPhPayment;
    @BindView(R.id.bt_submit)
    Button btPhSubmit;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    private SharedPreferencesPhUtil spPhUtil;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfomodify);
        ButterKnife.bind(this);
        activity = this;
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPhUserInfoDetail();
    }

    private void initView() {
        spPhUtil = SharedPreferencesPhUtil.getInstance(this);
        Apply apply = spPhUtil.getApply();
        if (apply == null) return;
        tvPhAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(apply.getApplyAmount())));
        ScheduleCalcPhReqModel modelPh = new ScheduleCalcPhReqModel();
//        modelPh.loanAmt = apply.getApplyAmount() + "";
//        modelPh.loanCode = apply.getLoanCode();
//        modelPh.loanDay = apply.getLoanTerms() + "";
//        modelPh.productCode = apply.getProductCode();
        NetworkPhRequest.scheduleCalc(modelPh, new PhSubscriber<ScheduleCalcPhModel>() {
            @Override
            public void onNext(ScheduleCalcPhModel scheduleCalcPhModel) {
                super.onNext(scheduleCalcPhModel);
                String code = scheduleCalcPhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    tvPhDate.setText(scheduleCalcPhModel.getData().getLoanPmtDueDate());
                    tvPhPayment.setText(String.format(getString(R.string.php_logo), PhUtils.setMoney(scheduleCalcPhModel.getData().getPeriod_amount())));
                }
            }
        });
        tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAgreement();
            }
        });
    }

    @OnClick({R.id.ll_userinfomodify_identity, R.id.ll_userinfomodify_address, R.id.ll_userinfomodify_job, R.id.ll_userinfomodify_idcard, R.id.ll_userinfomodify_pay, R.id.bt_submit})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_userinfomodify_identity:
                intent.setClass(this, LoanIdentityPhActivity.class);
                break;
            case R.id.ll_userinfomodify_address:
                intent.setClass(this, LoanAddressPhActivity.class);
                break;
            case R.id.ll_userinfomodify_job:
                intent.setClass(this, JobInfoPhActivity.class);
                break;
            case R.id.ll_userinfomodify_idcard:
                intent.setClass(this, IdCardPhActivity.class);
                break;
            case R.id.ll_userinfomodify_pay:
                intent.setClass(this, PayInfoPhActivity.class);
                break;
            case R.id.bt_submit:
                submit();
                break;
        }
        if (view.getId() != R.id.bt_submit) {
            intent.putExtra(PhConstants.SOURCE, PhConstants.SOURCE_MODIFY);
            startActivity(intent);
        }
    }

    private void toAgreement() {
        String accessToken = spPhUtil.getString(PhConstants.PhUser.ACCESS_TOKEN);
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
//                showToast(getString(R.string.error_request_fail));
//            }
//
//            @Override
//            public void onNext(BasePhModel basePhModel) {
//                super.onNext(basePhModel);
//                closeLoadingDialog();
//                String code = basePhModel.getCode();
//                if (CODE_SUCCESS.equals(code)) {
//                    Intent intent = new Intent(activity, WebPhActivity.class);
//                    intent.putExtra(PhConstants.WEB_URL, basePhModel.getData().toString());
//                    intent.putExtra(PhConstants.WEB_TITLE, getString(R.string.click_agreement_contract));
//                    startActivity(intent);
//                } else {
//                    showToast(basePhModel.getMsg());
//                }
//            }
//        });
    }

    private void submit() {
        showLoadingDialog();
        ApplyReqPhModel applyReqPhModelPh = new ApplyReqPhModel();
        // TODO: 2018/9/22  加参数
        applyReqPhModelPh.setSource("APP");
        applyReqPhModelPh.setChannel("UCASH");
        NetworkPhRequest.applyByReject(applyReqPhModelPh, new PhSubscriber<ApplyModel>() {
            @Override
            public void onNext(ApplyModel applyModel) {
                super.onNext(applyModel);
                closeLoadingDialog();
                String code = applyModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    RxBus.getDefault().post(new TagEvent(EventTagPh.MODIFY_SUCCESS));
                    finish();
                } else {
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

    private void getPhUserInfoDetail() {
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
                    SharedPreferencesPhUtil.getInstance(UserInfoModifyActivity.this).setUserInfo(userInfoDetailModel.getData());
                } else  {
                    showToast(userInfoDetailModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.info_title);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }
}
