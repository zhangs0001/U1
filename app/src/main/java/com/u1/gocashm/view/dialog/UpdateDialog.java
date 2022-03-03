package com.u1.gocashm.view.dialog;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.VersionPhModel;
import com.mylhyl.circledialog.AbsBaseCircleDialog;
/**
 * @author hewei
 * @date 2018/12/28 0028 下午 4:43
 */
public class UpdateDialog extends AbsBaseCircleDialog {

    private Activity activity;
    private View mCancelView;
    private View mYesView;
    private TextView mContentView;
    private VersionPhModel.Version version;
    private DialogClickListener dialogClickListener;

    public void setVersion(VersionPhModel.Version version) {
        this.version = version;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_update_layout, container);
        initView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        return view;
    }

    private void initView(View view) {
        mCancelView=view.findViewById(R.id.cancel);
        mYesView =view.findViewById(R.id.yes);

        mContentView =view.findViewById(R.id.tv_upgrade_content);
        view.findViewById(R.id.tv_copy).setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", version.getUrl());
            cm.setPrimaryClip(mClipData);
            ToastUtils.showShort(getString(R.string.a34));
        });

        mCancelView.setOnClickListener(v -> {
            dismiss();
            if (dialogClickListener != null) {
                dialogClickListener.onClickCancel();
            }
        });

        mYesView.setOnClickListener(v -> {
            dismiss();
            if (dialogClickListener != null) {
                dialogClickListener.onClickYes();
            }
        });

        mContentView.setText(version.getContent());
        if (version.isForcible()) {
            mYesView.setBackground(getContext().getResources().getDrawable(R.drawable.bg_btn_bottom_round));
            mCancelView.setVisibility(View.GONE);
        }
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public UpdateDialog setDialogClickListener(DialogClickListener clickListener){
        this.dialogClickListener=clickListener;
        return this;
    }
    public interface DialogClickListener{
        void onClickCancel();
        void onClickYes();
    }
}
