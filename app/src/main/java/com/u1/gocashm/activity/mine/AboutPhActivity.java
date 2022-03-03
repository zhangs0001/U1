package com.u1.gocashm.activity.mine;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.util.StatusPhBarUtil;

/**
 * @author hewei
 * @date 2018/8/30 0030 上午 10:54
 */
public class AboutPhActivity extends BasePhTitleBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.about_us);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }


    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P15_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P15_Leave";
    }

    @Override
    protected String getRecordBackKey() {
        return "P15_C_Back";
    }

}
