package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.OrderDetailModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.FilePhUtils;
import com.u1.gocashm.util.PermissionPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by jishudiannao on 2018/9/10.
 */

public class OrderDetailPhActivity extends BasePhTitleBarActivity {

    private static final String TAG = OrderDetailPhActivity.class.getSimpleName();
    @BindView(R.id.tv_orderdetail_name)
    TextView tvPhName;
    @BindView(R.id.tv_orderdetail_pay_code)
    TextView tvPhPayCode;
    @BindView(R.id.tv_orderdetail_number)
    TextView tvPhNumber;
    @BindView(R.id.tv_orderdetail_amount)
    TextView tvPhAmount;
    @BindView(R.id.tv_orderdetail_term)
    TextView tvPhTerm;
    @BindView(R.id.tv_orderdetail_date)
    TextView tvPhDate;
    @BindView(R.id.tv_orderdetail_paymentdate)
    TextView tvPhPaymentDate;
    @BindView(R.id.tv_orderdetail_payment)
    TextView tvPhPayment;
    @BindView(R.id.tv_orderdetail_status)
    TextView tvPhStatus;
    @BindView(R.id.bt_orderdetail_roll)
    Button btPhRoll;
    @BindView(R.id.bt_orderdetail_repayment)
    Button btPhRepayment;
    @BindView(R.id.ll_orderdetail_repay)
    LinearLayout llPhRepay;
    @BindView(R.id.ll_orderdetail_apply)
    LinearLayout llPhApply;
    @BindView(R.id.ll_orderdetail_modify)
    LinearLayout llPhModify;

    private String contractNoPh;
    private boolean showBtnPh;
    private String loanStatusPh;

    private Activity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        activity = this;
        ButterKnife.bind(this);
        initTitleBar();
        initView();
    }

    private void initView() {
        contractNoPh = getIntent().getStringExtra(PhConstants.CONTRACTNO);
        showBtnPh = getIntent().getBooleanExtra(PhConstants.CONTRACTSHOWBTN, false);
        getContractDetail(contractNoPh);
    }

    @OnClick({R.id.bt_orderdetail_roll, R.id.bt_orderdetail_repayment, R.id.bt_orderdetail_apply})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_orderdetail_roll:
//                delay();
                break;
            case R.id.bt_orderdetail_repayment:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P13_C_Repayment");
                repay();
                break;
            case R.id.bt_orderdetail_apply:
                apply();
                break;
        }
    }

    private void downloadContract() {
        PermissionPhUtils.requestStoragePermission(activity);
        String accessToken = SharedPreferencesPhUtil.getInstance(this).getString(PhConstants.PhUser.ACCESS_TOKEN);
        NetworkPhRequest.getContractDownload(contractNoPh, accessToken, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                Log.e(TAG, e.getMessage());
                showToast(getString(R.string.error_request_fail));
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                String code = basePhModel.getCode();
                closeLoadingDialog();
                if (CODE_SUCCESS.equals(code)) {
                    String pdfPath = FilePhUtils.getSdPath(true) + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + contractNoPh + ".pdf";
                    File pdfFile = new File(pdfPath);
                    if (pdfFile.exists()) {
                        pdfFile.delete();
                    }
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(basePhModel.getData().toString()));
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, contractNoPh + ".pdf");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                    showToast(getString(R.string.get_contract_download));
                } else {
                    showToast(basePhModel.getMsg());
                }
            }
        });
    }

    private void getContractDetail(String contractNo) {
        NetworkPhRequest.getOrderDetail(TokenUtils.getToken(activity), contractNo, new PhSubscriber<OrderDetailModel>() {
            @Override
            public void onNext(OrderDetailModel orderDetailModel) {
                super.onNext(orderDetailModel);
                closeLoadingDialog();
                String code = orderDetailModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    OrderDetailModel.OrderDetail contractDetail = orderDetailModel.getData();
                    if (contractDetail.getUser() != null) {
                        tvPhName.setText(contractDetail.getUser().getFullname());
                    }
                    tvPhPayCode.setText(contractDetail.getReference_no());
                    tvPhNumber.setText(String.valueOf(contractDetail.getOrder_no()));
                    tvPhAmount.setText(getString(R.string.loan_how_much) + contractDetail.getPaid_amount());
                    tvPhTerm.setText(String.valueOf(contractDetail.getLoan_days()));
                    tvPhDate.setText(contractDetail.getCreated_at());
                    tvPhPaymentDate.setText(contractDetail.getAppointment_paid_time());
                    tvPhPayment.setText(getString(R.string.loan_how_much) + contractDetail.getReceivable_amount());
                    tvPhStatus.setText(contractDetail.getApi_status());
                    if (showBtnPh) {
                        String loanStatus = getStatus(contractDetail.getStatus());
                        if (loanStatus.equals("REPAY")) {
                            llPhRepay.setVisibility(View.VISIBLE);
                        } else if (loanStatus.equals("SETTLE") || loanStatus.equals("LOAN_CANCEL") || loanStatus.equals("CANCEL")) {
                            loanStatusPh = loanStatus;
                            if (loanStatus.equals("LOAN_CANCEL")) {
                                loanStatusPh = "CANCEL";
                            }
                            llPhApply.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    ToastUtils.showShort(orderDetailModel.getMsg());
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
                showToast(R.string.error_request_fail);
                Log.e(TAG, e.getMessage());
            }
        });
    }

    private void repay() {
//        startActivity(new Intent(this, RepayPhActivity.class));
    }

    private void apply() {
        RxBus.getDefault().post(new TagEvent(EventTagPh.RE_APPLY, loanStatusPh));
//        Intent intent = new Intent(this, MainPhActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        finish();
    }

    private String getStatus(String status) {
        String loanStatus = null;
        switch (status) {
            case "wait_system_approve":
            case "wait_manual_approve":
            case "system_approving":
            case "sign":
            case "wait_call_approve":
            case "wait_twice_call_approve":
            case "manual_pass":
            case "system_pass":
                loanStatus = "APPROVE";
                break;
            case "create":
                loanStatus = "SIGN";
                break;
            case "system_reject":
            case "manual_reject":
                loanStatus = "REJECTED";
                break;
            case "replenish_bank":
            case "system_pay_fail":
            case "replenish":
                loanStatus = "REPLENISH";
                break;
            case "paying":
                loanStatus = "LOAN";
                break;
            case "system_paid":
            case "manual_paid":
            case "repaying":
                loanStatus = "REPAY";
                break;
            case "manual_pay_fail":
            case "manual_cancel":
            case "user_cancel":
            case "system_cancel":
                loanStatus = "CANCEL";
                break;
            case "overdue":
            case "collection_bad":
                loanStatus = "OVERDUE";
                break;
            case "finish":
            case "overdue_finish":
                loanStatus = "SETTLE";
                break;
        }
        return loanStatus;
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.loan_detail);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P13_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P13_Leave";
    }

    @Override
    protected String getRecordBackKey() {
        return "P13_C_Back";
    }
}
