package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepayAmountDialog extends AbsBaseCircleDialog {


    @BindView(R.id.et_repay_amount)
    EditText etRepayAmount;
    @BindView(R.id.tv_repay_amount_error)
    TextView tvRepayAmountError;

    private String repayAmount;
    private Double minAmount;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_repay_amount, container);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        minAmount = 100d;
    }

    @OnClick({R.id.tv_input_total_amount, R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                confirmListener.onCancelClick();
                break;
            case R.id.tv_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        repayAmount = etRepayAmount.getText().toString();
        if (TextUtils.isEmpty(repayAmount)) {
            setAmountError(getString(R.string.repay_min_amount_hint, String.valueOf(minAmount)));
            return;
        }
        Double amount = Double.parseDouble(repayAmount);
        if (amount < minAmount) {
            setAmountError(getString(R.string.repay_min_amount_hint, String.valueOf(minAmount)));
            return;
        }
        confirmListener.OnConfirmClick(amount);
        dismiss();
    }

    private void setAmountError(String text) {
        tvRepayAmountError.setText(text);
        tvRepayAmountError.setVisibility(View.VISIBLE);
    }

    /**
     * 定义一个接口对象listener
     */
    private OnConfirmClick confirmListener;

    /**
     * 定义一个接口对象
     */
    public interface OnConfirmClick {
        void OnConfirmClick(Double repayAmount);
        default void onCancelClick(){};
    }


    public void setConfirmListener(OnConfirmClick confirmListener) {
        this.confirmListener = confirmListener;
    }
}
