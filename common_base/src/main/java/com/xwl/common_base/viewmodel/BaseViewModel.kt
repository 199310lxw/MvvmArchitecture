package com.xwl.common_base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_net.download.DownloadManager
import com.example.lib_net.download.DownloadState
import com.example.lib_net.error.ExceptionHandler
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.orhanobut.logger.Logger
import com.xwl.common_base.response.BaseResponse
import com.xwl.common_lib.callback.IHttpCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
abstract class BaseViewModel : ViewModel() {
    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    val error: MutableLiveData<String> = MutableLiveData<String>()

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框,需要跟网络请求显示隐藏loading
     */
    inner class UiLoadingChange {
        //加载提示框
        val showDialog by lazy { UnPeekLiveData<Boolean>() }
    }

    protected open fun getRequestBody(json: String): RequestBody {
//        return RequestBody.create("application/json".toMediaTypeOrNull(), json!!)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    protected open fun getRequestBody(file: File): RequestBody {
        return file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    /**
     * 运行在主线程中，可直接调用
     * @param errorBlock 错误回调
     * @param responseBlock 请求函数
     */
    fun launchUI(errorBlock: (Int?, String?) -> Unit, responseBlock: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            safeApiCall(errorBlock = errorBlock, responseBlock)
        }
    }

    /**
     * 通用网络请求
     */
    fun <T> request(
        requestCall: suspend () -> BaseResponse<T>?,
        calllback: IHttpCallBack<T>
    ) {
        sendRequest(requestCall, showLoading = {
            loadingChange.showDialog.value = it
        }, errorBlock = {
            calllback.onFailure(it)
        }, successBlock = {
            calllback.onSuccess(it)
        })
    }

    /**
     * 需要运行在协程作用域中
     * @param errorBlock 错误回调
     * @param responseBlock 请求函数
     */
    private suspend fun <T> safeApiCall(
        errorBlock: suspend (Int?, String?) -> Unit,
        responseBlock: suspend () -> T?
    ): T? {
        try {
            return responseBlock()
        } catch (e: Exception) {
            e.printStackTrace()
            val exception = ExceptionHandler.handleException(e)
            errorBlock(exception.errCode, exception.errMsg)
        }
        return null
    }


    private fun <T> sendRequest(
        requestCall: suspend () -> BaseResponse<T>?,
        showLoading: ((Boolean) -> Unit)? = null,
        errorBlock: (String?) -> Unit,
        successBlock: (T) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = requestFlow(requestCall, errorBlock = errorBlock, showLoading = showLoading)
            withContext(Dispatchers.Main) {
                if (data != null) {
                    successBlock.invoke(data)
                }
            }
        }
    }


    /**
     * 通过flow执行请求，需要在协程作用域中执行
     * @param errorBlock 错误回调
     * @param requestCall 执行的请求
     * @param showLoading 开启和关闭加载框
     * @return 请求结果
     */
    private suspend fun <T> requestFlow(
        requestCall: suspend () -> BaseResponse<T>?,
        errorBlock: (String?) -> Unit,
        showLoading: ((Boolean) -> Unit)? = null
    ): T? {
        var data: T? = null
        val flow = requestFlowResponse(requestCall, errorBlock, showLoading)
        //7.调用collect获取emit()回调的结果，就是请求最后的结果
        flow.collect {
            data = it?.data
        }
        return data
    }

    /**
     * 通过flow执行请求，需要在协程作用域中执行
     * @param errorBlock 错误回调
     * @param requestCall 执行的请求
     * @param showLoading 开启和关闭加载框
     * @return Flow<BaseResponse<T>>
     */
    private suspend fun <T> requestFlowResponse(
        requestCall: suspend () -> BaseResponse<T>?,
        errorBlock: (String?) -> Unit,
        showLoading: ((Boolean) -> Unit)? = null
    ): Flow<BaseResponse<T>?> {
        //1.执行请求
        val flow = flow {
            //设置超时时间
            val response = withTimeout(8 * 1000) {
                requestCall()
            }

            if (response?.isSuccess() == false) {
                withContext(Dispatchers.Main) {
                    errorBlock.invoke(response.errorMsg)
                }
                return@flow
            }
            //2.发送网络请求结果回调
            emit(response)
            //3.指定运行的线程，flow {}执行的线程
        }.flowOn(Dispatchers.IO)
            .onStart {
                //4.请求开始，展示加载框
                showLoading?.invoke(true)
            }
            //5.捕获异常
            .catch { e ->
                e.printStackTrace()
                Logger.e(e.message)
                errorBlock.invoke(e.message)
            }
            //6.请求完成，包括成功和失败
            .onCompletion {
                showLoading?.invoke(false)
            }

        return flow
    }

    fun downloadFile(url: String, file: File, downloadState: (DownloadState) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            DownloadManager.download(url, file).collect {
                downloadState.invoke(it)
            }
        }
    }
}