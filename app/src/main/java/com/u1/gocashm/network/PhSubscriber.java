package com.u1.gocashm.network;

import android.util.Log;

import com.u1.gocashm.PhApplication;
import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.view.dialog.UpdateUserLevelDialog;

import rx.Subscriber;


/**
 * by y on 2016/5/6.
 */
public class PhSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "PhSubscriber";

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage());
    }

    @Override
    public void onNext(T t) {
        if (t instanceof AbstractBaseRespBean) {
            String code = ((AbstractBaseRespBean<?, ?>) t).getCode();
            if ("1403".equals(code) && TokenUtils.TokenCheck(PhApplication.getContext())) {
                SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).logout();
                SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).clearApplyData();
                UpdateUserLevelDialog.isShowUpgradeLevelDialog = true;
                RxBus.getDefault().post(new TagEvent(EventTagPh.LOGOUT));
            }
        }
        Log.d(TAG, "onNext");
    }
}
