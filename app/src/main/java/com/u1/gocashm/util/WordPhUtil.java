package com.u1.gocashm.util;

import android.text.TextUtils;

/**
 * Created by jishudiannao on 2018/9/21.
 */

public class WordPhUtil {

    public static String capitalize(String str) {
        return capitalize(str, (char[]) null);
    }

    public static String capitalize(String str, char... delimiters) {
        int delimLen = delimiters == null ? -1 : delimiters.length;
        if (!TextUtils.isEmpty(str) && delimLen != 0) {
            char[] buffer = str.toCharArray();
            boolean capitalizeNext = true;

            for (int i = 0; i < buffer.length; ++i) {
                char ch = buffer[i];
                if (isDelimiter(ch, delimiters)) {
                    capitalizeNext = true;
                } else if (capitalizeNext) {
                    buffer[i] = Character.toTitleCase(ch);
                    capitalizeNext = false;
                }
            }

            return new String(buffer);
        } else {
            return str;
        }
    }

    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        } else {
            char[] arr$ = delimiters;
            int len$ = delimiters.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                char delimiter = arr$[i$];
                if (ch == delimiter) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String getAccountKitPhone(String phone) {
        return phone.substring(3);
    }
}
