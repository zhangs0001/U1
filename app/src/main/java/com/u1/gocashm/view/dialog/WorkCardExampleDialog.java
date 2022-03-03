package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author hewei
 * @date 2018/12/6 0006 下午 5:08
 */
public class WorkCardExampleDialog extends AbsBaseCircleDialog {

    private ImageView close;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_work_card_example, container);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        close = view.findViewById(R.id.close);
    }

    private void initListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
