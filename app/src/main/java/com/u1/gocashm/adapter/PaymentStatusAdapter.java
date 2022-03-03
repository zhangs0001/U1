package com.u1.gocashm.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.PaymentStatusBean;
import com.u1.gocashm.model.response.PaymentStatusModel;

public class PaymentStatusAdapter extends BaseQuickAdapter<PaymentStatusModel.PaymentStatus, BaseViewHolder> {

    public PaymentStatusAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PaymentStatusModel.PaymentStatus item) {
        helper.addOnClickListener(R.id.tv_unpaid);
        helper.setText(R.id.tv_payment_date,item.getCreated_at());
        helper.setText(R.id.tv_reference_number,item.getRepay_no());
        helper.setText(R.id.tv_amount,item.getAmount());
        helper.setText(R.id.tv_state_payment,item.getStatus());
        String status = item.getStatus();
        if (status.equals("UNPAID")){
            helper.getView(R.id.tv_unpaid).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.tv_unpaid).setVisibility(View.GONE);
        }
    }
}
