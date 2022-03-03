package com.u1.gocashm.view.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by jishudiannao on 2018/9/5.
 */
public abstract class BasePopupWindow extends PopupWindow {

    private int popupWidth,popupHeight;

    public BasePopupWindow() {
        initParams();
    }

    public BasePopupWindow(View contentView) {
        super(contentView);
        initParams();
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        initParams();
    }

    public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        initParams();
    }

    public BasePopupWindow(Context context) {
        super(context);
        initParams();
    }

    public BasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams();
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams();
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams();
    }

    public BasePopupWindow(int width, int height) {
        super(width, height);
        initParams();
    }

    private void initParams(){
        setOutsideTouchable(true);
//        ColorDrawable dw = new ColorDrawable(0x65000000);
//        setBackgroundDrawable(dw);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        if(null != getContentView() && isOutsideTouchable()){
            getContentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = getContentView().getMeasuredWidth();
        popupHeight = getContentView().getMeasuredHeight();
    }

    protected abstract void initData();

    protected abstract void findViews();

    protected abstract void initViews();

    protected <T extends View> T findViewById(int resId){
        return (T)getContentView().findViewById(resId);
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if(touchable && null != getContentView()){
            getContentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    //显示在控件上方
    public void showAsDropUp(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }

    public void showPopupWindow(View anchorView) {
        if (getPopupDirection(anchorView)) {
            showAsDropUp(anchorView);
        } else {
            showAsDropDown(anchorView);
        }
    }

    public boolean getPopupDirection(View anchorView) {
        final int anchorLoc[] = new int[2];

        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        int anchorHeight = anchorView.getHeight();

        // 获取屏幕的高宽
        DisplayMetrics dm2 = anchorView.getResources().getDisplayMetrics();
        int screenHeight = dm2.heightPixels;

        // 判断需要向上弹出还是向下弹出显示
        return (screenHeight - anchorLoc[1] - anchorHeight < popupHeight);
    }

    private int[] calculatePopWindowPos(View anchorView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];

        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();

        // 获取屏幕的高宽
        DisplayMetrics dm2 = anchorView.getResources().getDisplayMetrics();
        final int screenHeight = dm2.heightPixels;
        final int screenWidth = dm2.widthPixels;

        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < popupHeight);

        if (isNeedShowUp) {
            windowPos[0] = screenWidth - popupWidth;
            windowPos[1] = anchorLoc[1] - popupHeight;
        } else {
            windowPos[0] = screenWidth - popupWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }

        return windowPos;
    }


}
