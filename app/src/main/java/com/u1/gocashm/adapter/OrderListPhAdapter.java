package com.u1.gocashm.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.OrderListModel;

import java.util.List;

/**
 * Created by jishudiannao on 2018/9/10.
 */

public class OrderListPhAdapter extends BaseQuickAdapter<OrderListModel.OrderList.Order, BaseViewHolder> {
    public OrderListPhAdapter( @Nullable List<OrderListModel.OrderList.Order> data) {
        super(R.layout.item_orderlist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListModel.OrderList.Order item) {
        helper.setText(R.id.tv_orderlist_number, item.getOrder_no());
        helper.setText(R.id.tv_orderlist_amount, mContext.getString(R.string.loan_how_much )+  item.getPrincipal());
        helper.setText(R.id.tv_orderlist_date, item.getCreated_at());
        helper.setText(R.id.tv_orderlist_term, mContext.getString(R.string.loan_how_long) + item.getLoan_days());
        helper.setText(R.id.tv_orderlist_status, item.getApi_status());
    }

}
