package com.u1.gocashm.adapter;


import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.SurveyModel;

import java.util.List;

public class SurveyAdapter extends BaseQuickAdapter<SurveyModel, BaseViewHolder> {

    private List<SurveyModel> list;
    private String content;
    private int mPosition = 0;
    private ItemClick itemClick;

    public SurveyAdapter(@Nullable List<SurveyModel> data) {
        super(R.layout.item_survey_layout, data);
        list = data;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelected()) {
                    mPosition = i;
                }
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, final SurveyModel item) {
        final int position = helper.getAdapterPosition();
        TextView tvSurvey = helper.getView(R.id.tv_survey);
        CheckBox cbSurvey = helper.getView(R.id.cb_survey);
        tvSurvey.setText(item.getContent());
        cbSurvey.setChecked(item.isSelected());
        if (cbSurvey.isChecked()) {
            tvSurvey.setTextColor(0xFF296DB8);
        } else {
            tvSurvey.setTextColor(0xFF565656);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = item.getContent();
                list.get(mPosition).setSelected(false);
                //设置新的Item勾选状态
                mPosition = position;
                list.get(mPosition).setSelected(true);
                itemClick.ItemClick(content);
                notifyDataSetChanged();
            }
        });
    }

    //定义一个接口
    public interface ItemClick {
        void ItemClick(String content);

    }

    public void setOnItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
}
