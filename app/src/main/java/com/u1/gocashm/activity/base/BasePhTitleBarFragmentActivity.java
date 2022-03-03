package com.u1.gocashm.activity.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.view.TitleBar;

/**
 * Created by jishudiannao on 2018/8/31.
 */

public abstract class BasePhTitleBarFragmentActivity extends BasePhFragmentActivity {

    protected TitleBar pHTitleBar;

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

        pHTitleBar = new TitleBar(this);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                PhUtils.dip2px(this,45));
        pHTitleBar.setLayoutParams(titleParams);
        linearLayout.addView(pHTitleBar);
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

    final public TitleBar getpHTitleBar() {
        return pHTitleBar;
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView textView = findViewById(R.id.title_name);
        textView.setText(title);
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getString(resId));
    }

    public void setTitleBack(String title){
        setTitle(title);
        getpHTitleBar().setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitleBack(String title, View.OnClickListener mOnClickListener){
        setTitle(title);
        getpHTitleBar().setLeftBack(mOnClickListener);
    }

    public String getActTitle() {
        TextView textView = findViewById(R.id.title_name);
        String title = textView.getText().toString();
        return title;
    }

    public void hideTitlebar() {
        getpHTitleBar().setVisibility(View.GONE);
    }


    public void showTitlebar() {
        getpHTitleBar().setVisibility(View.VISIBLE);
    }

    public abstract void initTitleBar();

    @Override
    public void onClick(View v) {

    }
}
