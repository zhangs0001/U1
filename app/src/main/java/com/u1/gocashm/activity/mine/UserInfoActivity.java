package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.activity.loan.IdCardPhActivity;
import com.u1.gocashm.activity.loan.JobInfoPhActivity;
import com.u1.gocashm.activity.loan.LoanAddressPhActivity;
import com.u1.gocashm.activity.loan.LoanIdentityPhActivity;
import com.u1.gocashm.activity.loan.PayInfoPhActivity;
import com.u1.gocashm.model.response.UserInfoDetailModel;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.constant.PhConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by jishudiannao on 2018/9/10.
 */

public class UserInfoActivity extends BasePhTitleBarActivity {

    private static final String TAG = UserInfoActivity.class.getSimpleName();
    @BindView(R.id.ll_userinfo_identity)
    LinearLayout llPhIdentity;
    @BindView(R.id.ll_userinfo_address)
    LinearLayout llPhAddress;
    @BindView(R.id.ll_userinfo_job)
    LinearLayout llPhJob;
    @BindView(R.id.ll_userinfo_idcard)
    LinearLayout llPhIdCard;
    @BindView(R.id.ll_userinfo_pay)
    LinearLayout llPhPay;

    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        activity = this;
        initData();
    }

    private void initData() {
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
                    SharedPreferencesPhUtil.getInstance(activity).setUserInfo(userInfoDetailModel.getData());
                } else  {
                    showToast(userInfoDetailModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(TAG,e.getMessage());
                closeLoadingDialog();
            }
        });
    }

    @OnClick({R.id.ll_userinfo_identity, R.id.ll_userinfo_address, R.id.ll_userinfo_job, R.id.ll_userinfo_idcard, R.id.ll_userinfo_pay})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_userinfo_identity:
                intent.setClass(this, LoanIdentityPhActivity.class);
                break;
            case R.id.ll_userinfo_address:
                intent.setClass(this, LoanAddressPhActivity.class);
                break;
            case R.id.ll_userinfo_job:
                intent.setClass(this, JobInfoPhActivity.class);
                break;
            case R.id.ll_userinfo_idcard:
                intent.setClass(this, IdCardPhActivity.class);
                break;
            case R.id.ll_userinfo_pay:
                intent.setClass(this, PayInfoPhActivity.class);
                break;
        }
        intent.putExtra(PhConstants.SOURCE, PhConstants.SOURCE_FIND);
        startActivity(intent);
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.info_title);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }
}
