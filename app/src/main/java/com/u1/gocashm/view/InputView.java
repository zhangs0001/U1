package com.u1.gocashm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.util.EnglishKeyListener;

/**
 * Created by jishudiannao on 2018/9/18.
 */

public class InputView extends LinearLayout {
    private View layout;
    private Context mContext;
    private LinearLayout ll, ll_title;
    private TextView tv_title,  tv_message, tv_left,mustView;
    private BehaviorRecordTextView tv_content;
    private BehaviorRecordEditText et_content;
    private ImageView iv_pwd_show, iv_input;
    private boolean isVerify = true;
    private int type, maxLength, inputType;
    // 标题、提示、
    private String title, hint, digits, leftStr;
    private boolean english;
    private boolean showTitle,must;
    private static final int TYPE_DEFAULT = 100;
    private boolean isShowPwd;
    private String controlNo;

    public InputView(Context context) {
        super(context);
        init(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputView);
        type = ta.getInteger(R.styleable.InputView_type, 0);
        maxLength = ta.getInteger(R.styleable.InputView_maxLength, 0);
        title = ta.getString(R.styleable.InputView_title);
        hint = ta.getString(R.styleable.InputView_hint);
        digits = ta.getString(R.styleable.InputView_digits);
        showTitle = ta.getBoolean(R.styleable.InputView_showtitle, true);
        must = ta.getBoolean(R.styleable.InputView_must, true);
        english = ta.getBoolean(R.styleable.InputView_english, false);
        inputType = ta.getInteger(R.styleable.InputView_inputType, TYPE_DEFAULT);
        leftStr = ta.getString(R.styleable.InputView_leftStr);
        controlNo = ta.getString(R.styleable.InputView_controlNo);
        ta.recycle();
        init(context);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputView);
        type = ta.getInteger(R.styleable.InputView_type, 0);
        maxLength = ta.getInteger(R.styleable.InputView_maxLength, 0);
        title = ta.getString(R.styleable.InputView_title);
        hint = ta.getString(R.styleable.InputView_hint);
        digits = ta.getString(R.styleable.InputView_digits);
        showTitle = ta.getBoolean(R.styleable.InputView_showtitle, true);
        must = ta.getBoolean(R.styleable.InputView_must, true);
        inputType = ta.getInteger(R.styleable.InputView_inputType, TYPE_DEFAULT);
        leftStr = ta.getString(R.styleable.InputView_leftStr);
        english = ta.getBoolean(R.styleable.InputView_english, false);
        controlNo = ta.getString(R.styleable.InputView_controlNo);
        ta.recycle();
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setOrientation(LinearLayout.VERTICAL);
        layout = inflater.inflate(R.layout.layout_input, null);
        addView(layout);
        mContext = context;
        initView();
    }

    private void initView() {
        setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                getBehaviorRecord().setStartTime(String.valueOf(System.currentTimeMillis()));
            }
            return false;
        });
        ll_title = layout.findViewById(R.id.ll_input_title);
        ll = layout.findViewById(R.id.ll_input);
        tv_title = layout.findViewById(R.id.tv_input_title);
        tv_left = layout.findViewById(R.id.tv_input_left);
        tv_content = layout.findViewById(R.id.tv_input_content);
        et_content = layout.findViewById(R.id.et_input_content);
        tv_message = layout.findViewById(R.id.tv_input_message);
        iv_pwd_show = layout.findViewById(R.id.iv_pwd_show);
        iv_input = layout.findViewById(R.id.iv_input);
        mustView = layout.findViewById(R.id.mustView);

        if (type == 0) {
            et_content.setRecordNo(controlNo);
        } else {
            tv_content.setRecordNo(controlNo);
        }

        boolean ns = false;
        switch (inputType) {
            case 0:
                et_content.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 1:
                et_content.setInputType(InputType.TYPE_CLASS_NUMBER);
                ns = true;
                break;
            case 2:
                et_content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                et_content.setTypeface(Typeface.SANS_SERIF);
                iv_pwd_show.setVisibility(View.VISIBLE);
                iv_pwd_show.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isShowPwd) {
                            iv_pwd_show.setImageResource(R.drawable.ic_pwd_hide);
                            et_content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else {
                            iv_pwd_show.setImageResource(R.drawable.ic_pwd_show);
                            et_content.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                        et_content.setTypeface(Typeface.SANS_SERIF);
                        isShowPwd = !isShowPwd;
                        et_content.setSelection(et_content.getText().length());
                    }
                });
                break;
            case 3:
                ns = true;
            default:
                break;
        }

        mustView.setVisibility(must ? VISIBLE : GONE);

        if (ns) {
            et_content.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
                if (source.equals(" ")) return "";
                else return null;
            }});
        }

        if (showTitle)
            tv_title.setText(title);
        else
            ll_title.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(leftStr)) {
            tv_left.setVisibility(View.VISIBLE);
            tv_left.setText(leftStr);
        }
        // 控件类型 0 输入框； 选择框
        if (type == 0) {
            if (maxLength != 0) {
                et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            }
            if (!TextUtils.isEmpty(digits)) {
                et_content.setKeyListener(DigitsKeyListener.getInstance(digits));
            } else if (english) {
                et_content.setKeyListener(new EnglishKeyListener());
            }


            et_content.setHint(hint);
            et_content.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (isVerify) {
                        if (hasFocus) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ll.setBackground(getResources().getDrawable(R.drawable.bg_tv_input));
                            } else {
                                ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tv_input));
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ll.setBackground(getResources().getDrawable(R.drawable.bg_tv_grey));
                            } else {
                                ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tv_grey));
                            }
                        }
                    }
                }
            });
        } else {
            et_content.setVisibility(View.GONE);
            tv_content.setVisibility(View.VISIBLE);
            iv_input.setVisibility(View.VISIBLE);
            tv_content.setHint(hint);
        }
    }

    public void setText(String value) {
        if (type == 0) {
            et_content.setText(value);
        } else {
            tv_content.setText(value);
        }
    }

    public String getText() {
        if (type == 0)
            return et_content.getText().toString();
        else
            return tv_content.getText().toString();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled && type == 0) {
            et_content.setEnabled(false);
            et_content.setFocusable(false);
        }
    }

    public void setSelEnabled(boolean enabled) {
        iv_input.setVisibility(enabled ? VISIBLE : GONE);
        setEnabled(enabled);
    }

    public void setHint(String hint) {
        if (type == 0) {
            et_content.setHint(hint);
        }else{
            tv_content.setHint(hint);
        }
    }

    public EditText getEt_content() {
        return et_content;
    }

    public TextView getTv_content() {
        return tv_content;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public void setVerify(int resId) {
        setVerify(getResources().getString(resId));
    }

    public void setVerify(String msg) {
        if (TextUtils.isEmpty(msg)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ll.setBackground(getResources().getDrawable(R.drawable.bg_tv_grey));
            } else {
                ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tv_grey));
            }
            tv_message.setText("");
            tv_message.setVisibility(View.GONE);
            setContextForeground(1);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ll.setBackground(getResources().getDrawable(R.drawable.bg_tv_orange));
            } else {
                ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tv_orange));
            }
            tv_message.setText(msg);
            tv_message.setVisibility(View.VISIBLE);
            setContextForeground(0);
        }
    }

    private void setContextForeground(int colorType) {
        switch (colorType) {
            case 0:
                setContextFgRed();
                break;
            case 1:
                setContextFgBlack();
                break;
            default:
                setContextFgBlack();
                break;
        }
    }

    private void setContextFgRed() {
        if (type == 0) {
            et_content.setTextColor(getResources().getColor(R.color.red));
        } else {
            tv_content.setTextColor(getResources().getColor(R.color.red));
        }
    }

    private void setContextFgBlack() {
        if (type == 0) {
            et_content.setTextColor(getResources().getColor(R.color.black_1));
        } else {
            tv_content.setTextColor(getResources().getColor(R.color.black_1));
        }
    }

    public BehaviorRecord getBehaviorRecord(){
        if (type == 0) {
            return et_content.getBehaviorRecord();
        }
        return tv_content.getBehaviorRecord();
    }

    public void inputClearFocus() {
        et_content.clearFocus();
        et_content.setFocusable(false);
    }

    public void getFocus() {
        et_content.setFocusable(true);
        et_content.setFocusableInTouchMode(true);
        et_content.requestFocus();
    }

    public LinearLayout getLinearLayout() {
        return ll;
    }
}
