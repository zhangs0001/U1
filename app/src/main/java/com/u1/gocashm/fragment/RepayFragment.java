package com.u1.gocashm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.BankCardRepaymentActivity;
import com.u1.gocashm.activity.PaymentStatusActivity;
import com.u1.gocashm.fragment.base.BasePhFragment;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorRecordReqModel;
import com.u1.gocashm.model.request.FawryBean;
import com.u1.gocashm.model.response.LoanResultPhModel;
import com.u1.gocashm.model.response.ProductPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.view.dialog.ReimbursementCodesDialog;
import com.u1.gocashm.view.dialog.RepaymentAmountDialog;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/9/29 0029 下午 5:11
 */
public class RepayFragment extends BasePhFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fawryButton)
    ImageView fawryButton;
    @BindView(R.id.fawry_click)
    LinearLayout fawryClick;
    @BindView(R.id.tv_payment_status)
    TextView tvPaymentStatus;


    private View mRootView;
    private BehaviorRecordReqModel behaviorRecordReqModel;
    private String applyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_repay, null);
            unbinder = ButterKnife.bind(this, mRootView);
            initView();
            initData();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            behaviorRecordReqModel = BehaviorPhUtils.setEnterPageModifyV2("P14_Enter", getContext());
            initView();
            initData();
        } else {
            if (behaviorRecordReqModel != null) {
                BehaviorPhUtils.setClickModifyV2(behaviorRecordReqModel, "P14_Leave");
                BehaviorPhUtils.saveBehaviorReqModelV2(behaviorRecordReqModel);
            }
        }
    }

    private void initView() {

    }


    @Override
    protected void initData() {
        super.initData();
        String token = TokenUtils.getToken(getActivity());
        ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
        reqPhModel.setToken(token);
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
                    applyId = data.getApplyId();
                } else {
                    ToastUtils.showLong(loanResultPhModel.getMsg());
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fawry_click, R.id.tv_payment_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fawry_click:
                if (!TextUtils.isEmpty(applyId)) {
                    new RepaymentAmountDialog(getActivity()).setDialogClickListener(new RepaymentAmountDialog.DialogClickListener() {
                        @Override
                        public void onClickCancel() {

                        }

                        @Override
                        public void onClickRepayment_Code(String amount) {
                            //获取还款条码
                            getRepayBarCode(applyId, amount, "referenceNumber");
                        }

                        @Override
                        public void onClickElectronic_Wallet(String amount) {
                            getRepayBarCode(applyId, amount, "eWallet");
                        }

                        @Override
                        public void onClickBank_Card(String amount) {
                            Log.e("zhangs", "onClickBank_Card: " + 1);
                            Intent intent = new Intent(getActivity(), BankCardRepaymentActivity.class);
                            intent.putExtra("amount", amount);
                            startActivity(intent);
                        }

                    }).show();
                }
                break;
            case R.id.tv_payment_status:
                PaymentStatusActivity.startAct(getActivity());
                break;
        }
    }

    private void getRepayBarCode(String applyId, String amount, String method) {
        String token = TokenUtils.getToken(getActivity());
        NetworkPhRequest.putFawry(token, applyId, "fawry", method, amount, new PhSubscriber<FawryBean>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(FawryBean fawryBean) {
                super.onNext(fawryBean);
                closeLoadingDialog();
                String code = fawryBean.getCode();
                if (code.equals(CODE_SUCCESS)) {
                    FawryBean.Fawry data = fawryBean.getData();
                    String requestNo = data.getRequestNo();
                    String msg = data.getMsg();
                    String walletQr = data.getWalletQr();
                    showPop(method, requestNo, msg, walletQr);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

    private void showPop(String method, String requestNo, String msg, String walletQr) {
        ReimbursementCodesDialog codesDialog = new ReimbursementCodesDialog(Objects.requireNonNull(getActivity()));
        codesDialog.show();
        codesDialog.setTextData(method, requestNo, msg, walletQr);
    }

}
