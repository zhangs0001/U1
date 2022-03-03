package com.u1.gocashm.activity.loan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.u1.gocashm.PhApplication;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.request.BehaviorRecordReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.dialog.SurveyDialog;

import java.util.ArrayList;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public abstract class IdentityInfoActivity extends BasePhTitleBarActivity {
    protected BehaviorRecordReqModel mBehaviorRecord;
    /**
     * 复贷跳转到第几步
     */
    protected ArrayList<Integer> steps;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBehaviorRecord = BehaviorPhUtils.setEnterPageModifyV2(getRecordEnterKey(), getBaseContext());
        steps = getIntent().getIntegerArrayListExtra("steps");

        if (isStepEmpty()) {
            Class aClass = getStepByClass(steps.get(0));
            if (aClass.equals(this.getClass())) {
                steps.remove(0);
            }
        }
    }


    protected String getRecordEnterKey() {
        return "P0" + (getIndexStep() + 1) + getString(R.string.a3);
    }

    protected String getRecordLeaveKey() {
        return "P0" + (getIndexStep() + 1) + getString(R.string.a4);
    }

    protected String getRecordBackKey() {
        return "P0" + (getIndexStep() + 1) + getString(R.string.a5);
    }


    private boolean isStepEmpty() {
        return steps != null && !steps.isEmpty();
    }

    protected void switchStep(Class target) {
        switchStep(target, null);
    }

    protected void switchStep(Class target, Bundle args) {
        target = isStepEmpty() ? getStepByClass(steps.remove(0)) : target;
        if (target == null) return;
        Intent intent = new Intent(this, target);
        intent.putIntegerArrayListExtra("steps", steps);
        if (args != null) intent.putExtras(args);
        startActivity(intent);
    }

    public static Class getStepByClass(int step) {
        switch (step) {
            case 1:
                return LoanIdentityPhActivity.class;
            case 2:
                return LoanAddressPhActivity.class;
            case 3:
                return JobInfoPhActivity.class;
            case 4:
                return IdCardPhActivity.class;
            case 5:
                return PayInfoPhActivity.class;
            default:
                return LoanIdentityPhActivity.class;
        }
    }

    public int getIndexStep() {
        if (this.getClass() == LoanIdentityPhActivity.class) return 1;
        if (this.getClass() == LoanAddressPhActivity.class) return 2;
        if (this.getClass() == JobInfoPhActivity.class) return 3;
        if (this.getClass() == IdCardPhActivity.class) return 4;
        if (this.getClass() == PayInfoPhActivity.class) return 5;
        return 0;
    }

    protected void uploadBehaviorRecords(InputView... inputViews) {
        if (mBehaviorRecord == null) return;
        for (InputView inputView : inputViews) {
            inputView.clearFocus();
        }
        for (InputView inputView : inputViews) {
            if (inputView.getBehaviorRecord() == null) continue;
            if (TextUtils.isEmpty(inputView.getBehaviorRecord().getControlNo())) continue;
            mBehaviorRecord.getRecords().add(inputView.getBehaviorRecord());
        }
        BehaviorPhUtils.setDestroyModifyV2(mBehaviorRecord, getRecordLeaveKey());
        BehaviorPhUtils.saveBehaviorReqModelV2(mBehaviorRecord);
    }

    protected void uploadBehaviorRecords(BehaviorRecord... records) {
        if (mBehaviorRecord == null) return;
        for (BehaviorRecord record : records) {
            mBehaviorRecord.getRecords().add(record);
        }
        BehaviorPhUtils.setDestroyModifyV2(mBehaviorRecord, getRecordLeaveKey());
        BehaviorPhUtils.saveBehaviorReqModelV2(mBehaviorRecord);
    }

    @Override
    public void onBackPressed() {
        showSurveyDialog();
    }

    private void showSurveyDialog() {
        final int step = getIndexStep();
        final BehaviorRecord pBackRecord = BehaviorPhUtils.setDestroyModifyV2(mBehaviorRecord, getRecordBackKey());
        SurveyDialog dialog = new SurveyDialog();
        dialog.setStep(String.valueOf(step));
        dialog.setLeaveListener(new SurveyDialog.OnLeaveClick() {
            @Override
            public void OnLeaveClick(String content) {
                if (TextUtils.isEmpty(content)) {
                    showToast(getString(R.string.a2));
                    return;
                }
                NetworkPhRequest.submitUserSurvey(TokenUtils.getToken(PhApplication.getContext()), String.valueOf(step),
                        content, new PhSubscriber<BasePhModel>() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                showLoadingDialog();
                            }

                            @Override
                            public void onNext(BasePhModel basePhModel) {
                                super.onNext(basePhModel);
                                closeLoadingDialog();
                                String code = basePhModel.getCode();
                                if (CODE_SUCCESS.equals(code)) {
                                    dialog.dismiss();
                                    pBackRecord.setEndTime();
                                    finish();
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
}
