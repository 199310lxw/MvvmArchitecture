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
    private var mSavePath: String? = null
    private var mFileName = ""
    private lateinit var mPathFile: File
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
            mSavePath = intent.getStringExtra(KeyConstant.KEY_DOWNLOAD_FILE).toString()
            mFileName = intent.getStringExtra(KeyConstant.KEY_DOWNLOAD_FILE_NAME).toString()

            mSavePath = if (mDownloadUrl.endsWith(".apk")) {
                KeyConstant.APP_UPDATE_PATH
            } else if (mDownloadUrl.endsWith(".mp4") || mDownloadUrl.endsWith(".m3u8")) {
                "${KeyConstant.APP_ROOT_PATH}/video"
            } else if (mDownloadUrl.endsWith(".jpg") || mDownloadUrl.endsWith(".png") || mDownloadUrl.endsWith(
                    ".jpeg"
                ) || mDownloadUrl.endsWith(
                    ".webp"
                )
            ) {
                "${KeyConstant.APP_ROOT_PATH}/images"
            } else {
                "${KeyConstant.APP_ROOT_PATH}/other"
            }

            mPathFile = File(mSavePath).also {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            val fileType = mDownloadUrl.substringAfterLast(".", " ")
            Logger.i("mSavePath----->$mSavePath")
            Logger.i("mFileName----->$mFileName")
            Logger.i("fileType----->$fileType")

            mDownloadFile = File(mSavePath, "${mFileName}.$fileType").also {
                it.createNewFile()
            }

            startDownload(mDownloadFile)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startDownload(file: File) {
        lifecycleScope.launch {
            DownloadManager.download(mDownloadUrl, file).collect {
                mDownloadListener?.callback(it)
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
        Logger.i("DownloadService onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("DownloadService onDestroy")
        mBinder = null
    }

    interface DownloadListener {
        fun callback(state: DownloadState)
    }
}