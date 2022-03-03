package com.u1.gocashm.util;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.u1.gocashm.PhApplication;

import androidx.annotation.ColorRes;

/**
 * 文本颜色工具类
 *
 * @author LiuFeng
 * @data 2021/2/1 11:55
 */
public class TextColorUtil {
    private static Context context = PhApplication.getContext();

    /**
     * 获取带颜色文本的html
     *
     * @param id
     * @param text
     * @return
     */
    public static String getColorTextHtml(@ColorRes int id, String text) {
        return String.format("<font color = %s>%s</font>", context.getResources().getColor(id), text);
    }

    /**
     * 获取带颜色文本的html
     *
     * @param id
     * @param text
     * @param start
     * @param end
     * @return
     */
    public static String getColorTextHtml(@ColorRes int id, String text, int start, int end) {
        if (start < 0) {
            start = 0;
        }

        if (end > text.length()) {
            end = text.length();
        }

        String prevText = start > 0 ? text.substring(0, start) : "";
        String colorText = text.substring(start, end);
        String nextText = end < text.length() ? text.substring(end) : "";
        return String.format("%s<font color = %s>%s</font>%s", prevText, context.getResources().getColor(id), colorText, nextText);
    }

    /**
     * 获取带颜色文本的Spanned
     *
     * @param id
     * @param text
     * @return
     */
    public static Spanned getColorTextSpanned(@ColorRes int id, String text) {
        String coloredText = getColorTextHtml(id, text);
        coloredText = coloredText.replace("\n","<br>");
        return Html.fromHtml(coloredText);
    }

    /**
     * 获取带颜色文本的Spanned
     *
     * @param id
     * @param text
     * @param start
     * @param end
     * @return
     */
    public static Spanned getColorTextSpanned(@ColorRes int id, String text, int start, int end) {
        String coloredText = getColorTextHtml(id, text, start, end);
        coloredText = coloredText.replace("\n","<br>");
        return Html.fromHtml(coloredText);
    }
}
