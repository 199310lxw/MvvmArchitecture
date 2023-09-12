package com.xwl.common_base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.orhanobut.logger.Logger
import com.xwl.common_base.response.BaseResponse
import com.xwl.common_lib.callback.IHttpCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
abstract class BaseViewModel: ViewModel() {
    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的，不然我加他个鸡儿加
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { UnPeekLiveData<String>() }
        //隐藏
        val dismissDialog by lazy { UnPeekLiveData<Boolean>() }
    }

    fun <T> request(
        requestCall: suspend () -> BaseResponse<T>?,
        calllback: IHttpCallBack<T>
    ) {
        sendRequest(requestCall,showLoading =  {
            if(it) {
                loadingChange.showDialog.value = "正在请求"
            } else {
                loadingChange.dismissDialog.value = true
            }
        }, errorBlock = {
            calllback.onFailure(it)
        }, successBlock = {
            calllback.onSuccess(it)
        })
    }

   fun <T> sendRequest(
       requestCall: suspend () -> BaseResponse<T>?,
       showLoading: ((Boolean) -> Unit)? = null,
       errorBlock: (String?) -> Unit,
       successBlock: (T) -> Unit
   ) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = requestFlow(requestCall, errorBlock = errorBlock,showLoading = showLoading)
            if (data != null) {
                successBlock.invoke(data)
            } else {
                errorBlock.invoke("数据为空")
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
    suspend fun <T> requestFlow(
        requestCall: suspend () -> BaseResponse<T>?,
        errorBlock: (String?) -> Unit,
        showLoading: ((Boolean) -> Unit)? = null
    ): T? {
        var data: T? = null
        val flow = requestFlowResponse(requestCall,errorBlock, showLoading)
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
    suspend fun <T> requestFlowResponse(
        requestCall: suspend () -> BaseResponse<T>?,
        errorBlock: (String?) -> Unit,
        showLoading: ((Boolean) -> Unit)? = null
    ): Flow<BaseResponse<T>?> {
        //1.执行请求
        val flow = flow {
            //设置超时时间
            val response = withTimeout(10 * 1000) {
                requestCall()
            }

            if (response?.isSuccess() == false) {
                errorBlock?.invoke(response.msg)
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
//                val exception = ExceptionHandler.handleException(e)
                errorBlock?.invoke(e.message)
            }
            //6.请求完成，包括成功和失败
            .onCompletion {
                showLoading?.invoke(false)
            }
        return flow
    }
}