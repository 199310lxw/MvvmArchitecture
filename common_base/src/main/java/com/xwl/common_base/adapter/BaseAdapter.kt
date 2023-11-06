package com.xwl.common_base.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder

/**
 * @author  lxw
 * @date 2023/11/6
 * descripe
 */
abstract class BaseAdapter<T>(open var item: List<T> = emptyList()) :
    BaseQuickAdapter<T, QuickViewHolder>() {
    init {
        isEmptyViewEnable = true
        animationEnable = false
//        itemAnimation = AlphaInAnimation(300)
    }

    fun setEmptyView() {
        if (isEmptyViewEnable) {
            item = arrayListOf()
            submitList(item)
            setEmptyViewLayout(
                context,
                com.xwl.common_lib.R.layout.view_empty_data
            )
        }
    }
}