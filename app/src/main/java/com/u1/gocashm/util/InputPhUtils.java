package com.u1.gocashm.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author hewei
 * @date 2018/9/20 0020 下午 4:22
 */
public class InputPhUtils {

    public static void setBlankToEditText(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int i2) {
                if (s == null || s.length() == 0) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 4 && i != 9 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 5 || sb.length() == 10) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }

                    editText.setText(sb.toString());
                    editText.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
