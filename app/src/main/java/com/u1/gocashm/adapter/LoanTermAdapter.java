package com.u1.gocashm.adapter;

import android.graphics.Color;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.LoanTermModel;

import java.util.List;

public class LoanTermAdapter extends BaseQuickAdapter<LoanTermModel, BaseViewHolder> {

    private List<LoanTermModel> list;
    private int loanTerm;
    private int mPosition = 0;
    private ItemClick itemClick;

    public LoanTermAdapter(int layoutResId, @Nullable List<LoanTermModel> data) {
        super(layoutResId, data);
        list = data;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelected()) {
                    mPosition = i;
                }
            }
            loanTerm = list.get(mPosition).getLoanTerm();
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, final LoanTermModel item) {
        final int position = helper.getAdapterPosition();
        final TextView tvLoanTerm = helper.getView(R.id.tv_loan_term);
        tvLoanTerm.setText(item.getLoanTerm() + "Days");
        tvLoanTerm.setEnabled(item.isAvailable());
        if (item.isAvailable()) {
            if (item.isSelected()) {
                tvLoanTerm.setBackgroundResource(R.drawable.bg_term_unavailable_select);
                tvLoanTerm.setTextColor(Color.WHITE);
            } else {
                tvLoanTerm.setBackgroundResource(R.drawable.bg_term_unavailable);
                tvLoanTerm.setTextColor(Color.parseColor("#FFB739"));
            }
        }
        tvLoanTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loanTerm = item.getLoanTerm();
                list.get(mPosition).setSelected(false);
                //设置新的Item勾选状态
                mPosition = position;
                list.get(mPosition).setSelected(true);
                itemClick.ItemClick(loanTerm);
                notifyDataSetChanged();
            }
        });
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    //定义一个接口
    public interface ItemClick {
        void ItemClick(int loanTerm);

    }

    public void setOnItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
}
