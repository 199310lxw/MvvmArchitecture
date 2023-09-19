package com.example.lib_net.download

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.lib_net.manager.ApiManager
import com.orhanobut.logger.Logger
import com.xwl.common_lib.constants.KeyConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

/**
 * @author  lxw
 * @date 2023/9/19
 * descripe
 */
class DownloadService: LifecycleService() {
    private var mDownloadUrl = ""

    private var mBinder: ServiceBinder? = null

    init {
        mBinder = ServiceBinder(this)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent != null) {
            mDownloadUrl = intent.getStringExtra(KeyConstant.KEY_DOWNLOAD_URL).toString()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun setOnProgressListener(downloadListener: DownloadListener) {
        if(mDownloadUrl.isNullOrEmpty()) return
        lifecycleScope.launch{
            download(mDownloadUrl, onProgressChange =  {
                downloadListener.onProgress(it.toInt())
            })
        }
    }

    /**
     * 下载比较简单的写法：可取消
     * @param onProgressChange 返回简单 [0.0-1.0】
     * @return true 下载成功
     */
    suspend fun download(url: String,onProgressChange: ((Double) -> Unit)? = null): Boolean = withContext(
        Dispatchers.IO) {
        val fileName = "date.apk"
        val file = File(KeyConstant.APP_UPDATE_PATH,fileName)
        var downloadLength: Long = 0
        if (file.exists()) {
            downloadLength = file.length()
        } else {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val range = String.format(Locale.CHINESE, "bytes=%d-", downloadLength)
        Logger.e(range)
        try {
            Logger.e("11111111111")
            val body = ApiManager.downloadApi.downloadFile(range,url).body() ?:return@withContext  false
            Logger.e(body.toString())
            val totalSize =  body.contentLength().toDouble()

            body.byteStream().use {
                FileOutputStream(file).use { out ->
                    //it.copyTo(targetOutputStream) 如果不需要任务取消可以用 copyTo
                    var bytesCopied: Long = 0
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var bytes = it.read(buffer)
                    while (bytes >= 0 && isActive) {
                        out.write(buffer, 0, bytes)
                        bytesCopied += bytes
                        bytes = it.read(buffer)
                        val percent = bytesCopied / totalSize
                        Logger.e(percent.toString())
                        onProgressChange?.invoke(percent)
                    }

                }
            }
            return@withContext  true
        } catch (e: Exception) {
            e.printStackTrace()
            file.delete()//删除失败的文件
            return@withContext  false
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
        Logger.e("UpdateService onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.e("UpdateService onDestroy")
        mBinder = null
    }

    interface DownloadListener {
        fun onProgress(progress: Int)
    }
}