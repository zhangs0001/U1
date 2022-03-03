package com.u1.gocashm.activity.loan;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.util.StatusPhBarUtil;

/**
 * @author hewei
 * @date 2018/9/29 0029 下午 2:38
 */
public class ExamplePhotoPhActivity extends BasePhTitleBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_photo);
    }

    @Override
    public void initTitleBar() {
        setTitleBack("Example");
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }
}
