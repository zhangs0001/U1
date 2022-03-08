package com.u1.gocashm.view.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.u1.gocashm.R;

public class ReimbursementCodesDialog extends Dialog {

    private Context mContext;
    private TextView tvReferenceNumber;
    private TextView tvExpiryDate;
    private LinearLayout tvNumberLayout;
    private ImageView ivWalletQr;
    private LinearLayout lin_walletQr;
    private ImageView iv_copy;
    private TextView tvReferenceNumber1;

    private ClipboardManager cm;

    public void setTextData(String method, String requestNo, String msg, String walletQr) {
        if ("referenceNumber".equals(method)) {
            tvNumberLayout.setVisibility(View.VISIBLE);
            lin_walletQr.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(requestNo) && !TextUtils.isEmpty(msg)) {
                tvReferenceNumber.setText(requestNo);
                tvExpiryDate.setText(msg);
            }
        } else if ("eWallet".equals(method)) {
            lin_walletQr.setVisibility(View.VISIBLE);
            tvNumberLayout.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(walletQr)) {
                tvReferenceNumber1.setText(requestNo);
                Glide.with(mContext)
                        .load(walletQr)
                        .into(ivWalletQr);
            }
        }
    }

    public ReimbursementCodesDialog(@NonNull Context context) {
        this(context, 0);
    }

    public ReimbursementCodesDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.PesDialog);
        mContext = context;
        setContentView(R.layout.dialog_reimbursement_codes);
        setCancelable(true);
        initView();
    }

    private void initView() {
        tvNumberLayout = findViewById(R.id.tv_number_layout);
        ivWalletQr = findViewById(R.id.walletQr);
        tvReferenceNumber = findViewById(R.id.tv_reference_number);
        tvExpiryDate = findViewById(R.id.tv_expiry_date);
        lin_walletQr = findViewById(R.id.lin_walletQr);
        iv_copy = findViewById(R.id.iv_copy);
        tvReferenceNumber1 = findViewById(R.id.tv_reference_number1);
        cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        iv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm.setText(tvReferenceNumber1.getText().toString());
            }
        });
    }
}
