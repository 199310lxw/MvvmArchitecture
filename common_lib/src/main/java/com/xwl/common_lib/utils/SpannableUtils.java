package com.xwl.common_lib.utils;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

/**
 * author: Administrator
 * time: 2022/8/17
 * desc:
 */
public class SpannableUtils {
    /**
     * 单独设置字体大小
     */
    public static SpannableStringBuilder setSpannableTextSize(String text, int start, int end, int textSize) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize));
        sBuilder.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 单独设置粗体
     */
    public static SpannableStringBuilder setSpannableTextBold(String text, int start, int end) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, SPAN_INCLUSIVE_INCLUSIVE); //粗体
        return sBuilder;
    }

    /**
     * 字体 + 粗体
     */
    public static SpannableStringBuilder setSpannableTextSizeBold(String text, int start, int end, int textSize) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize));
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, SPAN_INCLUSIVE_INCLUSIVE); //粗体
        sBuilder.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 单独设置字体 粗斜体
     */
    public static SpannableStringBuilder setSpannableTextSizeBoldItalic(String text, int start, int end, int textSize) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize));
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, SPAN_INCLUSIVE_INCLUSIVE); //粗体
        sBuilder.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色 + 粗体
     */
    public static SpannableStringBuilder setSpannableTextBoldColor(String text, int start, int end, String textColor) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, SPAN_INCLUSIVE_INCLUSIVE); //粗体
        ForegroundColorSpan startSpan = new ForegroundColorSpan(Color.parseColor(textColor));
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色 + 粗体
     */
    public static SpannableStringBuilder setSpannableTextBoldColor(String text, int start, int end, int textColor) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, SPAN_INCLUSIVE_INCLUSIVE); //粗体
        ForegroundColorSpan startSpan = new ForegroundColorSpan(textColor);
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色1
     */
    public static SpannableStringBuilder setSpannableTextColor(String text, int start, int end, int textColor) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        ForegroundColorSpan startSpan = new ForegroundColorSpan(textColor);
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色2
     */
    public static SpannableStringBuilder setSpannableTextColor(String text, int start, int end, String textColor) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        ForegroundColorSpan startSpan = new ForegroundColorSpan(Color.parseColor(textColor));
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色 + 字体大小
     */
    public static SpannableStringBuilder setSpannableTextSizeColor(String text, int start, int end, int textSize, String textColor) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize));
        ForegroundColorSpan startSpan = new ForegroundColorSpan(Color.parseColor(textColor));
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        sBuilder.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色2 + 字体大小2
     */
    public static SpannableStringBuilder setSpannableTextSizeTwoColor(String text, int start, int end, int textSize, int textColor,
                                                                      int start2, int end2, int textSize2, int textColor2) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize));
        ForegroundColorSpan startSpan = new ForegroundColorSpan(textColor);
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        sBuilder.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan sizeSpan2 = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize2));
        ForegroundColorSpan startSpan2 = new ForegroundColorSpan(textColor2);
        sBuilder.setSpan(startSpan2, start2, end2, SPAN_INCLUSIVE_INCLUSIVE);
        sBuilder.setSpan(sizeSpan2, start2, end2, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 两个颜色
     */
    public static SpannableStringBuilder setSpannableTwoTextColor(String text, int start, int end, String textColor1,
                                                                  int s2, int e2, String color2) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        ForegroundColorSpan startSpan = new ForegroundColorSpan(Color.parseColor(textColor1));
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        ForegroundColorSpan startSpan2 = new ForegroundColorSpan(Color.parseColor(color2));
        sBuilder.setSpan(startSpan2, s2, e2, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色 + 字体大小 + 粗体
     */
    public static SpannableStringBuilder setSpannableTextSizeBoldColor(String text, int start, int end, int textSize, String textColor) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize));
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, SPAN_INCLUSIVE_INCLUSIVE); //粗体
        ForegroundColorSpan startSpan = new ForegroundColorSpan(Color.parseColor(textColor));
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        sBuilder.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 颜色 + 字体大小 + 粗体
     */
    public static SpannableStringBuilder setSpannableTextSizeBoldColor(String text, int start, int end, int textSize, int textColor) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtil.dip2px(textSize));
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, SPAN_INCLUSIVE_INCLUSIVE); //粗体
        ForegroundColorSpan startSpan = new ForegroundColorSpan(textColor);
        sBuilder.setSpan(startSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        sBuilder.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

    /**
     * 设置下划线
     */
    public static SpannableStringBuilder setUnderLineSpan(String text, int start, int end) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        sBuilder.setSpan(underlineSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return sBuilder;
    }

    /**
     * 设置删除线
     */
    public static SpannableStringBuilder setDeleteLineSpan(String text, int start, int end) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        sBuilder.setSpan(strikethroughSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return sBuilder;
    }

    /**
     * 设置删除线和下划线
     */
    public static SpannableStringBuilder setTextStrike(String text, int start, int end) {
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        //用删除线标记文本
        sBuilder.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return sBuilder;
    }

}
