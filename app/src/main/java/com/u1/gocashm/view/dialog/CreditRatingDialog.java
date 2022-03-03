package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firstinvest.chlibrary.BuildConfig;
import com.u1.gocashm.R;
import com.u1.gocashm.util.TokenUtils;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author hewei
 * @date 2019/5/8 0008 下午 2:52
 */
public class CreditRatingDialog extends AbsBaseCircleDialog {

    private TextView confirmBtn;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_credit_rating, container);
        initView(view);
        initListener();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        return view;
    }

    private void initView(View view) {
        confirmBtn = view.findViewById(R.id.tv_dialog_confirm);
        setRadius(30);
    }

    private void initListener() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?appKey=%s&token=%s",("https://www.zegentech.net/CreditEvaluation/"),BuildConfig.APP_KEY,TokenUtils.getToken(getContext()))));
                startActivity(intent);
                dismiss();
                if (listener != null) {
                    listener.OnClick();
                }
            }
        });
    }

    //定义一个接口对象listener
    private OnClick listener;

    //获得接口对象的方法。
    public void setOnClick(OnClick listener) {
        this.listener = listener;
    }


    //定义一个接口
    public interface OnClick {
        void OnClick();
    }

}
