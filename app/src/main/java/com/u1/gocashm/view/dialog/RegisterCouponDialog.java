package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterCouponDialog extends AbsBaseCircleDialog {
    @BindView(R.id.textView2)
    TextView textView2;
    /**
     * 1免息券，2抵扣券
     */
    public static int couponType = -1;
    public static String endTime;

    private OnLisstener onLisstener;

    public void setOnLisstener(OnLisstener onLisstener) {
        this.onLisstener = onLisstener;
    }

    public static void showRegisterCouponDialog(@NonNull FragmentManager manager, @Nullable String tag) {
        RegisterCouponDialog couponDialog = new RegisterCouponDialog();
        couponDialog.setCancelable(false);
        couponDialog.show(manager,tag);
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog__register_coupon, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if (couponType == 2) {
            textView2.setText(getString(R.string.a33));
        }
    }

    @OnClick({R.id.closeView, R.id.okView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.okView:
                if (onLisstener != null) {
                    onLisstener.onOkClick(this);
                }
                dismiss();
                couponType = -1;
                break;
            case R.id.closeView:
                dismiss();
                couponType = -1;
                break;
        }
    }


    public interface  OnLisstener{
        void onOkClick(RegisterCouponDialog dialog);
    }
}
