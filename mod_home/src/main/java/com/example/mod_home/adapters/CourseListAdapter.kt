package com.example.mod_home.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.xwl.common_lib.bean.CourseListBean

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class CourseListAdapter : BaseQuickAdapter<CourseListBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: CourseListBean?) {
        val view = holder.getView<TextView>(R.id.tvName)
        val myShapeDrawable = view.background as GradientDrawable
        item?.let {
            holder.setText(R.id.tvName, it.name)
            myShapeDrawable.setColor(Color.parseColor(it.bgColor))
        }
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_course_layout, parent)
    }
}