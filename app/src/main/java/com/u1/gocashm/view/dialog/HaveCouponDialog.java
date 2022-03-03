package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class HaveCouponDialog extends AbsBaseCircleDialog {


    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_have_coupon, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.tv_continue, R.id.tv_leave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_continue:
                dismiss();
                leave();
                break;
            case R.id.tv_leave:
                dismiss();
                break;
        }
    }

    private void leave() {
        leaveListener.OnConfirmClick(null);
    }


    /**
     * 定义一个接口对象listener
     */
    private OnConfrimClick leaveListener;

    /**
     * 定义一个接口对象
     */
    public interface OnConfrimClick {
        void OnConfirmClick(String content);
    }


    public void setConfirmListener(OnConfrimClick leaveListener) {
        this.leaveListener = leaveListener;
    }
}
