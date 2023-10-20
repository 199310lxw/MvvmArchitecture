package com.example.lib_net.download

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.lib_net.download.DownloadService.ServiceBinder
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
        start: (Boolean) -> Unit,
        progress: (Int) -> Unit,
        success: (String) -> Unit,
        failure: (String) -> Unit
    ) {
        download(
            context,
            url,
            fileName,
            start = start,
            progress = progress,
            success = success,
            failure = failure
        )
    }

    private fun download(
        context: Context,
        url: String,
        fileName: String,
        start: (Boolean) -> Unit,
        progress: (Int) -> Unit,
        success: (String) -> Unit,
        failure: (String) -> Unit
    ) {
        Observable.create { emitter: ObservableEmitter<String?> ->
            initService(
                progress = progress,
                start = start,
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
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            Logger.e("文件下载地址：${url}")
            Logger.e("文件保存地址：${savePath}")
            if (file != null) {
                emitter.onNext(savePath)
            }
        }.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String?> {
                override fun onSubscribe(d: Disposable) {}

                // 默认最先调用复写的 onSubscribe（）
                override fun onNext(path: String) {
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
        start: (Boolean) -> Unit,
        progress: (Int) -> Unit,
        success: (String) -> Unit,
        failure: (String) -> Unit
    ) {
        conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
                val binder = iBinder as ServiceBinder
                val service = binder.service
                service!!.setOnDownloadListener(object : DownloadService.DownloadListener {
                    override fun onStart(boolean: Boolean) {
                        start.invoke(boolean)
                    }

                    override fun onSuccess(path: String) {
                        showTips("下载完成")
                        success.invoke(path)
                    }

                    override fun onDownload(pro: Int) {
                        progress.invoke(pro)
                    }

                    override fun onError(msg: String) {
                        failure.invoke(msg)
                    }
                })
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Logger.e("download service is disconnected")
            }
        }
    }
}