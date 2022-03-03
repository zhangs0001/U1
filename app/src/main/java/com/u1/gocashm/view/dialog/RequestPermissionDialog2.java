package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author ADMIN
 */
public class RequestPermissionDialog2 extends AbsBaseCircleDialog {

    private int dialogIconId;
    private int dialogTitle;
    private int dialogContent;
    private int dialogHint;

    private TextView tvPermissionTitle;
    private TextView tvPermissionContent;
    private TextView tvPermissionHint;
    private TextView tvPermissionConfirm;
    private TextView tvPermissionCancel;
    private ImageView ivPermissionIcon;

    public void setData(int dialogIconId, int dialogTitle, int dialogContent, int dialogHint) {
        this.dialogIconId = dialogIconId;
        this.dialogTitle = dialogTitle;
        this.dialogContent = dialogContent;
        this.dialogHint = dialogHint;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_request_permission2, container);
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void initView(View view) {
        tvPermissionTitle = view.findViewById(R.id.tv_permission_title);
        tvPermissionContent = view.findViewById(R.id.tv_permission_content);
        tvPermissionHint = view.findViewById(R.id.tv_permission_hint);
        tvPermissionConfirm = view.findViewById(R.id.tv_permission_confirm);
        tvPermissionCancel = view.findViewById(R.id.tv_permission_cancel);
        ivPermissionIcon = view.findViewById(R.id.iv_dialog_icon);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setCanceledBack(false);
    }

    private void initData() {
        tvPermissionTitle.setText(dialogTitle);
        tvPermissionContent.setText(dialogContent);
        tvPermissionHint.setText(dialogHint);
        ivPermissionIcon.setImageResource(dialogIconId);
    }

    private void initListener() {
        tvPermissionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                confirmListener.OnConfirmClick();
            }
        });
        tvPermissionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 定义一个接口对象listener
     */
    private OnConfirmClick confirmListener;

    /**
     * 定义一个接口对象
     */
    public interface OnConfirmClick {
        void OnConfirmClick();
    }


    public void setConfirmListener(OnConfirmClick confirmListener) {
        this.confirmListener = confirmListener;
    }
}
