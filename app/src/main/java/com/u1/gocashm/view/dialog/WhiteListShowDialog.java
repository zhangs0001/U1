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
 * @date 2018/12/20 0020 下午 3:43
 */
public class WhiteListShowDialog extends AbsBaseCircleDialog {
    private TextView confirmBtn;
    private TextView nextBtn;

    //定义一个接口对象listener
    private OnConfirmClick confirmListener;

    //定义一个接口对象listener
    private OnNextClick nextListener;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_white_list_layout, container);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        confirmBtn = view.findViewById(R.id.white_list_confirm_btn);
        nextBtn = view.findViewById(R.id.white_list_next_btn);
    }

    private void initListener() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmListener.OnConfirmClick();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextListener.OnNextClick();
            }
        });
    }

    //定义一个接口
    public interface OnConfirmClick {
        void OnConfirmClick();
    }

    //定义一个接口
    public interface OnNextClick {
        void OnNextClick();
    }

    public void setConfirmListener(OnConfirmClick confirmListener) {
        this.confirmListener = confirmListener;
    }

    public void setNextListener(OnNextClick nextListener) {
        this.nextListener = nextListener;
    }
}
