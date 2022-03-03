package com.u1.gocashm.activity.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.tu.loadingdialog.LoadingDailog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.PhApplication;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.request.BehaviorRecordReqModel;
import com.u1.gocashm.util.BehaviorPhUtils;

import java.util.List;


public abstract class BasePhActivity extends AppCompatActivity {
    protected BehaviorRecordReqModel mBehaviorRecord;
    private LoadingDailog.Builder phBuilder;
    private LoadingDailog phDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (uploadRecordsEnable()) mBehaviorRecord = BehaviorPhUtils.setEnterPageModifyV2(getRecordEnterKey(), getBaseContext());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        PhApplication.getInstance().addActivityToSet(this);
        LogUtils.i("BasePhActivity --> " + this);
    }

    @Override
    protected void onDestroy() {
        if(phDialog != null) {
            phDialog.dismiss();
        }
        if (uploadRecordsEnable()) uploadBehaviorRecords(getRecords());
        super.onDestroy();
    }

    public void showToast(int stringRes) {
        ToastUtils.showShort(stringRes);
    }

    public void showToast(String message) {
        ToastUtils.showShort(message);
    }


    public void showLoadingDialog() {
        phBuilder = new LoadingDailog.Builder(this)
                .setCancelable(true)
                .setCancelOutside(true);
        phDialog = phBuilder.create();
        phDialog.show();
    }

    public void closeLoadingDialog() {
        if (phDialog != null && phDialog.isShowing()) {
            phDialog.dismiss();
        }
    }

    private void uploadBehaviorRecords(List<BehaviorRecord> records) {
        if (records != null) {
            for (BehaviorRecord record : records) {
                if (!mBehaviorRecord.getRecords().contains(record)) {
                    mBehaviorRecord.getRecords().add(record);
                }
            }
        }
        BehaviorPhUtils.setDestroyModifyV2(mBehaviorRecord, getRecordLeaveKey());
        BehaviorPhUtils.saveBehaviorReqModelV2(mBehaviorRecord);
    }

    protected boolean uploadRecordsEnable(){
        return false;
    }

    protected String getRecordEnterKey() {
        return "";
    }

    protected String getRecordLeaveKey() {
        return "";
    }

    protected List<BehaviorRecord> getRecords() {
        return null;
    }
}
