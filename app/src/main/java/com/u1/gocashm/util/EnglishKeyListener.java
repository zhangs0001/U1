package com.u1.gocashm.util;

import android.text.InputType;
import android.text.method.DigitsKeyListener;

/**
 * 输入英文数字
 */
public class EnglishKeyListener extends DigitsKeyListener {

    public static final String AVAILABLE="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    @Override
    public int getInputType() {
        return InputType.TYPE_TEXT_VARIATION_PASSWORD;
    }

    @Override
    protected char[] getAcceptedChars() {
        return AVAILABLE.toCharArray();
    }
}
