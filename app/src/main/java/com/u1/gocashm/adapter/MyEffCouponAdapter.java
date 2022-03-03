package com.u1.gocashm.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.CouponModel;

import java.util.List;

/**
 * Created by jishudiannao on 2018/9/3.
 */

public class MyEffCouponAdapter extends BaseQuickAdapter<CouponModel.Coupon, BaseViewHolder> {
    public MyEffCouponAdapter(int layoutResId, @Nullable List<CouponModel.Coupon> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponModel.Coupon item) {
        helper.setText(R.id.tv_item_test, item.getTitle());
    }

}
