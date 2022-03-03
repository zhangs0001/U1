package com.u1.gocashm.adapter;


import android.graphics.drawable.Drawable;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.NoticeListModel;
import com.u1.gocashm.util.NoticeMananger;
import com.u1.gocashm.util.PhUtils;

import java.util.List;

public class NoticeAdapter extends BaseQuickAdapter<NoticeListModel.Notice, BaseViewHolder> {
    private Drawable drawable;

    public NoticeAdapter(@Nullable List<NoticeListModel.Notice> data) {
        super(R.layout.item_message_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeListModel.Notice item) {
        helper.setText(R.id.tv_notice_time, item.getDatetime());
        helper.setText(R.id.tv_notice_title, item.getTitle());
        TextView tvTitle = helper.getView(R.id.tv_notice_title);
        if (drawable == null) {
            drawable = mContext.getResources().getDrawable(R.drawable.bg_circular_red_5_5);
            drawable.setBounds(0, 0, PhUtils.dip2px(mContext, 5), PhUtils.dip2px(mContext, 5));
        }
        tvTitle.setCompoundDrawables(!item.isRead() ? drawable : null, null, null, null);
        tvTitle.setCompoundDrawablePadding(!item.isRead() ? PhUtils.dip2px(mContext, 2) : 0);
        TextView tvContent = helper.getView(R.id.tv_notice_content);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvContent.setText(NoticeMananger.formatNoticeContent(mContext, item));


    }
}
