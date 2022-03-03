package com.u1.gocashm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.util.model.ContactInfo;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author hewei
 * @date 2020/2/25 0025 下午 5:07
 */
public class ContactInfoAdapter extends BaseQuickAdapter<ContactInfo, BaseViewHolder> {

    public ContactInfoAdapter(int layoutResId, @Nullable List<ContactInfo> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ContactInfo item) {
        helper.setText(R.id.tv_contact_name, item.getContactFullname());
        helper.setText(R.id.tv_contact_phone, item.getContactTelephone());
        helper.setText(R.id.tv_contact_relation, item.getContactRelation());
    }
}
