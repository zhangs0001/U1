package com.u1.gocashm.view.popupwindow;

import android.content.Context;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.u1.gocashm.R;
import com.u1.gocashm.adapter.PhSelectAdapter;
import com.u1.gocashm.model.response.DictionaryPhModel;

import java.util.List;


/**
 * Created by jishudiannao on 2018/9/5.
 */
public class SelectListPopupWindow extends BasePopupWindow {

    private Context mContext;
    private RecyclerView rv;
    private List<DictionaryPhModel.Dictionary> mList;
    private PhSelectAdapter adapter;
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;

    public SelectListPopupWindow(Context context, List<DictionaryPhModel.Dictionary> list, BaseQuickAdapter.OnItemClickListener onItemClickListener) {
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
        adapter = new PhSelectAdapter(R.layout.item_select, mList);
        adapter.setOnItemClickListener(mOnItemClickListener);
        rv.setAdapter(adapter);
    }

    public void setList(List<DictionaryPhModel.Dictionary> list) {
        mList.clear();
        mList.addAll(list);
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

}
