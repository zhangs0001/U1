package com.u1.gocashm.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;

import com.android.tu.loadingdialog.LoadingDailog;
import com.u1.gocashm.PhApplication;

public abstract class BasePhFragment extends Fragment implements View.OnClickListener {
    protected Context mContext = null;


    protected Activity mActivity = null;

    private LoadingDailog.Builder builderPh;
    private LoadingDailog dialogPh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    protected void initData() {
        mContext = PhApplication.getInstance();
        mActivity = getActivity();
    }

    @Override
    public void onClick(View v) {

    }

    protected void showLoadingDialog() {
        closeLoadingDialog();
        builderPh = new LoadingDailog.Builder(getActivity())
                .setCancelable(true)
                .setCancelOutside(true);
        dialogPh = builderPh.create();
        dialogPh.show();
    }

    protected void closeLoadingDialog() {
        if (dialogPh != null && dialogPh.isShowing()) {
            dialogPh.dismiss();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialogPh != null) {
            dialogPh.cancel();
        }
    }
}
