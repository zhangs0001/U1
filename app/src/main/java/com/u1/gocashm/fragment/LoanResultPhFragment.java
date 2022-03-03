package com.u1.gocashm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.mine.ReUploadActivity;
import com.u1.gocashm.activity.mine.RepayPhActivity;
import com.u1.gocashm.activity.mine.coupon.FreeInterestActivity;
import com.u1.gocashm.fragment.base.BasePhFragment;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.CouponModel;
import com.u1.gocashm.model.response.InitUserModel;
import com.u1.gocashm.model.response.LoanResultPhModel;
import com.u1.gocashm.model.response.OrderDetailModel;
import com.u1.gocashm.model.response.OrderListModel;
import com.u1.gocashm.model.response.RenewaInfoPhModel;
import com.u1.gocashm.model.response.RenewalPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.DateTimePhUtil;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LotterEnterView;
import com.u1.gocashm.view.dialog.CreditRatingDialog;
import com.u1.gocashm.view.dialog.ExtendSuccessfulDialog;
import com.u1.gocashm.view.dialog.RegisterCouponDialog;
import com.u1.gocashm.view.dialog.UpdateUserLevelDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


/**
 * @author hewei
 * @date 2018/9/5 0005 上午 11:03
 */
public class LoanResultPhFragment extends BasePhFragment {

    private static final String TAG = LoanResultPhFragment.class.getSimpleName();
    private static final int RE_UPLOAD = 1001;
    @BindView(R.id.iv_status)
    ImageView statusPhImage;
    @BindView(R.id.tv_status)
    TextView tvStatusPh;
    @BindView(R.id.tv_status_hint)
    TextView tvPhStatusHint;
    @BindView(R.id.loan_apply_date)
    TextView loanPhApplyDate;
    @BindView(R.id.loan_apply_amount)
    TextView loanPhApplyAmount;
    @BindView(R.id.loan_actual_amount)
    TextView loanPhActualAmount;
    @BindView(R.id.loan_apply_term)
    TextView loanPhApplyTerm;
    @BindView(R.id.loan_repay_amount)
    TextView loanPhRepayAmount;
    @BindView(R.id.loan_repay_date)
    TextView loanPhRepayDate;
    @BindView(R.id.loan_apply_status)
    TextView loanPhApplyStatus;
    @BindView(R.id.loan_info_layout)
    LinearLayout loanPhInfoLayout;
    @BindView(R.id.tv_attention_hint1)
    TextView tvPhAttentionHint1;
    @BindView(R.id.tv_attention_hint2)
    TextView tvPhAttentionHint2;
    @BindView(R.id.attention_layout)
    LinearLayout attentionPhLayout;
    @BindView(R.id.tv_reapply)
    TextView tvPhReapply;
    @BindView(R.id.repay_btn_layout)
    LinearLayout repayBtnLayout;
    @BindView(R.id.tv_re_upload)
    TextView tvReUpload;
    @BindView(R.id.tv_loan_activity_hint)
    TextView tvLoanActivityHint;
    @BindView(R.id.tv_apply_date)
    TextView tvApplyDate;
    @BindView(R.id.tv_loan_amount)
    TextView tvLoanAmount;
    @BindView(R.id.tv_first_period_date)
    TextView tvFirstPeriodDate;
    @BindView(R.id.tv_first_period_amount)
    TextView tvFirstPeriodAmount;
    @BindView(R.id.tv_second_period_date)
    TextView tvSecondPeriodDate;
    @BindView(R.id.tv_second_period_amount)
    TextView tvSecondPeriodAmount;
    @BindView(R.id.ll_staging)
    LinearLayout llStaging;
    @BindView(R.id.loan_withdrawal_number_layout)
    LinearLayout loan_withdrawal_number_layout;
    @BindView(R.id.loan_withdrawal_number_lin)
    View loan_withdrawal_number_lin;
    @BindView(R.id.tv_repay_hint)
    TextView tvRepayHint;
    @BindView(R.id.loan_withdrawal_number)
    TextView loan_withdrawal_number;

