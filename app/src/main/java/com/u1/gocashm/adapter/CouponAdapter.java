package com.u1.gocashm.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.CouponModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author hewei
 * @date 2018/11/9 0009 上午 11:20
 */
public class CouponAdapter extends BaseQuickAdapter<CouponModel.Coupon, BaseViewHolder> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private List<CouponModel.Coupon> list;
    public List<CouponModel.Coupon> checkeds = new ArrayList<>();
    private String type;


    public CouponAdapter(int layoutResId, @Nullable List<CouponModel.Coupon> data) {
        super(layoutResId, data);
        this.list = data;
    }

    public List<CouponModel.Coupon> getCheckeds() {
        return checkeds;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BaseViewHolder holder, int position, @NonNull @NotNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            ((CheckBox) holder.itemView.findViewById(R.id.is_interest_select)).setChecked((Boolean) payloads.get(0));
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponModel.Coupon item) {
        final int position = helper.getAdapterPosition();
        final CheckBox checkBox = helper.getView(R.id.is_interest_select);
        checkBox.setChecked(item.isSelected());
        switch (type) {
            case "0":
                checkBox.setVisibility(View.VISIBLE);
                helper.setVisible(R.id.id_coupon_disable, false);
                helper.setBackgroundRes(R.id.interest_value_tv, R.drawable.coupon_redeemed_bg);
                break;
            case "1":
                checkBox.setVisibility(View.GONE);
                helper.setVisible(R.id.id_coupon_disable, true);
                helper.setBackgroundRes(R.id.interest_value_tv, R.drawable.coupon_redeemed_bg);
                ((ImageView) helper.getView(R.id.id_coupon_disable)).setImageResource(R.drawable.coupon_redeemed_finished);
                break;
            case "2":
                checkBox.setVisibility(View.GONE);
                helper.setVisible(R.id.id_coupon_disable, true);
                helper.setBackgroundRes(R.id.interest_value_tv, R.drawable.coupon_expired_bg);
                ((ImageView) helper.getView(R.id.id_coupon_disable)).setImageResource(R.drawable.coupon_expired);
                break;
        }
        if (1 == item.getCoupon_type()) {
            //1免息卷
            helper.setText(R.id.interest_value_tv, "INTEREST FREE");
            ((TextView) helper.getView(R.id.interest_value_tv)).setTextSize(14);
        } else {
            //2优惠卷
            ((TextView) helper.getView(R.id.interest_value_tv)).setTextSize(25);
            helper.setText(R.id.interest_value_tv, String.valueOf(item.getUsed_amount()) + " EGP");
        }

        helper.setText(R.id.validity_period_tv, "Valid Until " + sdf.format(new Date(item.getEnd_time().replaceAll("-", "/"))));
        helper.setText(R.id.interest_remark_tv, item.getTitle());
        helper.getView(R.id.is_interest_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    for (int i = 0; i < list.size() && !checkeds.isEmpty(); i++) {
                        if (list.get(i).getId() == checkeds.get(0).getId()) {
                            list.get(i).setSelected(false);
                            checkeds.clear();
                            CouponAdapter.this.notifyItemChanged(i, false);
                            break;
                        }
                    }
                    //选中状态
                    //不存在就存进去
                    if (!checkeds.contains(list.get(position))) {
                        list.get(position).setSelected(true);
                        checkeds.add(list.get(position));
                    }
                } else {
                    //未选中状态
                    checkeds.clear();
                }
            }
        });
    }

    public List<CouponModel.Coupon> getList() {
        return list;
    }
}
