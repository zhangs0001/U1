package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.WebPhActivity;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorRecordReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.QRCodeUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.view.dialog.RepayAmountDialog;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blankj.utilcode.util.ScreenUtils.getScreenWidth;
import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/10/11 0011 上午 10:11
 */
public class RepayPhActivity extends BasePhTitleBarActivity {

    @BindView(R.id.repay_code)
    TextView repayCodePh;
    @BindView(R.id.iv_bar_code)
    ImageView ivBarCode;
    @BindView(R.id.ll_bar_code)
    LinearLayout llBarCode;

    private Activity activity;
    private String repayCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay);
        ButterKnife.bind(this);
        activity = this;
        initView();
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.repay);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    private void initView() {
        repayCode = TokenUtils.getRepayCode(activity);
//        repayCodePh.setText(repayCode);
    }

    @OnClick({R.id.tv_get_barcode, R.id.bt_other_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_barcode:
                getBarCode();
                break;
            case R.id.bt_other_help:
                toHelp();
                break;
        }
    }

    private void getBarCode() {
        if (TextUtils.isEmpty(repayCode) || "---".equals(repayCode)) {
            ToastUtils.showShort(getString(R.string.a18));
            return;
        }
        RepayAmountDialog dialog = new RepayAmountDialog();
        dialog.setConfirmListener(new RepayAmountDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick(Double repayAmount) {
                ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
                reqPhModel.setToken(TokenUtils.getToken(activity));
                reqPhModel.setAmount(repayAmount.toString());
                reqPhModel.setReference_no(repayCode);
                NetworkPhRequest.getBarCode(reqPhModel, new PhSubscriber<BasePhModel>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoadingDialog();
                    }

                    @Override
                    public void onNext(BasePhModel basePhModel) {
                        super.onNext(basePhModel);
                        closeLoadingDialog();
                        if (basePhModel.getCode().equals(CODE_SUCCESS)) {
                            llBarCode.setVisibility(View.VISIBLE);
                            String text = (String) basePhModel.getData();
                            if (!TextUtils.isEmpty(text)) {
                                Bitmap barcode = QRCodeUtil.createBarcode(text, getScreenWidth() * 2 / 3, 100);
                                Glide.with(activity).asBitmap().load(barcode).into(ivBarCode);
                            }
                        } else {
                            ToastUtils.showShort(basePhModel.getMsg());
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

    private void toHelp() {
        Intent intent = new Intent(activity, WebPhActivity.class);
        intent.putExtra(PhConstants.WEB_URL, PhConstants.HELP_URL);
        intent.putExtra(PhConstants.WEB_TITLE, getString(R.string.repay_other_help));
        startActivity(intent);
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P14_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P14_Leave";
    }
    @Override
    protected String getRecordBackKey() {
        return "P14_C_Back";
    }

    public BehaviorRecordReqModel getBehaviorRecord() {
        return mBehaviorRecord;
    }
}
