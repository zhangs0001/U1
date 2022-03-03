package com.u1.gocashm.util;

import android.view.View;
import android.widget.EditText;

import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.RecordPhModel;

/**
 * Created by jishudiannao on 2018/9/17.
 */

public class AFPhListener {

    private static AFPhListener instance;
    private String value_old;
    private String value_new;
    private long time_old;
    private long time_new;
    private RecordPhModel recordPhModel;

    public static AFPhListener getInstance() {
        if (instance == null) {
            instance = new AFPhListener();
        }
        return instance;
    }

    /**
     * 处理输入框
     * @param editText
     * @param bfModel
     * @param id
     */
    public void addEditTextListener(final EditText editText, final BehaviorPhReqModel bfModel, final String id) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    value_old = editText.getText().toString();
                    // 输入框获取焦点
                    recordPhModel = BehaviorPhUtils.setStartModify(id,value_old);
                } else {
                    value_new = editText.getText().toString();
                    //输入框失去焦点
                    BehaviorPhUtils.setEndModify(recordPhModel,bfModel,id,value_new);
                }
            }
        });
    }



}
