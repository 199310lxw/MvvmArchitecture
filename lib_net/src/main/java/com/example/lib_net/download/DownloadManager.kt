package com.example.lib_net.download

import com.example.lib_net.api.DownloadAPiService
import com.orhanobut.logger.Logger
import com.xwl.common_lib.constants.UrlConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile
import java.util.*

/**
 * @author  lxw
 * @date 2023/9/20
 * descripe
 */
object DownloadManager {
    suspend fun download(url: String, file: File): Flow<DownloadState> {
        val mFlow = flow {
            val range = String.format(Locale.CHINESE, "bytes=%d-", file.length())
            Logger.e(range)
//           val response = ApiManager.downloadApi.downloadFile(range,url)
            val retrofit = Retrofit.Builder()
                .baseUrl(UrlConstants.BASE_URL)
                .client(OkHttpClient())
                .build()
            emit(DownloadState.Start(true))
            val response = retrofit.create(DownloadAPiService::class.java).downloadFile(range, url)
            if (response.isSuccessful) {
                saveToFile(response.body()!!, file, progress = {
                    emit(DownloadState.InProgress(it))
                })
                emit(DownloadState.Success(file))
            } else {
                if (response.toString().contains("Requested Range Not Satisfiable")) { //range超出界限
                    emit(DownloadState.Success(file))
                } else {
                    emit(DownloadState.Error(IOException(response.toString())))
                }
            }
        }.retry(1) { cause ->
            Logger.e("retrying cause: $cause")
            true
        }.catch {
            Logger.e("${it.stackTraceToString()}")
            emit(DownloadState.Error(it))
        }.flowOn(Dispatchers.IO)
        return mFlow
    }

    private inline fun saveToFile(responseBody: ResponseBody, file: File, progress: (Int) -> Unit) {
        val total = responseBody.contentLength()
        val fileLength = file.length()
        var bytesCopied = 0
        var emittedProgress = 0
        try {
            val input = responseBody.byteStream()
            val savedFile = RandomAccessFile(file, "rw")
            savedFile.seek(fileLength)
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var bytes = input.read(buffer)
            while (bytes >= 0) {
                savedFile.write(buffer, 0, bytes)
                bytesCopied += bytes
                bytes = input.read(buffer)
                val progress = ((bytesCopied + fileLength) * 100 / (total + fileLength)).toInt()
                if (progress - emittedProgress > 0) {
                    progress(progress)
                    emittedProgress = progress
                }
            }
            savedFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace();
        }
    }
}