package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hewei
 * @date 2020/3/18 0018 下午 2:24
 */
public class VirusDialog extends AbsBaseCircleDialog {


    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_dialog_content)
    TextView tvDialogContent;

    private String title;
    private CharSequence content;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_virus, container);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tvDialogTitle.setText(title);
        tvDialogContent.setText(content);
        tvDialogContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.tv_dialog_confirm)
    public void onViewClicked() {
        dismiss();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent(Spannable content) {
        this.content = content;
    }

    public void setCancelable2(boolean enable) {
        setCancelable(enable);
        setCanceledOnTouchOutside(enable);
        setCanceledBack(enable);
    }
}
