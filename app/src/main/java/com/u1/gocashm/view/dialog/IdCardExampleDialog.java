package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author hewei
 * @date 2018/12/6 0006 下午 5:08
 */
public class IdCardExampleDialog extends AbsBaseCircleDialog {

    private ImageView close;
    private LinearLayout llHoldSample;
    private LinearLayout llLivenessSample;
    private boolean isLiveness;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_idcard_example, container);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        llHoldSample = view.findViewById(R.id.ll_hold_sample_layout);
        llLivenessSample = view.findViewById(R.id.ll_liveness_sample_layout);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        close = view.findViewById(R.id.close);
        if (isLiveness){
            llLivenessSample.setVisibility(View.VISIBLE);
            llHoldSample.setVisibility(View.GONE);
        }else {
            llHoldSample.setVisibility(View.VISIBLE);
            llLivenessSample.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setLiveness(boolean liveness) {
        isLiveness = liveness;
    }
}
