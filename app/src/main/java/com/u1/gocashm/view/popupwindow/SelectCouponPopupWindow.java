package com.u1.gocashm.view.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.u1.gocashm.R;
import com.u1.gocashm.adapter.MyEffCouponAdapter;
import com.u1.gocashm.model.response.CouponModel;

import java.util.List;



public class SelectCouponPopupWindow extends BasePopupWindow {

    private Context mContext;
    private RecyclerView rv;
    private List<CouponModel.Coupon> mList;
    private MyEffCouponAdapter adapter;
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;

    public SelectCouponPopupWindow(Context context, List<CouponModel.Coupon> list, BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_selectlist, null));
        mContext = context;
        mList = list;
        mOnItemClickListener = onItemClickListener;
        initData();
        findViews();
        initViews();
        setOutsideTouchable(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findViews() {
        rv = findViewById(R.id.rv_selectlist);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        adapter = new MyEffCouponAdapter(R.layout.item_select, mList);
        adapter.setOnItemClickListener(mOnItemClickListener);
        rv.setAdapter(adapter);
    }

    public void setList(List<CouponModel.Coupon> list) {
        mList.clear();
        mList.addAll(list);
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

}
