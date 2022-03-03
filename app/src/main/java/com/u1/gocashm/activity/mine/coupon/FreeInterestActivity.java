package com.u1.gocashm.activity.mine.coupon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.adapter.CouponAdapter;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.CouponModel;
import com.u1.gocashm.model.response.LoanResultPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.params.ButtonParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


public class FreeInterestActivity extends BasePhTitleBarActivity {

    @BindView(R.id.interest_list)
    RecyclerView interestList;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;
    @BindView(R.id.coupon_bottom_layout)
    LinearLayout couponBottomLayout;
    @BindView(R.id.tv_no_coupon_hint)
    TextView tvNoCouponHint;
    private View[] views;
    private TextView[] textviews;

    private CouponAdapter couponAdapter;
    private FragmentActivity activity;
    private int index;
    private int currentTabIndex;
    private List<Integer> couponIdList;
    private ConfigButton buttonConfirm;
    private ConfigButton buttonCancel;

    /**
     * 未使用
     */
    private List<CouponModel.Coupon> mPendingCoupons = new ArrayList<>();
    /**
     * 已使用
     */
    private List<CouponModel.Coupon> mRedeemedCoupons = new ArrayList<>();
    /**
     * 已过期
     */
    private List<CouponModel.Coupon> mExpiredCoupons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_interest);
        ButterKnife.bind(this);
        activity = this;
        initViews();
        initData();
    }

    protected void initViews() {
        views = new View[3];
        textviews = new TextView[3];
        views[0] = findViewById(R.id.first_line);
        views[1] = findViewById(R.id.second_line);
        views[2] = findViewById(R.id.third_line);
        textviews[0] = findViewById(R.id.first);
        textviews[1] = findViewById(R.id.second);
        textviews[2] = findViewById(R.id.third);
        textviews[0].setTextColor(getResources().getColor(R.color.blue));
        views[0].setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        interestList.setLayoutManager(layoutManager);
    }

    protected void initData() {
        requestCouponDatas();
    }

    private void requestCouponDatas() {
        ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
        reqPhModel.setToken(TokenUtils.getToken(activity));
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
                if (CODE_SUCCESS.equals(code)) {
                    List<CouponModel.Coupon> body = couponModel.getData();
                    if (body != null && body.size() != 0) {
                        mExpiredCoupons.clear();
                        mPendingCoupons.clear();
                        mRedeemedCoupons.clear();
                        for (CouponModel.Coupon datum : body) {
                            if (0 == datum.getStatus() && datum.getUsed() == 0) {
                                //已过期
                                mExpiredCoupons.add(datum);
                            }
                            if (1 == datum.getStatus()  && datum.getUsed() == 0) {
                                //已过期
                                mPendingCoupons.add(datum);
                            }

                            if (datum.getUsed() == 1) {
                                //已使用
                                mRedeemedCoupons.add(datum);
                            }
                        }
                    }
                    notifyAdapter(0);
                } else {
                    showNoDataLayout(0);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showToast(R.string.error_request_fail);
                closeLoadingDialog();
            }
        });
    }

    private void notifyAdapter(int type) {
        List<CouponModel.Coupon> datasource = getAdapterDatasourceByType(type);
        if (couponAdapter == null) {
            couponAdapter = new CouponAdapter(R.layout.item_free_intetrest_layout, datasource);
            interestList.setAdapter(couponAdapter);
        }

        if (!datasource.isEmpty()) {
            interestList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            tvNoCouponHint.setVisibility(View.GONE);
            if (type != 0) {
                couponBottomLayout.setVisibility(View.GONE);
            } else {
                couponBottomLayout.setVisibility(View.VISIBLE);
            }
        } else {
            interestList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            couponBottomLayout.setVisibility(View.GONE);
            showNoDataLayout(type);
        }

        couponAdapter.setType(String.valueOf(type));
        couponAdapter.getCheckeds().clear();
        couponAdapter.setNewData(datasource);
    }

    private List<CouponModel.Coupon> getAdapterDatasourceByType(int type) {
        switch (type) {
            case 0:
                for (CouponModel.Coupon mPendingCoupon : mPendingCoupons) {
                    mPendingCoupon.setSelected(false);
                }
                return mPendingCoupons;
            case 1:
                return mRedeemedCoupons;
            default:
                return mExpiredCoupons;
        }
    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.mine_offer));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @OnClick({R.id.interest_wait_tab, R.id.interest_already_tab, R.id.interest_expired_tab, R.id.invite_friend_btn, R.id.confirm_order})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.interest_wait_tab:
                index = 0;
                break;
            case R.id.interest_already_tab:
                index = 1;
                break;
            case R.id.interest_expired_tab:
                index = 2;
                break;
            case R.id.invite_friend_btn:
                index = -1;
                startActivity(new Intent(activity, InviteFriendsActivity.class));
                break;
            case R.id.confirm_order:
                index = -1;
                confirmExchange();
                break;
        }
        onTabSelected(index);
    }

    private void confirmExchange() {
//        final int id = couponAdapter.getCouponId();
        List<CouponModel.Coupon> list = couponAdapter.getCheckeds();
        couponIdList = new ArrayList<>();
        for (CouponModel.Coupon coupon : list) {
            couponIdList.add(coupon.getId());
        }
        if (couponIdList.size() == 0) {
            showToast(R.string.please_select);
            return;
        }
        final CircleDialog.Builder builder = new CircleDialog.Builder();
        final CircleDialog.Builder builder2 = new CircleDialog.Builder();
        final CircleDialog.Builder builder3 = new CircleDialog.Builder();
        buttonConfirm = new ConfigButton() {
            @Override
            public void onConfig(ButtonParams params) {
                params.textColor = getResources().getColor(R.color.blue);
                params.textSize = 20;
            }
        };
        buttonCancel = new ConfigButton() {
            @Override
            public void onConfig(ButtonParams params) {
                params.textColor = getResources().getColor(R.color.black_1);
                params.textSize = 20;
            }
        };
        builder.setTitle(getString(R.string.permission_title_dialog))
                .setText(getString(R.string.exchange_dialog_text))
                .setPositive(getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                                    NetworkPhRequest.checkingCoupon(TokenUtils.getToken(activity),String.valueOf(couponIdList.get(0)),data.getApplyId(), new PhSubscriber<BasePhModel>() {
                                        @Override
                                        public void onStart() {
                                            super.onStart();
                                            showLoadingDialog();
                                        }

                                        @Override
                                        public void onNext(BasePhModel baseModel) {
                                            super.onNext(baseModel);
                                            closeLoadingDialog();
                                            String code = baseModel.getCode();
                                            if (CODE_SUCCESS.equals(code)) {
                                                ToastUtils.showShort(baseModel.getMsg());
                                                requestCouponDatas();
                                            }
                                            /*else if (status != null && "119".equals(status.getCode())) {
                                                //超出金额确认弹框
                                                showConfirmDialog(builder2, baseModel);
                                            }*/
                                            else {
                                                builder3.setTitle(getString(R.string.permission_title_dialog))
                                                        .setText(baseModel.getMsg())
                                                        .setNegative(getString(R.string.confirm), new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                requestCouponDatas();
                                                            }
                                                        })
                                                        .show(getSupportFragmentManager());
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            super.onError(e);
                                            closeLoadingDialog();
                                            ToastUtils.showShort(R.string.error_request_fail);
                                        }
                                    });
                                } else {
                                    showToast(loanResultPhModel.getMsg());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                showToast(e.getMessage());
                                closeLoadingDialog();
                            }
                        });
                    }
                })
                .setNegative(getString(R.string.dialog_cancel), null)
                .configPositive(buttonConfirm)
                .configNegative(buttonCancel)
                .show(getSupportFragmentManager());
    }



    private void onTabSelected(int index) {
        if (index < 0) {
            return;
        } else if (index > 2) {
            index = 2;
        }
        this.index = index;
        textviews[currentTabIndex].setTextColor(getResources().getColor(R.color.button_unpressed));
        textviews[index].setTextColor(getResources().getColor(R.color.blue));
        views[currentTabIndex].setVisibility(View.GONE);
        views[index].setVisibility(View.VISIBLE);
        currentTabIndex = index;

        if (couponAdapter != null) {
            couponAdapter.checkeds.clear();
        }
        notifyAdapter(index);
        //refreshList(index);
    }


    private void showNoDataLayout(int statusCode) {
        switch (statusCode) {
            case 0:
                emptyLayout.setVisibility(View.VISIBLE);
                tvNoCouponHint.setVisibility(View.GONE);
                break;
            case 1:
                emptyLayout.setVisibility(View.GONE);
                tvNoCouponHint.setVisibility(View.VISIBLE);
                tvNoCouponHint.setText(getString(R.string.no_redeemed_coupon));
                break;
            case 2:
                emptyLayout.setVisibility(View.GONE);
                tvNoCouponHint.setVisibility(View.VISIBLE);
                tvNoCouponHint.setText(getString(R.string.no_expired_coupon));
                break;
        }
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P16_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P16_Leave";
    }

}
