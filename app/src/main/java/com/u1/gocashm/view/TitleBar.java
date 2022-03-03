package com.u1.gocashm.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.util.LiveDataBus;


/**
 * @author Jason.Lu QQ:122479237
 * @name CashHome
 * @class name：com.example.jasonlu.cashhome.ui.component
 * @class describe
 * @time 2018/8/25 11:09
 * @change
 * @chang time
 * @class describe
 */
public class TitleBar extends FrameLayout {

    /**
     * 当前类型
     */
    private int mStyle = 0;
    /**
     * 显示标题文字内容
     */
    private String mDisplayName = "";
    /**
     * 左边按钮图标
     */
    private int mLeftIcon = 0;
    /**
     * 右边按钮图标
     */
    private int mRghtIcon = 0;
    /**
     * 当前绑定AC
     */
    private Activity mBindActivity = null;
    /**
     * 点击事件监听
     */
    private OnClickListener mOnClickListener = null;
    /***
     * 默认点击事件
     */
    private OnClickListener mDefOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.title_bar_left) {
                int style = (Integer) v.getTag();
                if ((style == TitleBar.Style.BACK) && mBindActivity != null) {
                    mBindActivity.finish();

                }
            }
        }
    };

    public TitleBar(Context context) {
        super(context);
        buildTitleBar();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        this.mStyle = typedArray.getInt(R.styleable.TitleBar_display_style, TitleBar.Style.MAIN);
        this.mDisplayName = typedArray.getString(R.styleable.TitleBar_displayname);
        this.mLeftIcon = typedArray.getResourceId(R.styleable.TitleBar_left_btn, 0);
        this.mRghtIcon = typedArray.getResourceId(R.styleable.TitleBar_right_btn, 0);
        typedArray.recycle();
        buildTitleBar();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        buildTitleBar();
    }

    private void buildTitleBar() {
        addView(LinearLayout.inflate(getContext(), R.layout.component_titlebar, null));
        View leftBtn = findViewById(R.id.title_bar_left);
        View rightBtn = findViewById(R.id.title_bar_right);
        TextView nameView = (TextView) findViewById(R.id.title_name);
        ImageView leftImage = (ImageView) findViewById(R.id.title_left_image);
        nameView.setText(mDisplayName);
        if (mStyle == TitleBar.Style.MAIN) {
            leftBtn.setVisibility(View.GONE);
            rightBtn.setVisibility(View.GONE);
        } else if (mStyle == TitleBar.Style.BACK) {
            leftImage.setImageResource(R.drawable.ic_back);
            leftBtn.setVisibility(View.VISIBLE);
            rightBtn.setVisibility(View.INVISIBLE);
        }
        leftBtn.setTag(mStyle);
        leftBtn.setOnClickListener(mDefOnClickListener);
        changeBackground(R.color.blue);
    }

    /**
     * 重置标题
     *
     * @param displayName 标题名称
     * @param leftStr     左文字
     * @param leftRes     左图标
     * @param rightStr    右文字
     * @param rightRes    右图标
     * @param style       样式
     */
    public void rebuildTitleBar(String displayName, int centerRes, String leftStr, int leftRes,
                                String rightStr, int rightRes, int style) {
        this.mStyle = style;
        this.mDisplayName = displayName;
        View leftBtn = findViewById(R.id.title_bar_left);
        View centerBtn = findViewById(R.id.title_bar_center);
        View rightBtn = findViewById(R.id.title_bar_right);
        TextView nameView = (TextView) findViewById(R.id.title_name);
        nameView.setText(mDisplayName);
        if (mStyle == TitleBar.Style.MAIN_ALL) {
            ImageButton leftImgBtn = (ImageButton) findViewById(R.id.title_left_image);
            ImageButton centerImgBtn = (ImageButton) findViewById(R.id.title_center_image);
            ImageButton rightImgBtn = (ImageButton) findViewById(R.id.title_right_image);
            if (leftRes == -1) {
                leftImgBtn.setVisibility(View.GONE);
            } else {
                leftImgBtn.setVisibility(View.VISIBLE);
                leftImgBtn.setImageResource(leftRes);
            }
            if (centerRes == -1) {
                centerImgBtn.setVisibility(View.GONE);
            } else {
                centerImgBtn.setVisibility(View.VISIBLE);
                centerImgBtn.setImageResource(centerRes);
            }
            if (rightRes == -1) {
                rightImgBtn.setVisibility(View.GONE);
            } else {
                rightImgBtn.setVisibility(View.VISIBLE);
                rightImgBtn.setImageResource(rightRes);
            }

            TextView left = (TextView) findViewById(R.id.title_left_txt);
            if ("".equals(leftStr) || null == leftStr) {
                left.setVisibility(View.GONE);
            } else {
                left.setVisibility(View.VISIBLE);
                left.setText(leftStr);
                left.setTextColor(0xFF929598);
            }
            TextView right = (TextView) findViewById(R.id.title_right_txt);
            if ("".equals(rightStr) || null == rightStr) {
                right.setVisibility(View.GONE);
            } else {
                right.setVisibility(View.VISIBLE);
                right.setText(rightStr);
            }
            leftBtn.setVisibility(View.VISIBLE);
        }
        leftBtn.setTag(mStyle);
        leftBtn.setOnClickListener(mDefOnClickListener);
        if (mOnClickListener != null) {
            setOnClickListener(mOnClickListener);
        }
    }

    public void setLeftBack(OnClickListener mOnClickListener) {
        View leftBtn = findViewById(R.id.title_bar_left);
        ImageButton leftImgBtn = findViewById(R.id.title_left_image);
        leftImgBtn.setImageResource(R.drawable.ic_back);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setOnClickListener(mOnClickListener);
    }

    /**
     * 设置点击事件
     */
    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
        resetOnClickListener();
    }

    /**
     * 重置事件
     */
    private void resetOnClickListener() {
        View leftBtn = findViewById(R.id.title_bar_left);
        ImageButton centerImgBtn = (ImageButton) findViewById(R.id.title_center_image);
        View rightBtn = findViewById(R.id.title_bar_right);
        rightBtn.setOnClickListener(mOnClickListener);
        leftBtn.setOnClickListener(mOnClickListener);
        centerImgBtn.setOnClickListener(mOnClickListener);
    }

    /**
     * 绑定
     *
     * @param activity
     */
    public void bindActivity(Activity activity) {
        this.mBindActivity = activity;
    }

    /**
     * 修改titleBar的背景色
     *
     * @param res
     */
    public void changeBackground(int res) {
        View titleBar = findViewById(R.id.title_bar_ly);
        titleBar.setBackgroundResource(res);
    }

    /**
     * @param color
     */
    public void setTitleForeground(int color) {
        TextView title = (TextView) findViewById(R.id.title_name);
        title.setTextColor(color);
    }

    public void setTitleStyle() {
        TextView title = (TextView) findViewById(R.id.title_name);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
    }

    /**
     * 获取标题文案
     *
     * @return 标题名称
     */
    public String getTitle() {
        TextView nameView = (TextView) findViewById(R.id.title_name);
        return nameView.getText().toString();
    }

