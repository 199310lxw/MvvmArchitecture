package com.xwl.common_base.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * @author  lxw
 * @date 2023/9/11
 * descripe
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initBar()
    }

    private fun initBar() {
        ImmersionBar.with(this)
//            .statusBarColor("#ffffff")
            .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
            //                .fitsSystemWindows(true)   //解决状态栏和布局顶部重合
            .init()
    }
}