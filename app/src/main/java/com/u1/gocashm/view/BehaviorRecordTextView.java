package com.u1.gocashm.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.u1.gocashm.model.request.BehaviorRecord;

@SuppressLint("AppCompatCustomView")
public class BehaviorRecordTextView extends TextView {
    private BehaviorRecord mRecord;

    public BehaviorRecordTextView(Context context) {
        this(context, null);
    }

    public BehaviorRecordTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BehaviorRecordTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRecordNo(String recordNo) {
        if (mRecord == null) {
            mRecord = new BehaviorRecord();
        }
        mRecord.setControlNo(recordNo);
    }

    public BehaviorRecord getBehaviorRecord() {
        return mRecord;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mRecord == null) {
            super.setText(text, type);
            return;
        }
        mRecord.setOldValue(getText().toString());
        super.setText(text, type);
        mRecord.setNewValue(getText().toString());
        mRecord.setEndTime(String.valueOf(System.currentTimeMillis()));
    }

}
