package com.xwl.common_lib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @author lxw
 * @date 2023/2/3
 * descripe
 */
public class AppUtils {
    /**
     * 安装apk
     */
    public static void installApk(Context context, String path) {
        Intent intentUpdate = new Intent("android.intent.action.VIEW");
        intentUpdate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //对Android N及以上的版本做判断
            Uri apkUriN = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", file);
            intentUpdate.addCategory("android.intent.category.DEFAULT");
            intentUpdate.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加Flag 表示我们需要什么权限
            intentUpdate.setDataAndType(apkUriN, "application/vnd.android.package-archive");
        } else {
            Uri apkUri = Uri.fromFile(file);
            intentUpdate.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }
        context.startActivity(intentUpdate);
    }
}
