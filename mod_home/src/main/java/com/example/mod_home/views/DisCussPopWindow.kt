package com.example.mod_home.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import com.example.mod_home.R

/**
 * @author  lxw
 * @date 2023/11/4
 * descripe
 */
class DisCussPopWindow(
    val context: Context,
    private val AnchorView: View
) : PopupWindow() {
    init {
        initView()
    }

    private fun initView() {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_discuss_pop, null)
        width = FrameLayout.LayoutParams.WRAP_CONTENT
        height = FrameLayout.LayoutParams.WRAP_CONTENT
        animationStyle = com.xwl.common_base.R.style.RightByAchorAnimStyle
        isOutsideTouchable = true
    }

    fun show(gravity: Int) {
        this.showAsDropDown(AnchorView, -480, -100, gravity)
    }
}