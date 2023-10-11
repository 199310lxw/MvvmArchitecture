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
import java.io.IOException
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
    private var mFileName = ""
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
            mFileName = intent.getStringExtra(KeyConstant.KEY_DOWNLOAD_FILE_NAME).toString()
            if (mFilePath != null) {
                if (mDownloadUrl.endsWith(".apk")) {
                    mDownloadFile = File(mFilePath, "${mFileName}.apk")
                } else if (mDownloadUrl.endsWith(".mp4")) {
                    mDownloadFile = File(mFilePath, "${mFileName}.mp4")
                } else if (mDownloadUrl.endsWith(".m3u8")) {
                    mDownloadFile = File(mFilePath, "${mFileName}.txt")
                } else if (mDownloadUrl.endsWith(".jpg")) {
                    mDownloadFile = File(mFilePath, "${mFileName}.jpg")
                } else if (mDownloadUrl.endsWith(".png")) {
                    mDownloadFile = File(mFilePath, "${mFileName}.png")
                } else if (mDownloadUrl.endsWith(".webp")) {
                    mDownloadFile = File(mFilePath, "${mFileName}.webp")
                } else if (mDownloadUrl.endsWith(".svg")) {
                    mDownloadFile = File(mFilePath, "${mFileName}.svg")
                }
                try {
                    if (!mDownloadFile.exists()) {
                        mDownloadFile.createNewFile()
                    }
                } catch (e: IOException) {
                    Logger.e(e.message)
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