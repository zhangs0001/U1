package com.u1.gocashm.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import com.u1.gocashm.model.request.BehaviorRecord;

public class BehaviorRecordEditText extends EditText {
    private BehaviorRecord mRecord;

    public BehaviorRecordEditText(Context context) {
        super(context);
    }

    public BehaviorRecordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BehaviorRecordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (mRecord == null) return;
        if (focused) {
            mRecord.setOldValue(getText().toString());
            mRecord.setStartTime(String.valueOf(System.currentTimeMillis()));
        } else {
            mRecord.setNewValue(getText().toString());
            mRecord.setEndTime(System.currentTimeMillis());
        }
    }
}
