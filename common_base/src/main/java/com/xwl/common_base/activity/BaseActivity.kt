package com.xwl.common_base.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xwl.common_base.R

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
    }
}