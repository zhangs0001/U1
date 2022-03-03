package com.u1.gocashm.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.RecommendModel;

import java.util.List;


/**
 * @author hewei
 * @date 2018/11/9 0009 下午 2:58
 */
public class PromotionAdapter extends BaseQuickAdapter<RecommendModel.Recommend, BaseViewHolder> {

    public PromotionAdapter(int layoutResId, @Nullable List<RecommendModel.Recommend> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendModel.Recommend item) {
        helper.setText(R.id.user_phone_tv, item.getInvited_user().getTelephone());
        helper.setText(R.id.user_promotion_time_tv, String.format(mContext.getString(R.string.new_user_time), item.getUpdated_at()));
    }
}
