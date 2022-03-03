package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author hewei
 * @date 2019/5/8 0008 下午 2:52
 */
public class NoCardHintDialog extends AbsBaseCircleDialog {

    private TextView confirmBtn;
    private TextView cancelBtn;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_no_card_hint, container);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        confirmBtn = view.findViewById(R.id.tv_confirm);
        cancelBtn = view.findViewById(R.id.tv_cancel);
        setRadius(30);
    }

    private void initListener() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener2.OnCancelClick();
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick();
                dismiss();
            }
        });
    }

    //定义一个接口对象listener
    private OnClick listener;
    private OnCancelClick listener2;

    //获得接口对象的方法。
    public void setOnClick(OnClick listener) {
        this.listener = listener;
    }

    public void setOnCancelClick(OnCancelClick listener2) {
        this.listener2 = listener2;
    }

    //定义一个接口
    public interface OnClick {
        void OnClick();
    }

    //定义一个接口
    public interface OnCancelClick {
        void OnCancelClick();
    }
}
