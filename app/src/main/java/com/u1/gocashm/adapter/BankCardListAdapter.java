package com.u1.gocashm.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.request.BankCardListModel;
import com.u1.gocashm.util.model.ContactInfo;

import org.w3c.dom.Text;

import java.util.List;

public class BankCardListAdapter extends BaseQuickAdapter<BankCardListModel.bankcardlist, BaseViewHolder> {
    public BankCardListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCardListModel.bankcardlist item) {
        //注册点击事件
        helper.addOnClickListener(R.id.bt_delete);
        helper.addOnClickListener(R.id.bt_repayment);
        TextView tv_CardNumber = helper.getView(R.id.tv_CardNumber);
        tv_CardNumber.setText(item.getCard_number() + "");
    }
}