    @BindView(R.id.lotterEnterView)
    LotterEnterView lotterEnterView;


    @BindView(R.id.extendLayoutRoot)
    LinearLayout extendLayoutRoot;
    @BindView(R.id.extendStartLayout)
    LinearLayout extendStartLayout;
    @BindView(R.id.extendLayout)
    LinearLayout extendLayout;

    @BindView(R.id.extenHint1View)
    TextView extenHint1View;
    @BindView(R.id.extenHint2View)
    TextView extenHint2View;
    @BindView(R.id.extenSubmitBtn)
    TextView extenSubmitBtn;

    @BindView(R.id.id_extend_amount)
    TextView id_extend_amount;
    @BindView(R.id.id_extend_period)
    TextView id_extend_period;
    @BindView(R.id.id_extend_interest)
    TextView id_extend_interest;
    @BindView(R.id.id_extend_repayment_amount)
    TextView id_extend_repayment_amount;
    @BindView(R.id.id_extend_start_time)
    TextView id_extend_start_time;
    @BindView(R.id.id_extend_end_time)
    TextView id_extend_end_time;
    @BindView(R.id.id_extend_mini_repayment)
    TextView id_extend_mini_repayment;

    @BindView(R.id.select_extend_term)
    InputView selectExtendTerm;

    @BindView(R.id.loan_first_due_date)
    TextView loan_first_due_date;
    @BindView(R.id.loan_first_due_amount)
    TextView loan_first_due_amount;
    @BindView(R.id.loan_second_due_date)
    TextView loan_second_due_date;
    @BindView(R.id.loan_second_due_amount)
    TextView loan_second_due_amount;
    @BindView(R.id.loan_repayment_plans_layout)
    LinearLayout loan_repayment_plans_layout;
    @BindView(R.id.loan_info_old_layout)
    LinearLayout loan_info_old_layout;

