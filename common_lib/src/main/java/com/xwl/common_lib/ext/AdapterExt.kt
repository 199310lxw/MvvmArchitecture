package com.xwl.common_lib.ext

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xwl.common_lib.R

/**
 * @author  lxw
 * @date 2023/9/18
 * descripe
 */
//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: BaseQuickAdapter.AnimationType) {
    //等于0，关闭列表动画 否则开启
    this.animationEnable = true
    this.setItemAnimation(mode)
}

fun  BaseQuickAdapter<*, *>.setEmptyView(context: Context) {
    this.setEmptyViewLayout(context, R.layout.view_empty_data)
}