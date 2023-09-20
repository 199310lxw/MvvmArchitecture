package com.xwl.common_base.dialog;

import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.lib_net.download.DownloadService;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.orhanobut.logger.Logger;
import com.xwl.common_base.R;
import com.xwl.common_lib.constants.KeyConstant;
import com.xwl.common_lib.dialog.TipsToast;
import com.xwl.common_lib.provider.ContextServiceProvider;
import com.xwl.common_lib.utils.AppUtils;

import java.io.File;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private Intent intent;

    public UpdateDialog(@NonNull Context context, String path) {
        super(context);
        this.mContext = context;
        this.mDownloadUrl = path;
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
            checkPermission();
        });
        tvIgnore.setOnClickListener(v -> dismiss());
    }

    private void checkPermission() {
        XXPermissions.with(mContext)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.READ_MEDIA_IMAGES)
                .permission(Permission.READ_MEDIA_VIDEO)
                .permission(Permission.READ_MEDIA_AUDIO)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        checkFile(KeyConstant.INSTANCE.getAPP_UPDATE_PATH());
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            TipsToast.INSTANCE.showTips("请到设置界面手动授予读写内存权限");
                            dismiss();
                        }
                    }
                });
    }

    private void checkFile(String path) {
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
                    Logger.e(path);
                    File file = new File(path);
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                    emitter.onNext(file);
                    emitter.onComplete();
                }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    // 默认最先调用复写的 onSubscribe（）
                    @Override
                    public void onNext(File file) {
                        if (file.exists()) {
                            Logger.e("文件夹创建成功");
                        } else {
                            Logger.e("文件夹不存在或为空");
                        }
                        initService();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Logger.e("开始启动更新服务");
                        Logger.e("mDownloadUrl-->" + mDownloadUrl);
                        intent = new Intent(ContextServiceProvider.service.getApplicationContext(), DownloadService.class);
                        intent.putExtra(KeyConstant.KEY_DOWNLOAD_URL, mDownloadUrl);
                        mContext.startService(intent);
                        mContext.bindService(intent, conn, Service.BIND_AUTO_CREATE);
                    }
                });
    }

    private void initService() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                DownloadService.ServiceBinder binder = (DownloadService.ServiceBinder) iBinder;
                if (binder != null) {
                    DownloadService service = binder.getService();
                    btnDownload.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    tvProgress.setVisibility(View.VISIBLE);
                    service.setOnDownloadListener(new DownloadService.DownloadListener() {
                        @Override
                        public void onSuccess(String path) {
                            AppUtils.installApk(mContext, path);
                            dismiss();
                        }

                        @Override
                        public void onDownload(int progress) {
                            progressBar.setProgress(progress);
                            tvProgress.setText(String.format("%d%%", progress));
                        }

                        @Override
                        public void onError(@NonNull String msg) {
                            TipsToast.INSTANCE.showTips(msg);
                        }
                    });
                } else {
                    TipsToast.INSTANCE.showTips("服务错误");
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Logger.e("service is disconnected");
                TipsToast.INSTANCE.showTips("服务已关闭");
            }
        };
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