//    public void setTextSize(int size) {
//        TextView name = findViewById(R.id.title_name);
//        name.setTextSize(size);
//    }

    public void setRightText(String str, OnClickListener onClickListener) {
        View rightBtn = findViewById(R.id.title_bar_right);
        TextView tv = findViewById(R.id.title_right_txt);
        tv.setText(str);
        rightBtn.setOnClickListener(onClickListener);
        rightBtn.setVisibility(View.VISIBLE);
    }

    public void showRight() {
        View rightBtn = findViewById(R.id.title_bar_right);
        rightBtn.setVisibility(View.VISIBLE);
    }

    public void hideRight() {
        View rightBtn = findViewById(R.id.title_bar_right);
        rightBtn.setVisibility(View.GONE);
    }

    /**
     * 修改标题文案
     *
     * @param displayName 标题名称
     */
    public void changeTitle(String displayName) {
        TextView nameView = (TextView) findViewById(R.id.title_name);
        nameView.setText(displayName);
    }

    /**
     * 修改左侧文本
     *
     * @param displayName 标题名称
     */
    public void changeLeftText(String displayName) {
        TextView leftText = (TextView) findViewById(R.id.title_left_txt);
        leftText.setText(displayName);
    }

    /**
     * 修改右侧文本
     *
     * @param displayName 标题名称
     */
    public void changeRightText(String displayName) {
        TextView rightText = (TextView) findViewById(R.id.title_right_txt);
        rightText.setText(displayName);
    }

    public View getView(int id) {
        return findViewById(id);
    }

    /**
     * 标题样式
     *
     * @author Fang
     * @version V1.0
     * @date 2016年6月8日 上午11:53:52
     */
    public static class Style {
        /**
         * 没有左右按钮，只有标题
         **/
        public static int MAIN = 0;
        /**
         * 默认带返回按钮(文字)+标题
         **/
        public static int BACK = 1;
        /**
         * 默认带文字或者按钮的
         **/
        public static int MAIN_ALL = 3;
    }

}
