package com.example.lib_net.error

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

/**
 * 统一错误处理工具类
 */
object ExceptionHandler {

    fun handleException(e: Throwable): ResultException {
        val ex:ResultException
        if(e is ApiException) {
            ex = ResultException(e.errCode,e.errMsg)
        } else if(e is HttpException) { //网络code码错误
            ex = when(e.code()) {
                ERROR.UNAUTHORIZED.code -> ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                ERROR.FORBIDDEN.code -> ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                ERROR.NOT_FOUND.code -> ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                ERROR.REQUEST_TIMEOUT.code -> ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                ERROR.GATEWAY_TIMEOUT.code -> ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                ERROR.INTERNAL_SERVER_ERROR.code -> ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                ERROR.BAD_GATEWAY.code -> ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                ERROR.SERVICE_UNAVAILABLE.code ->ResultException( ERROR.UNAUTHORIZED.code, ERROR.UNAUTHORIZED.errMsg)
                else ->  ResultException(e.code(), e.message())
            }
        } else if(e is IOException) { //网络问题
            ex = ResultException(ERROR.NETWORD_ERROR.code, ERROR.NETWORD_ERROR.errMsg)
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException
                || e is MalformedJsonException
        ) {
            ex = ResultException(ERROR.PARSE_ERROR.code, ERROR.PARSE_ERROR.errMsg)
        } else if (e is ConnectException) {
            ex = ResultException(ERROR.NETWORD_ERROR.code, ERROR.NETWORD_ERROR.errMsg)
        } else if (e is javax.net.ssl.SSLException) {
            ex = ResultException(ERROR.SSL_ERROR.code, ERROR.SSL_ERROR.errMsg)
        } else if (e is java.net.SocketException) {
            ex = ResultException(ERROR.TIMEOUT_ERROR.code, ERROR.SSL_ERROR.errMsg)
        } else if (e is java.net.SocketTimeoutException) {
            ex = ResultException(ERROR.TIMEOUT_ERROR.code, ERROR.SSL_ERROR.errMsg)
        } else if (e is java.net.UnknownHostException) {
            ex = ResultException(ERROR.UNKNOW_HOST.code, ERROR.SSL_ERROR.errMsg)
        } else {
            ex = if (!e.message.isNullOrEmpty()) ResultException(1000, e.message!!)
            else ResultException(ERROR.UNKNOWN.code, ERROR.SSL_ERROR.errMsg)
        }
        return ex
    }
}
