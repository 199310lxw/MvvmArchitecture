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
import com.xwl.common_lib.provider.ContextServiceProvider
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author  lxw
 * @date 2023/10/11
 * descripe
 */
object DownloadUtil {
    fun startDownload(
        context: Context,
        url: String,
        fileName: String,
        state: (DownloadState) -> Unit
    ) {
        download(
            context,
            url,
            fileName,
            state = state
        )
    }

    private fun download(
        context: Context,
        url: String,
        fileName: String,
        state: (DownloadState) -> Unit
    ) {
        Observable.create { emitter: ObservableEmitter<ServiceConnection?> ->
            val conn = initService(
                downloadState = state
            )
            emitter.onNext(conn)
        }.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ServiceConnection?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(serviceConnection: ServiceConnection) {
                    val intent = Intent(
                        ContextServiceProvider.service.applicationContext,
                        DownloadService::class.java
                    )
                    intent.putExtra(KeyConstant.KEY_DOWNLOAD_URL, url)
                    intent.putExtra(KeyConstant.KEY_DOWNLOAD_FILE_NAME, fileName)
                    context.startService(intent)
                    context.bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {

                }
            })
    }

    private fun initService(
        downloadState: (DownloadState) -> Unit
    ): ServiceConnection {
        val conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
                val binder = iBinder as ServiceBinder
                val service = binder.service
                service!!.setOnDownloadListener(object : DownloadService.DownloadListener {
                    override fun callback(state: DownloadState) {
                        downloadState.invoke(state)
                    }
                })
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Logger.i("download service is disconnected")
            }
        }
        return conn
    }
}