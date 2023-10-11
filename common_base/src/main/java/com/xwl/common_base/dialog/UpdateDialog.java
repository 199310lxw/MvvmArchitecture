package com.xwl.common_base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lib_net.download.DownloadUtil;
import com.xwl.common_base.R;
import com.xwl.common_lib.dialog.TipsToast;
import com.xwl.common_lib.utils.AppUtils;

/**
 * @author lxw
 * @date 2023/2/1
 * descripe
 */
public class UpdateDialog extends Dialog {
    private TextView tvIgnore;
    private TextView tvVersion;
    private Button btnDownload;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private Context mContext;
    private ServiceConnection conn;
    private String mVersionName = "";
    private String mDownloadUrl;
    private String mFileName;
    private Intent intent;

    public UpdateDialog(@NonNull Context context, String path, String fileName) {
        super(context);
        this.mContext = context;
        this.mDownloadUrl = path;
        this.mFileName = fileName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tvIgnore = findViewById(R.id.tv_ignore);
        btnDownload = findViewById(R.id.yes);
        tvVersion = findViewById(R.id.tv_version);
        progressBar = findViewById(R.id.progress_bar);
        tvProgress = findViewById(R.id.tv_progress);
        tvVersion.setText(mVersionName);
        btnDownload.setOnClickListener(v -> {
            btnDownload.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            tvProgress.setVisibility(View.VISIBLE);
            download();
        });
        tvIgnore.setOnClickListener(v -> dismiss());
    }

    private void download() {
        DownloadUtil.INSTANCE.startDownload(mContext, mDownloadUrl, mFileName, progress -> {
            progressBar.setProgress(progress);
            tvProgress.setText(String.format("%d%%", progress));
            return null;
        }, path -> {
            dismiss();
            AppUtils.installApk(mContext, path);
            return null;
        }, msg -> {
            TipsToast.INSTANCE.showTips(msg);
            return null;
        });
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (intent != null) {
            mContext.stopService(intent);
            mContext.unbindService(conn);
            intent = null;
            conn = null;
        }

    }
}
