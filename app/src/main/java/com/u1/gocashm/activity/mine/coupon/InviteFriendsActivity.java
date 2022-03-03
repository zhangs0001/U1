package com.u1.gocashm.activity.mine.coupon;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.InviteCodeModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.PhConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by hewei on 2018/6/12 0012.
 */

public class InviteFriendsActivity extends BasePhTitleBarActivity {
    @BindView(R.id.invite_url)
    TextView inviteUrl;
    @BindView(R.id.tv_rules)
    TextView rulesText;
    @BindView(R.id.tv_rules2)
    TextView rulesText2;
    @BindView(R.id.register_view)
    TextView register_view;
    @BindView(R.id.apply_loan_view)
    TextView apply_loan_view;
    @BindView(R.id.disbursement_view)
    TextView disbursement_view;
    @BindView(R.id.on_time_reapment_view)
    TextView on_time_reapment_view;
    @BindView(R.id.tv_activity_date)
    TextView tv_activity_date;

    private ClipboardManager clipboard;
    private ClipData clipData;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    protected void initViews() {
//        inviteUrl.setText(getString(R.string.ucash_url));
        rulesText.setText(Html.fromHtml("1. This activity is available to all users.<br>" +
                "2. Coupons are on a first come, first served basis, and they can be accumulated.<br>" +
                "3. Different coupon amounts you will receive for different step your friend complete."));
        rulesText2.setText(Html.fromHtml("4. Coupons will be released to your account directly.<br>" +
                "5. You can check your remaining coupons in [My Account]-[Coupon].<br>" +
                "6. GoCash reserves the right of final decision and interpretation of this activity."));
    }

    protected void initData() {
        clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        NetworkPhRequest.getRecommend(TokenUtils.getToken(this), new PhSubscriber<InviteCodeModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(InviteCodeModel baseModel) {
                super.onNext(baseModel);
                closeLoadingDialog();
                String code = baseModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    url = baseModel.getData().getInvite_code();
                    inviteUrl.setText(url);
                    clipData = ClipData.newPlainText(null, url);

                    tv_activity_date.setText(baseModel.getData().getActivity_time());
                    register_view.setText(baseModel.getData().getInvite_award().getRegister() + " EGP");
                    apply_loan_view.setText(baseModel.getData().getInvite_award().getApply_a_loan() + " EGP");
                    disbursement_view.setText(baseModel.getData().getInvite_award().getDisbursement() + " EGP");
                    on_time_reapment_view.setText(baseModel.getData().getInvite_award().getOn_time_repayment() + " EGP");
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

    private void copy() {
        clipboard.setPrimaryClip(clipData);
        showToast(getString(R.string.copy_success));
        BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P15_Copy");
    }

    private void invite() {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(this, QRCodeActivity.class);
            intent.putExtra(PhConstants.INVITE_URL, url);
            startActivity(intent);
            BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P17_QRcode");
        } else {
            Toast.makeText(this, getString(R.string.a12), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.mine_invite);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @OnClick({R.id.copy_btn, R.id.invite_qr_layout})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.copy_btn:
                copy();
                break;
            case R.id.invite_qr_layout:
                invite();
                break;
        }
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }


    @Override
    protected String getRecordEnterKey() {
        return "P17_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P17_Leave";
    }
}
