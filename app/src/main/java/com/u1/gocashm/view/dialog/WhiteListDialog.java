package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author hewei
 * @date 2018/11/13 0013 下午 7:42
 */
public class WhiteListDialog extends AbsBaseCircleDialog {

    private ImageView closeBtn;
    private EditText phoneEt;
    private TextView confirmBtn;

    private String phoneNum;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_whitelist_layout, container);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        closeBtn = view.findViewById(R.id.close);
        phoneEt = view.findViewById(R.id.white_list_phone);
        confirmBtn = view.findViewById(R.id.estimate_btn);
        setWidth(1.0f);
    }


    private void initListener() {
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNum = phoneEt.getText().toString();
                listener.OnClick();
            }
        });
    }

    public String getPhoneNum() {
        return phoneNum;
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
