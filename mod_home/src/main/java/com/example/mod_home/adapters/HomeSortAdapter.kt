package com.example.mod_home.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.xwl.common_base.adapter.BaseAdapter
import com.xwl.common_lib.bean.SortBean
import com.xwl.common_lib.ext.setUrlCircle
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class HomeSortAdapter : BaseAdapter<SortBean>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: SortBean?) {
        val rootView = holder.getView<ConstraintLayout>(R.id.rootView)
        val img = holder.getView<ImageView>(R.id.img)
        val rootWidth = ScreenUtil.getScreenWidth() / 4
        val params = ViewGroup.LayoutParams(
            rootWidth,
            rootWidth
        )
        rootView.layoutParams = params
        item?.let {
            holder.setText(R.id.tvName, it.title)
            img.setUrlCircle(it.picUrl)
        }
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_home_sort, parent)
    }
}