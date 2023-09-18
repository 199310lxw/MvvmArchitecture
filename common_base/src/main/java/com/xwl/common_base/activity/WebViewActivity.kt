package com.xwl.common_base.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.xwl.common_base.databinding.ActivityWebviewBinding
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.ext.dismissLoadingExt
import com.xwl.common_lib.ext.showLoadingExt

/**
 * @author mingyan.su
 * @date   2023/3/31 17:04
 * @desc   文字详情
 */
class WebViewActivity : BaseVmVbActivity<EmptyViewModel,ActivityWebviewBinding>() {
    private var mTitle = ""

    companion object {
        fun start(context: Context, url: String, title: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KeyConstant.KEY_URL, url)
            intent.putExtra(KeyConstant.KEY_TITLE, title)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        initWebView()
    }

    private fun initWebView() {
        mViewBinding.webView.webChromeClient = mWebChromeClient
        mViewBinding.webView.webViewClient = mWebViewClient //如果不设置则跳系统浏览器
    }

    override fun initData() {
        val url = intent?.getStringExtra(KeyConstant.KEY_URL)
        mTitle = intent?.getStringExtra(KeyConstant.KEY_TITLE) ?: ""
        showLoadingExt()
        mViewBinding.webView.loadUrl(url)
    }

    /**
     * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
     */
    private val mWebChromeClient = object : WebChromeClient() {
        /**
         * 网页Title更改
         */
        override fun onReceivedTitle(webView: WebView?, title: String?) {
            super.onReceivedTitle(webView, title)
            if (webView != null && !webView.canGoBack() && mTitle.isNotEmpty()) {
                mViewBinding.titleBar.setMiddleText(mTitle)
            } else {
                var titleResult = ""
                if (!title.isNullOrEmpty() && !title.startsWith("http", true)) {
                    titleResult = title
                }
                mViewBinding.titleBar.setMiddleText(titleResult)
            }
        }

        /**
         * 文件选择回调
         */
        override fun onShowFileChooser(
            webView: WebView?,
            back: ValueCallback<Array<Uri>>?,
            chooser: FileChooserParams?
        ): Boolean {
            return super.onShowFileChooser(webView, back, chooser)
        }

        /**
         * 获得网页的加载进度并显示
         */
        override fun onProgressChanged(webView: WebView?, process: Int) {
            super.onProgressChanged(webView, process)
            if (process == 100) {
                dismissLoadingExt()
            }
        }
    }

    /**
     * 处理各种通知 & 请求事件
     */
    private val mWebViewClient = object : WebViewClient() {
        /**
         * 网页加载完毕
         */
        override fun onPageFinished(webView: WebView?, url: String?) {
            super.onPageFinished(webView, url)
        }

        /**
         * 跳转拦截处理
         * 打开网页时，不调用系统浏览器进行打开，而是在本WebView中直接显示
         */
        override fun shouldOverrideUrlLoading(webview: WebView?, url: String?): Boolean {
            //处理url
            //mBinding.webView.loadUrl(url)
            return super.shouldOverrideUrlLoading(webview, url)
        }

        /**
         * 设置错误页面
         */
        override fun onReceivedError(webview: WebView?, p1: Int, p2: String?, p3: String?) {
            super.onReceivedError(webview, p1, p2, p3)
        }

        override fun onReceivedSslError(
            webView: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(webView, handler, error)
            //忽略ssl错误
            handler?.proceed()
        }
    }

    override fun onBackPressed() {
        if (mViewBinding.webView.canGoBack()) {
            mViewBinding.webView.goBack() // 返回上一个浏览页
        } else {
            super.onBackPressed()
        }
    }
}