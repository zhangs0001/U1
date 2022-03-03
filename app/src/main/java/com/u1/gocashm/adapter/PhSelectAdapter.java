package com.u1.gocashm.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.DictionaryPhModel;

import java.util.List;

/**
 * Created by jishudiannao on 2018/9/3.
 */

public class PhSelectAdapter extends BaseQuickAdapter<DictionaryPhModel.Dictionary, BaseViewHolder> {
    public PhSelectAdapter(int layoutResId, @Nullable List<DictionaryPhModel.Dictionary> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DictionaryPhModel.Dictionary item) {
        helper.setText(R.id.tv_item_test, item.getName());
    }

}
