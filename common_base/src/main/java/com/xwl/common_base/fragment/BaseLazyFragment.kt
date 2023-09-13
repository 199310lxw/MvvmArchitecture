package com.xwl.common_base.fragment

/**
 * @author  lxw
 * @date 2023/9/13
 * descripe
 */
abstract class BaseLazyFragment: BaseFragment() {
    protected var isVisibleToUser = true //是否是第一次加载 true是 false 否
    override fun onResume() {
        super.onResume()
        //懒加载 加载数据
        if (isVisibleToUser) {
            isVisibleToUser = false
            onLazyLoadData()
        }
    }
    protected abstract fun onLazyLoadData()
}