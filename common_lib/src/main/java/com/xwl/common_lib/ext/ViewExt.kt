package com.xwl.common_lib.ext
import android.view.View

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