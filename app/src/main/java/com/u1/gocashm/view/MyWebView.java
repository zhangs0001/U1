package com.u1.gocashm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author hewei
 * @date 2019/9/24 0024 下午 4:33
 */
public class MyWebView extends WebView {

    public interface ScrollInterface {
        void onSChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    ScrollInterface mScrollInterface;

    public void setOnCustomScrollChangeListener(ScrollInterface mInterface) {
        mScrollInterface = mInterface;
    }

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollInterface != null){
            mScrollInterface.onSChanged(l, t, oldl, oldt);
        }
    }
}
