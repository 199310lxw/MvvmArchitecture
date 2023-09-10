package com.xwl.common_lib.Ext
import android.view.View
import com.orhanobut.logger.Logger

var lastClickTime = 0L
fun View.onClick(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action(it)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
 }

fun View.gone() {
    visibility = View.GONE
}