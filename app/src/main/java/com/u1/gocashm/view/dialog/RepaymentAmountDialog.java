package com.u1.gocashm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;

public class RepaymentAmountDialog extends Dialog {
    private Context mContext;
    private EditText edAmount;
    private TextView tvAmountError;
    private TextView tvrepayment_code;
    private TextView tvelectronic_wallet;
    private TextView tvbank_card;
    private ImageView ivclose;
    private DialogClickListener dialogClickListener;


    public RepaymentAmountDialog(@NonNull Context context) {
        this(context, 0);
    }

    public RepaymentAmountDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.PesDialog);
        mContext = context;
        setContentView(R.layout.dialog_repayment_amount);
        setCancelable(false);
        initView();
    }

    private void initView() {
        edAmount = findViewById(R.id.ed_amount);
        tvAmountError = findViewById(R.id.tv_amount_error);
        tvrepayment_code = findViewById(R.id.tv_repayment_code);
        tvelectronic_wallet = findViewById(R.id.tv_electronic_wallet);
        tvbank_card = findViewById(R.id.tv_bank_card);
        ivclose = findViewById(R.id.iv_close);
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInput();
                dismiss();
                if (dialogClickListener != null) {
                    dialogClickListener.onClickCancel();
                }
            }
        });
        tvrepayment_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edAmount.getText().toString().trim())) {
                    ToastUtils.showLong(mContext.getString(R.string.pleaserepaymentpage));
                    return;
                }
                String numberStr = edAmount.getText().toString();
                long number = Long.valueOf(numberStr);
                // todo 之前逻辑是number<100  现在改成number<1
                if (number < 1) {
                    tvAmountError.setVisibility(View.VISIBLE);
                } else {
                    tvAmountError.setVisibility(View.INVISIBLE);
                    hideInput();
                    dismiss();
                    if (dialogClickListener != null) {
                        dialogClickListener.onClickRepayment_Code(numberStr);
                    }
                }
            }
        });

        tvelectronic_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edAmount.getText().toString().trim())) {
                    ToastUtils.showLong(mContext.getString(R.string.pleaserepaymentpage));
                    return;
                }
                String numberStr = edAmount.getText().toString();
                long number = Long.valueOf(numberStr);
                // todo 之前逻辑是number<100  现在改成number<1
                if (number < 1) {
                    tvAmountError.setVisibility(View.VISIBLE);
                } else {
                    tvAmountError.setVisibility(View.INVISIBLE);
                    hideInput();
                    dismiss();
                    if (dialogClickListener != null) {
                        dialogClickListener.onClickElectronic_Wallet(numberStr);
                    }
                }
            }
        });

        tvbank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edAmount.getText().toString().trim())) {
                    ToastUtils.showLong(mContext.getString(R.string.pleaserepaymentpage));
                    return;
                }
                String numberStr = edAmount.getText().toString();
                long number = Long.valueOf(numberStr);
                // todo 之前逻辑是number<100  现在改成number<1
                if (number < 1) {
                    tvAmountError.setVisibility(View.VISIBLE);
                } else {
                    tvAmountError.setVisibility(View.INVISIBLE);
                    hideInput();
                    dismiss();
                    if (dialogClickListener != null) {
                        dialogClickListener.onClickBank_Card(numberStr);
                    }
                }
            }
        });
    }

    private void hideInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edAmount.getWindowToken(), 0);
    }

    public RepaymentAmountDialog setDialogClickListener(DialogClickListener clickListener) {
        this.dialogClickListener = clickListener;
        return this;
    }

    public interface DialogClickListener {
        void onClickCancel();
        void onClickRepayment_Code(String amount);
        void onClickElectronic_Wallet(String amount);
        void onClickBank_Card(String amount);
    }
}
