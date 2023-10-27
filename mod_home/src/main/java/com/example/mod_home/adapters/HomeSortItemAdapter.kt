package com.example.mod_home.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.ext.setUrlCircle

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class HomeSortItemAdapter : BaseQuickAdapter<VideoBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: VideoBean?) {
        val img = holder.getView<ImageView>(R.id.img)
//        val imgWidth = (ScreenUtil.getScreenWidth() - 600) / 4
//        val params = LinearLayout.LayoutParams(
//            imgWidth,
//            imgWidth
//        )
//        img.layoutParams = params
        item?.let {
            holder.setText(R.id.tvName, it.title)
            img.setUrlCircle(it.posterUrl)
        }
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_home_sort_item, parent)
    }
}