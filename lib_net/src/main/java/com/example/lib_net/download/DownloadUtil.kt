package com.example.lib_net.download

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.lib_net.download.DownloadService.ServiceBinder
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.orhanobut.logger.Logger
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.constants.KeyConstant.APP_ROOT_PATH
import com.xwl.common_lib.constants.KeyConstant.APP_UPDATE_PATH
import com.xwl.common_lib.dialog.TipsToast.showTips
import com.xwl.common_lib.provider.ContextServiceProvider
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException

/**
 * @author  lxw
 * @date 2023/10/11
 * descripe
 */
object DownloadUtil {

    private var conn: ServiceConnection? = null
    fun startDownload(
        context: Context,
        url: String,
        fileName: String,
        progress: (Int) -> Unit,
        success: (String) -> Unit,
        failure: (String) -> Unit
    ) {
        checkPermission(
            context,
            url,
            fileName,
            progress = progress,
            success = success,
            failure = failure
        )
    }

    private fun checkPermission(
        context: Context,
        url: String,
        fileName: String,
        progress: (Int) -> Unit,
        success: (String) -> Unit,
        failure: (String) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .permission(Permission.READ_MEDIA_AUDIO)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    download(
                        context,
                        url,
                        fileName,
                        progress = progress,
                        success = success,
                        failure = failure
                    )
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        showTips("请到设置界面手动授予读写内存权限")
                    }
                }
            })
    }

    private fun download(
        context: Context,
        url: String,
        fileName: String,
        progress: (Int) -> Unit,
        success: (String) -> Unit,
        failure: (String) -> Unit
    ) {
        Observable.create { emitter: ObservableEmitter<String?> ->
            initService(
                progress = progress,
                success = success,
                failure = failure
            )
            var savePath = ""
            var file: File? = null
            if (url.endsWith(".apk")) {
                savePath = APP_UPDATE_PATH
                file = File(savePath)
            } else if (url.endsWith(".mp4")) {
                savePath = "$APP_ROOT_PATH/video"
                file = File(savePath)
            } else if (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".jpeg")) {
                savePath = "$APP_ROOT_PATH/images"
            } else {
                showTips("当前文件格式不支持下载")
                conn = null
                return@create
            }
            file?.let {
                try {
                    if (!it.exists()) {
                        it.mkdirs()
                    }
                } catch (e: IOException) {
                    Logger.e(e.message)
                }
            }
            if (file != null) {
                emitter.onNext(savePath)
            }
        }.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String?> {
                override fun onSubscribe(d: Disposable) {}

                // 默认最先调用复写的 onSubscribe（）
                override fun onNext(path: String) {
                    Logger.e("开始启动更新服务")
                    Logger.e("mDownloadUrl-->$url")

                    if (conn == null) return
                    val intent = Intent(
                        ContextServiceProvider.service.applicationContext,
                        DownloadService::class.java
                    )
                    intent.putExtra(KeyConstant.KEY_DOWNLOAD_URL, url)
                    intent.putExtra(KeyConstant.KEY_DOWNLOAD_FILE, path)
                    intent.putExtra(KeyConstant.KEY_DOWNLOAD_FILE_NAME, fileName)
                    context.startService(intent)
                    context.bindService(intent, conn!!, Service.BIND_AUTO_CREATE)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {

                }
            })
    }

    private fun initService(
        progress: (Int) -> Unit,
        success: (String) -> Unit,
        failure: (String) -> Unit
    ) {
        conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
                val binder = iBinder as ServiceBinder
                if (binder != null) {
                    val service = binder.service
                    service!!.setOnDownloadListener(object : DownloadService.DownloadListener {
                        override fun onSuccess(path: String) {
                            showTips("下载完成")
                            success.invoke(path)
                        }

                        override fun onDownload(pro: Int) {
                            progress.invoke(pro)
//                            mViewBinding.progressBar.progress = progress
//                            mViewBinding.tvProgress.text = String.format("%d%%", progress)
                        }

                        override fun onError(msg: String) {
                            showTips(msg)
                            failure?.invoke(msg)
                        }
                    })
                } else {
                    showTips("服务错误")
                }
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Logger.e("service is disconnected")
                showTips("服务已关闭")
            }
        }
    }
}