    private View view;
    ViewGroup parent;
    Unbinder unbinder;
    private String loanStatus;
    private int loanCount;
    private String token;
    private String orderId;
    private String daysLeft;
    private RenewaInfoPhModel.RenewaInfo renewaInfo;
    private OrderDetailModel.OrderDetail orderDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_loan_result, null);
            unbinder = ButterKnife.bind(this, view);
            initView();
            initData();
        } else {
            parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void initView() {
        refreshView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refreshView();
        }
    }

    private CouponModel.Coupon mSelectCoupon;

    public void setCoupon(CouponModel.Coupon coupon) {
        this.mSelectCoupon = coupon;
    }

    private void refreshView() {
        token = TokenUtils.getToken(getActivity());
        if (TokenUtils.TokenCheck(getActivity())) {
            NetworkPhRequest.getOrderList(token, "all", new PhSubscriber<OrderListModel>() {
                @Override
                public void onNext(OrderListModel orderListModel) {
                    super.onNext(orderListModel);
                    closeLoadingDialog();
                    String code = orderListModel.getCode();
                    if (CODE_SUCCESS.equals(code)) {
                        OrderListModel.OrderList body = orderListModel.getData();
                        if (body != null) {
                            List<OrderListModel.OrderList.Order> data = body.getData();
                            if (data != null && data.size() != 0) {
                                OrderListModel.OrderList.Order order = data.get(0);
                                orderId = String.valueOf(order.getId());
                                TokenUtils.setOrderId(getActivity(), order.getId());
                                updateDetail(orderId);
                                checkingCouponOrder();
                            } else {
                                RxBus.getDefault().post(new TagEvent(EventTagPh.RE_LOAN));
                            }
                        } else {
                            RxBus.getDefault().post(new TagEvent(EventTagPh.RE_LOAN));
                        }
                    } else {
                        ToastUtils.showShort(orderListModel.getMsg());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    closeLoadingDialog();
                    ToastUtils.showShort(R.string.error_request_fail);
                }

                @Override
                public void onStart() {
                    super.onStart();
                    showLoadingDialog();
                }
            });
        }
    }

    /**
     * 有券下自动验券
     */
    private void checkingCouponOrder() {
        if (TextUtils.isEmpty(orderId) || mSelectCoupon == null) return;
        NetworkPhRequest.checkingCouponOrder(TokenUtils.getToken(getActivity()), String.valueOf(mSelectCoupon.getId()), orderId, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel baseModel) {
                super.onNext(baseModel);
            }
        });
        //clear.
        mSelectCoupon = null;
    }

    private void updateDetail(String orderId) {
        NetworkPhRequest.getOrderDetail(token, orderId, new PhSubscriber<OrderDetailModel>() {
            @Override
            public void onNext(OrderDetailModel orderDetailModel) {
                super.onNext(orderDetailModel);
                closeLoadingDialog();
                String code = orderDetailModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    orderDetail = orderDetailModel.getData();
                    daysLeft = orderDetail.getRejected_days_left();
                    updateView(orderDetail);
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
                Log.e(TAG, e.getMessage());
            }
        });

    }

    private void recordLoanSuccessEvent(OrderDetailModel.OrderDetail detail) {
        if (detail == null) return;
        if (!detail.getUser().isReload() && ("system_paid".equals(detail.getStatus()) || "manual_paid".equals(detail.getStatus()))) {
            SPUtils spUtils = SPUtils.getInstance("loan_success_events");
            String id = "";
            try {
                id = spUtils.getString("id");
            } catch (Exception e) {
                //莫名其妙的问题 (￣┰￣*)
                id = String.valueOf(spUtils.getInt("id"));
            }
            if (TextUtils.isEmpty(id) || (!TextUtils.isEmpty(id) && !id.equals(detail.getId()))) {
                AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_LOAN_SUCCESS, getActivity(), TokenUtils.getOrderId(getActivity()));
                spUtils.put("id", String.valueOf(detail.getId()));
            }
        }

    }

    private void updateView(OrderDetailModel.OrderDetail detail) {
        TokenUtils.setRepayCode(getActivity(), detail.getReference_no());

        recordLoanSuccessEvent(detail);
        updateStatus(detail);
        List<OrderDetailModel.OrderDetail.RepaymentPlan> repaymentPlans = detail.getRepayment_plans();

        String withdrawalNumber = !TextUtils.isEmpty(detail.getWithdraw_no()) ? detail.getWithdraw_no() : "--";
        if (TextUtils.equals("PAYING", detail.getOApi_status())) {
            loan_withdrawal_number_layout.setVisibility(View.VISIBLE);
            loan_withdrawal_number_lin.setVisibility(View.VISIBLE);
            loan_withdrawal_number.setText(withdrawalNumber);
        } else {
            loan_withdrawal_number_layout.setVisibility(View.GONE);
            loan_withdrawal_number_lin.setVisibility(View.GONE);
        }
        loanPhApplyStatus.setText(detail.getApi_status());
        loanPhApplyDate.setText(detail.getCreated_at());

        String applyAmount = detail.getPrincipal();
        if (!TextUtils.isEmpty(applyAmount)) {
            loanPhApplyAmount.setText(String.format(getString(R.string.loan_how_much), applyAmount));
        } else {
            loanPhApplyAmount.setText("---");
        }

        String countractAmount = detail.getAmount_due();
        if (!TextUtils.isEmpty(countractAmount)) {
            loanPhActualAmount.setText(String.format(getString(R.string.loan_how_much), countractAmount));
        }

        loanPhApplyTerm.setText(String.format(getString(R.string.loan_how_long), detail.getLoan_days()));
        String repayAmount = detail.getReceivable_amount();

        /*if (TextUtils.isEmpty(detail.getAppointment_paid_time())) {
            loanPhRepayDate.setText("---");
        } else {
            loanPhRepayDate.setText(detail.getRepay_time());
        }*/
        loanPhRepayDate.setText(detail.getAppointment_paid_time());

        /*if (!TextUtils.isEmpty(repayAmount)) {
            loanPhRepayAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(repayAmount)));
        } else {
            loanPhRepayAmount.setText("---");
        }
        */
        loanPhRepayAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(repayAmount)));

        if (repaymentPlans != null && repaymentPlans.size() > 1) {
            tvApplyDate.setText(detail.getCreated_at());
            tvLoanAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(applyAmount)));
            String repayAmount1 = repaymentPlans.get(0).getRepay_amount();
            if (!TextUtils.isEmpty(repayAmount1)) {
                tvFirstPeriodAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(repayAmount1)));
            } else {
                tvFirstPeriodAmount.setText("---");
            }
            if (TextUtils.isEmpty(repaymentPlans.get(0).getRepay_time())) {
                tvFirstPeriodDate.setText("---");
            } else {
                tvFirstPeriodDate.setText(repaymentPlans.get(0).getRepay_time());
            }
            String repayAmount2 = repaymentPlans.get(1).getRepay_amount();
            if (!TextUtils.isEmpty(repayAmount2)) {
                tvSecondPeriodAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(repayAmount2)));
            } else {
                tvSecondPeriodAmount.setText("---");
            }
            if (TextUtils.isEmpty(repaymentPlans.get(1).getRepay_time())) {
                tvSecondPeriodDate.setText("---");
            } else {
                tvSecondPeriodDate.setText(repaymentPlans.get(1).getRepay_time());
            }
            if ("LOAN".equals(getStatus(detail.getStatus())) || "REPAY".equals(getStatus(detail.getStatus()))) {
                tvRepayHint.setVisibility(View.VISIBLE);
            } else {
                tvRepayHint.setVisibility(View.GONE);
            }
        } else {
            tvRepayHint.setVisibility(View.GONE);
            llStaging.setVisibility(View.GONE);
        }


        if (TextUtils.equals("PENDING", detail.getOApi_status())
                || TextUtils.equals("PAYING", detail.getOApi_status())) {
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
                        if (data.getIt_service_signed() == 0 && data.getIt_service_need() == 1) {
                            CreditRatingDialog creditRatingDialog = new CreditRatingDialog();
                            creditRatingDialog.show(getChildFragmentManager(), "1");
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    closeLoadingDialog();
                }
            });

        }

        //若订单状态=PAID，且有优惠券，则弹窗提示用户，点击ok跳转到my account-coupon列表页
        if (TextUtils.equals("PAID", detail.getOApi_status())
                || TextUtils.equals("OVERDUE", detail.getOApi_status())) {
            ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
            reqPhModel.setToken(TokenUtils.getToken(getContext()));
            NetworkPhRequest.getCouponList(reqPhModel, new PhSubscriber<CouponModel>() {
                @Override
                public void onStart() {
                    super.onStart();
                    showLoadingDialog();
                }

                @Override
                public void onNext(CouponModel couponModel) {
                    super.onNext(couponModel);
                    closeLoadingDialog();
                    String code = couponModel.getCode();
                    boolean enable = false;
                    if (CODE_SUCCESS.equals(code)) {
                        List<CouponModel.Coupon> body = couponModel.getData();
                        if (body != null && body.size() != 0) {
                            for (CouponModel.Coupon datum : body) {
                                if (1 == datum.getStatus() && datum.getUsed() == 0) {
                                    enable = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (enable) {
                        RegisterCouponDialog.couponType = 2;
                        RegisterCouponDialog couponDialog = new RegisterCouponDialog();
                        couponDialog.setCancelable(false);
                        couponDialog.setOnLisstener(new RegisterCouponDialog.OnLisstener() {
                            @Override
                            public void onOkClick(RegisterCouponDialog dialog) {
                                startActivity(new Intent(getContext(), FreeInterestActivity.class));
                            }
                        });
                        couponDialog.show(getChildFragmentManager(), "2");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    closeLoadingDialog();
                }
            });


        }

    }


    @SuppressLint("StringFormatInvalid")
    private void updateStatus(OrderDetailModel.OrderDetail detail) {
        String status = detail.getStatus();
        loanStatus = getStatus(status);
        tvReUpload.setVisibility(View.GONE);
        tvLoanActivityHint.setVisibility(View.GONE);
        tvRepayHint.setVisibility(View.GONE);
        extendLayoutRoot.setVisibility(View.GONE);
        loan_repayment_plans_layout.setVisibility(View.GONE);
        switch (loanStatus) {
            case "APPROVE":
                setRepaymentPlansView(detail);
                lotterEnterView.setVisibility(View.GONE);
                statusPhImage.setImageResource(R.drawable.bg_review);
                tvStatusPh.setText(R.string.loan_status_review_title);
//                loanPhApplyStatus.setText(R.string.loan_status_review);
                tvPhAttentionHint1.setVisibility(View.VISIBLE);
                tvPhAttentionHint1.setText(R.string.loan_apply_hint);
                tvPhStatusHint.setVisibility(View.GONE);
                tvPhAttentionHint2.setVisibility(View.GONE);
                tvPhStatusHint.setVisibility(View.GONE);
                repayBtnLayout.setVisibility(View.GONE);
                loanPhInfoLayout.setVisibility(View.VISIBLE);
                tvPhReapply.setVisibility(View.GONE);
                break;
            case "LOAN":
                setRepaymentPlansView(detail);
                statusPhImage.setImageResource(R.drawable.icon_loan);
                tvStatusPh.setText(R.string.loan_status_apply_success_title);
//                loanPhApplyStatus.setText(R.string.loan_status_review);
                tvPhStatusHint.setVisibility(View.GONE);
                tvPhAttentionHint1.setVisibility(View.VISIBLE);
                tvPhAttentionHint2.setVisibility(View.VISIBLE);
                tvPhAttentionHint1.setText(R.string.loan_success_hint);
                tvPhAttentionHint2.setText(R.string.loan_success_hint2);
                tvPhStatusHint.setVisibility(View.GONE);
                repayBtnLayout.setVisibility(View.GONE);
                tvPhReapply.setVisibility(View.GONE);
                loanPhInfoLayout.setVisibility(View.VISIBLE);
                tvRepayHint.setVisibility(View.VISIBLE);
                break;
            case "REPAY":
            case "OVERDUE":
                setRepaymentPlansView(detail);
                tvLoanActivityHint.setVisibility(View.VISIBLE);
                statusPhImage.setImageResource(R.drawable.icon_repay);
                tvStatusPh.setText(R.string.loan_status_in_payment);
                tvPhStatusHint.setText(R.string.loan_repay_hint);
                tvPhStatusHint.setVisibility(View.VISIBLE);
//                loanPhApplyStatus.setText(R.string.loan_status_in_payment);
                repayBtnLayout.setVisibility(View.VISIBLE);
                tvPhAttentionHint2.setVisibility(View.GONE);
                tvPhReapply.setVisibility(View.GONE);
                loanPhInfoLayout.setVisibility(View.VISIBLE);
                tvRepayHint.setVisibility(View.VISIBLE);
                dealExtendLogic(detail);
                break;
            case "REJECTED":
                statusPhImage.setImageResource(R.drawable.icon_apply_failed);
                tvStatusPh.setText(R.string.loan_status_apply_failed);
                if (!TextUtils.isEmpty(daysLeft)) {
                    tvPhStatusHint.setText(getString(R.string.loan_failed_hint, daysLeft));
                }
                tvPhStatusHint.setVisibility(View.VISIBLE);
                tvPhReapply.setVisibility(View.VISIBLE);
                tvPhReapply.setText(R.string.reapply);
                loanPhInfoLayout.setVisibility(View.GONE);
                tvPhAttentionHint1.setVisibility(View.GONE);
                tvPhAttentionHint2.setVisibility(View.GONE);
                repayBtnLayout.setVisibility(View.GONE);
                llStaging.setVisibility(View.GONE);
                break;
            case "SETTLE":
                setRepaymentPlansView(detail);
                lotterEnterView.setVisibility(View.GONE);
                tvLoanActivityHint.setVisibility(View.VISIBLE);
                statusPhImage.setImageResource(R.drawable.icon_settle_);
                tvStatusPh.setText(R.string.loan_status_settle);
//                loanPhApplyStatus.setText(R.string.loan_status_settle);
                tvPhReapply.setVisibility(View.VISIBLE);
                tvPhReapply.setText(R.string.reapply);
                tvPhStatusHint.setVisibility(View.GONE);
                tvPhAttentionHint1.setVisibility(View.GONE);
                tvPhAttentionHint2.setVisibility(View.GONE);
                repayBtnLayout.setVisibility(View.GONE);
                loanPhInfoLayout.setVisibility(View.VISIBLE);

                if (UpdateUserLevelDialog.isShowUpgradeLevelDialog) {
                    UpdateUserLevelDialog.isShowUpgradeLevelDialog = false;
                    NetworkPhRequest.getUserLevel(TokenUtils.getToken(getActivity()), new PhSubscriber<InitUserModel>() {
                        @Override
                        public void onNext(InitUserModel userLevelModel) {
                            super.onNext(userLevelModel);
                            if (CODE_SUCCESS.equals(userLevelModel.getCode())) {
                                UpdateUserLevelDialog levelDialog = new UpdateUserLevelDialog();
                                levelDialog.setData(UpdateUserLevelDialog.convertLevel(userLevelModel.getData().getLevel()));
                                levelDialog.setConfirmListener(() -> {
                                });
                                levelDialog.show(getFragmentManager(), "1");
                            }
                        }
                    });
                }
                break;
            case "SUPPLEMENTARY":
                statusPhImage.setImageResource(R.drawable.icon_need_modify);
                tvStatusPh.setText(R.string.loan_status_apply_failed);
                tvPhStatusHint.setText(R.string.loan_failed_hint2);
                tvPhStatusHint.setVisibility(View.VISIBLE);
//                loanPhApplyStatus.setText(R.string.loan_status_review);
                tvPhReapply.setVisibility(View.VISIBLE);
                tvPhReapply.setText(R.string.modify);
                tvPhAttentionHint1.setVisibility(View.GONE);
                tvPhAttentionHint2.setVisibility(View.GONE);
                repayBtnLayout.setVisibility(View.GONE);
                loanPhInfoLayout.setVisibility(View.VISIBLE);
                break;
            case "APPLY":
                reapply();
                break;
            case "CANCEL":
                tvLoanActivityHint.setVisibility(View.VISIBLE);
                statusPhImage.setImageResource(R.drawable.icon_settle_);
                tvStatusPh.setText(R.string.loan_status_cancel);
                tvPhReapply.setVisibility(View.VISIBLE);
                tvPhReapply.setText(R.string.reapply);
                tvPhStatusHint.setVisibility(View.GONE);
                tvPhAttentionHint1.setVisibility(View.GONE);
                tvPhAttentionHint2.setVisibility(View.GONE);
                repayBtnLayout.setVisibility(View.GONE);
                loanPhInfoLayout.setVisibility(View.VISIBLE);
                break;
            case "REPLENISH":
                tvReUpload.setVisibility(View.VISIBLE);
                statusPhImage.setImageResource(R.drawable.need_reupload_image);
                tvStatusPh.setText(R.string.loan_status_re_upload);
                tvPhStatusHint.setVisibility(View.GONE);
                tvPhAttentionHint1.setVisibility(View.VISIBLE);
                tvPhAttentionHint1.setText(R.string.loan_hint_re_upload);
                tvPhAttentionHint1.setTextColor(0xFFEC4242);
                tvPhAttentionHint2.setVisibility(View.GONE);
                tvPhReapply.setVisibility(View.GONE);
                repayBtnLayout.setVisibility(View.GONE);
                loanPhInfoLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void setRepaymentPlansView(OrderDetailModel.OrderDetail detail) {
        loan_repayment_plans_layout.setVisibility(View.VISIBLE);
        loan_info_old_layout.setVisibility(View.GONE);
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
                    List<LoanResultPhModel.RepaymentPlan> repaymentPlans = data.getRepaymentPlans();
                    if (repaymentPlans != null) {
                        try {
                            for (LoanResultPhModel.RepaymentPlan repayment_plan : repaymentPlans) {
                                if (repayment_plan.getInstallment_num() == 1) {
                                    loan_first_due_date.setText(DateTimePhUtil.changeYMDtoEn3(repayment_plan.getRepayDate()));
                                    loan_first_due_amount.setText(getString(R.string.loan_how_much) + repayment_plan.getRepayAmount());
                                    Log.e(TAG, "onNext: " + repayment_plan.getRepayAmount());
                                    Log.e(TAG, "onNext: " + repayment_plan.getRepayDate());
                                } else if (repayment_plan.getInstallment_num() == 2) {
                                    loan_second_due_date.setText(DateTimePhUtil.changeYMDtoEn3(repayment_plan.getRepayDate()));
                                    loan_second_due_amount.setText(getString(R.string.loan_how_much) + repayment_plan.getRepayAmount());
                                    Log.e(TAG, "onNext: " + repayment_plan.getRepayAmount());
                                    Log.e(TAG, "onNext: " + repayment_plan.getRepayDate());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }


    private void dealExtendLogic(OrderDetailModel.OrderDetail detail) {
        NetworkPhRequest.getRenewalInfo(TokenUtils.getToken(getContext()), String.valueOf(detail.getId()), new PhSubscriber<RenewaInfoPhModel>() {
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

            @Override
            public void onNext(RenewaInfoPhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                if (CODE_SUCCESS.equals(basePhModel.getCode())) {
                    renewaInfo = basePhModel.getData();
                    changeExntendViewVisible(renewaInfo.getRenewalPreInfo().getStatus());
                    initExtendTerm(renewaInfo);
                } else if ("13000".equals(basePhModel.getCode())) {
                    changeExntendViewVisible(-1);
                } else {
                    Toast.makeText(getContext(), basePhModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            case "replenish":
                loanStatus = "REPLENISH";
                break;
            case "paying":
            case "system_pay_fail":
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_reapply, R.id.btn_repay, R.id.btn_facebook, R.id.tv_re_upload, R.id.startExtenNowBtn, R.id.extenSubmitBtn})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_reapply:
                reapply();
                break;
            case R.id.btn_repay:
                repay();
                break;
            case R.id.btn_facebook:
                toFacebook();
                break;
            case R.id.tv_re_upload:
                reUpload();
                break;
            case R.id.startExtenNowBtn:
                startExtendNow();
                changeExntendViewVisible(1);
                extenHint1View.setVisibility(View.VISIBLE);
                extenHint2View.setVisibility(View.GONE);
                extenSubmitBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.extenSubmitBtn:
                submitExtendNow();
                break;
        }
    }

    private void reUpload() {
        Intent intent = new Intent(getActivity(), ReUploadActivity.class);
        startActivityForResult(intent, RE_UPLOAD);
    }

    private void toFacebook() {
       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(PhUtils.getFacebookPageURL(getActivity())));
        startActivity(intent);*/
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(PhConstants.URL_OFFICIAL));
        startActivity(intent);
    }


    private void reapply() {
        if (!TextUtils.isEmpty(daysLeft) && !"0".equals(daysLeft)) {
            Toast.makeText(mContext, getString(R.string.loan_failed_hint, daysLeft), Toast.LENGTH_SHORT).show();
            return;
        }
        RxBus.getDefault().post(new TagEvent(EventTagPh.RE_APPLY, loanStatus));
    }

    private void repay() {
//        todo
//        Intent intent = new Intent(getActivity(), RepayFragment.class);
//        startActivity(intent);
        RxBus.getDefault().post(new TagEvent(EventTagPh.REPAYMENT));
//        ToastUtils.showLong(R.string.pleaserepaymentpage);
    }


    //提交展期
    private void submitExtendNow() {
        NetworkPhRequest.getRenewal(TokenUtils.getToken(getContext()), String.valueOf(renewaInfo.getId()), new PhSubscriber<RenewalPhModel>() {
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

            @Override
            public void onNext(RenewalPhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                if (CODE_SUCCESS.equals(basePhModel.getCode())) {
                    ExtendSuccessfulDialog dialog = new ExtendSuccessfulDialog(renewaInfo);
                    dialog.show(getChildFragmentManager(), "1");
                } else {
                    Toast.makeText(getContext(), basePhModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 展期
     */
    private void startExtendNow() {
        if (renewaInfo == null) return;
        id_extend_amount.setText(String.format("%1$s EGP", renewaInfo.getPrincipal()));
        id_extend_period.setText(renewaInfo.getRenewalPreInfo().getRenewal_days().get(0) + " days");
        id_extend_interest.setText(String.format("%1$s EGP", renewaInfo.getRenewalPreInfo().getInterest_fee()));
        id_extend_repayment_amount.setText(String.format("%1$s EGP", renewaInfo.getRenewalPreInfo().getExtend_appointment_paid_amount()));
        id_extend_start_time.setText(DateTimePhUtil.changeYMDtoEn(renewaInfo.getRenewalPreInfo().getValid_period()));
        id_extend_end_time.setText(DateTimePhUtil.changeYMDtoEn(renewaInfo.getRenewalPreInfo().getAppointment_paid_time()));
        id_extend_mini_repayment.setText(String.format("EGP %1$s", renewaInfo.getRenewalPreInfo().getMin_repay_amount()));
        String html = "Extension submission is successful, pls confirm minimun repayment amount that need to make one-off payment on <b><font color='#F50B0B'>" + DateTimePhUtil.changeYMDtoEn2(renewaInfo.getRenewalPreInfo().getAppointment_paid_time()) + "</font></b> to complete extension process, otherwise it will fail.";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            extenHint2View.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            extenHint2View.setText(Html.fromHtml(html));
        }
    }


    private void initExtendTerm(RenewaInfoPhModel.RenewaInfo renewal) {
        if (renewal.getRenewalPreInfo().getStatus() == 0) {
            String val = renewal.getRenewalPreInfo().getRenewal_days().isEmpty() ? "" : (String.format("%d Days", renewal.getRenewalPreInfo().getRenewal_days().get(0)));
            selectExtendTerm.setText(val);
        } else if (renewal.getRenewalPreInfo().getStatus() == 1 || renewal.getRenewalPreInfo().getStatus() == 2) {
            startExtendNow();
            if (renewal.getRenewalPreInfo().getStatus() == 1) {
                //切换已展期
                changeExntendViewVisible(1);
            }
        }
    }

    /**
     * @param status -1 不展示展期
     *               0   未展期
     *               1   已展期
     *               2   展期成功
     */
    private void changeExntendViewVisible(int status) {
        if (status == -1 || status == 2) {
            //不展示展期 展期成功
            extendLayoutRoot.setVisibility(View.GONE);
            loanPhInfoLayout.setVisibility(View.VISIBLE);
        } else if (status == 0) {
            //未展期
            extendLayoutRoot.setVisibility(View.VISIBLE);
            extendStartLayout.setVisibility(View.VISIBLE);
            extendLayout.setVisibility(View.GONE);
            loanPhInfoLayout.setVisibility(View.VISIBLE);

            selectExtendTerm.getLl().setBackgroundResource(R.drawable.bg_round10_ffffff);
            selectExtendTerm.getTv_content().setTextColor(Color.parseColor("#2C2727"));
            selectExtendTerm.getTv_content().setTextSize(15);
            selectExtendTerm.getTv_content().getPaint().setFakeBoldText(true);
        } else if (status == 1) {
            //已展期
            extendLayoutRoot.setVisibility(View.VISIBLE);
            extendStartLayout.setVisibility(View.GONE);
            extendLayout.setVisibility(View.VISIBLE);
            extenHint1View.setVisibility(View.GONE);
            extenHint2View.setVisibility(View.VISIBLE);
            extenSubmitBtn.setVisibility(View.GONE);

            loanPhInfoLayout.setVisibility(View.GONE);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RE_UPLOAD && resultCode == RESULT_OK && data != null) {
            refreshView();
        }
    }
}
