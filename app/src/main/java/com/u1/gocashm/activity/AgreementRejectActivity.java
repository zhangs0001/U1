package com.u1.gocashm.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhActivity;
import com.u1.gocashm.util.StatusPhBarUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hewei
 * @date 2019/9/25 0025 上午 10:13
 */
public class AgreementRejectActivity extends BasePhActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_reject);
        ButterKnife.bind(this);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.white));
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
