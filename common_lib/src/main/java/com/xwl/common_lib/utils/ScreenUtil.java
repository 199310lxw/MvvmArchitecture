package com.xwl.common_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.xwl.common_lib.provider.ContextServiceProvider;

/**
 * author: Administrator
 * time: 2022/8/9
 * desc:屏幕相关工具类
 */

public class ScreenUtil {
    @Nullable
    public static int dip2px(float dipValue) {
        final float scale = ContextServiceProvider.INSTANCE.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int px2dip(float pxValue) {
        final float scale = ContextServiceProvider.INSTANCE.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 将px值转换为sp值
    public static int px2sp(float pxValue) {
        final float fontScale = ContextServiceProvider.INSTANCE.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    // 将sp值转换为px值
    public static int sp2px(float spValue) {
        final float fontScale = ContextServiceProvider.INSTANCE.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 获取屏幕的宽度
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) ContextServiceProvider.INSTANCE.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 0;
        }
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    // 获取屏幕的高度
    public static int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) ContextServiceProvider.INSTANCE.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 0;
        }
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 屏幕高度 手机分辨率的高度包括状态栏和底部的虚拟键盘
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight2() {
        WindowManager wm = (WindowManager) ContextServiceProvider.INSTANCE.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 0;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    /**
     * 屏幕宽度 屏幕实际的高度
     *
     * @return the width of screen, in pixel
     */
    public static int getScreenWidth2() {
        WindowManager wm = (WindowManager) ContextServiceProvider.INSTANCE.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 0;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    /**
     * 返回屏幕可用高度
     * 当显示了虚拟按键时，会自动减去虚拟按键高度
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 获取厂商名
     **/
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取产品名
     **/
    public static String getDeviceProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取手机品牌
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机主板名
     */
    public static String getDeviceBoard() {
        return Build.BOARD;
    }

    /**
     * 设备名
     **/
    public static String getDeviceDevice() {
        return Build.DEVICE;
    }

    /**
     * fingerprit 信息
     **/
    public static String getDeviceFubgerprint() {
        return Build.FINGERPRINT;
    }

    public static String getDeviceString(){
        return "获取厂商名 = " + getDeviceManufacturer() + "\n" +
                "获取产品名 = " + getDeviceProduct() + "\n" +
                "获取手机品牌 = " + getDeviceBrand() + "\n" +
                "获取手机型号 = " + getDeviceModel() + "\n" +
                "获取手机主板名 = " + getDeviceBoard() + "\n" +
                "设备名 = " + getDeviceDevice() + "\n" +
                "fingerprit = " + getDeviceFubgerprint() + "\n";
    }


}
