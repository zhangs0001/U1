package com.u1.gocashm.activity.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.PhApplication;

public abstract class BasePhFragmentActivity extends FragmentActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        PhApplication.getInstance().addActivityToSet(this);
    }

    @Override
    protected void onDestroy() {
        PhApplication.getInstance().removeActivityFromSet(this);
        super.onDestroy();
    }

    public void showToast(int stringRes) {
        ToastUtils.showShort(stringRes);
    }

    public void showToast(String message) {
        ToastUtils.showShort(message);
    }

}
