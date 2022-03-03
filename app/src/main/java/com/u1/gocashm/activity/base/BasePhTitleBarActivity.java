package com.u1.gocashm.activity.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.view.TitleBar;

/**
 * Created by jishudiannao on 2018/8/31.
 */

public abstract class BasePhTitleBarActivity extends BasePhActivity {

    protected TitleBar phTitleBar;
    private int phColor;

    @Override
    protected void onResume() {
        super.onResume();
        initTitleBar();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(makeFinalContentView(layoutResID));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(makeFinalContentView(view));
    }

    protected View makeFinalContentView(int layoutResID) {
        return makeFinalContentView(getLayoutInflater().inflate(layoutResID, null, false));
    }

    protected View makeFinalContentView(View contentView) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        phTitleBar = new TitleBar(this);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                PhUtils.dip2px(this, 45));
        phTitleBar.setLayoutParams(titleParams);
        linearLayout.addView(phTitleBar);
        LinearLayout.LayoutParams contentViewLayoutParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        if (null == contentViewLayoutParams)
            contentViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(contentViewLayoutParams);
        linearLayout.addView(contentView);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        return linearLayout;
    }

    final public TitleBar getPhTitleBar() {
        return phTitleBar;
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView textView = findViewById(R.id.title_name);
        textView.setText(title);
        if (phColor != 0) {
            textView.setTextColor(phColor);
        }
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getString(resId));
    }

    public void setPhColor(int phColor) {
        this.phColor = phColor;
    }

    public void setTitleBack(String title) {
        setTitle(title);
        getPhTitleBar().setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setTitleBack(int resId) {
        setTitle(resId);
        getPhTitleBar().setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadRecordsEnable())  BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, getRecordBackKey());
                onBackPressed();
            }
        });
    }

    public void setTitleBack(String title, View.OnClickListener mOnClickListener) {
        setTitle(title);
        getPhTitleBar().setLeftBack(mOnClickListener);
    }

    public String getActTitle() {
        TextView textView = findViewById(R.id.title_name);
        String title = textView.getText().toString();
        return title;
    }

    public void hideTitlebar() {
        getPhTitleBar().setVisibility(View.GONE);
    }


    public void showTitlebar() {
        getPhTitleBar().setVisibility(View.VISIBLE);
    }

    public abstract void initTitleBar();

    protected String getRecordBackKey(){
        return "";
    }
}
