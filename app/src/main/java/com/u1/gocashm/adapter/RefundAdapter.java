package com.u1.gocashm.adapter;

/*
TODO com.u5.cash_cat.adapter 
TODO Time: 2022/2/12 11:46 
TODO Name: zhang
    * 入乡随俗 *
*/

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.request.RefundResult;

public class RefundAdapter extends BaseQuickAdapter<RefundResult.Refund, BaseViewHolder> {

    public RefundAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundResult.Refund item) {
        helper.addOnClickListener(R.id.lin_copy);
        helper.setText(R.id.tv_bankNameKey,item.getBankNameKey());
        helper.setText(R.id.tv_bankName,item.getBankName());
        helper.setText(R.id.tv_branchNameKey,item.getBranchNameKey());
        helper.setText(R.id.tv_branchName,item.getBranchName());
        helper.setText(R.id.tv_accountNumerKey,item.getAccountNumerKey());
        helper.setText(R.id.tv_number,item.getAccountNumber());
    }

}
