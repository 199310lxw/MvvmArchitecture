package com.example.lib_net.download

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import com.xwl.common_lib.constants.KeyConstant
import kotlinx.coroutines.launch
import java.io.File
import java.lang.ref.WeakReference

/**
 * @author  lxw
 * @date 2023/9/19
 * descripe
 */
class DownloadService : LifecycleService() {
    private var mDownloadUrl = ""

    private var mBinder: ServiceBinder? = null
    private var mDownloadListener: DownloadListener? = null
    private var mFilePath: String? = null
    private lateinit var mDownloadFile: File

    init {
        mBinder = ServiceBinder(this)
    }

    fun setOnDownloadListener(downloadListener: DownloadListener) {
        this.mDownloadListener = downloadListener
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            mDownloadUrl = intent.getStringExtra(KeyConstant.KEY_DOWNLOAD_URL).toString()
            mFilePath = intent.getStringExtra(KeyConstant.KEY_DOWNLOAD_FILE).toString()
            if (mFilePath != null) {
                mDownloadFile = File(mFilePath)
                if (!mDownloadFile.exists()) {
                    mDownloadFile.createNewFile()
                }
                startDownload(mDownloadFile)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startDownload(file: File) {
        lifecycleScope.launch {
            DownloadManager.download(mDownloadUrl, file).collect {
                when (it) {
                    is DownloadState.InProgress -> {
                        mDownloadListener?.onDownload(it.progress)
                    }
                    is DownloadState.Success -> {
                        mDownloadListener?.onSuccess(it.file.absolutePath)
                        Logger.e("下载成功")
                    }
                    is DownloadState.Error -> {
                        mDownloadListener?.onError("下载失败：${it.throwable.message}")
                        file?.delete()
                        Logger.e("下载失败：${it.throwable.message}")
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return mBinder
    }

    class ServiceBinder(downloadService: DownloadService) :
        Binder() {
        var weakReference: WeakReference<DownloadService>
        val service: DownloadService?
            get() = weakReference.get()

        init {
            weakReference =
                WeakReference(downloadService)
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logger.e("DownloadService onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.e("DownloadService onDestroy")
        mBinder = null
    }

    interface DownloadListener {
        fun onSuccess(path: String)
        fun onDownload(progress: Int)
        fun onError(msg: String)
    }
}