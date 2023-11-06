package com.example.mod_home.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.xwl.common_base.adapter.BaseAdapter
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.ext.setUrlRound
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class HomeAdapter : BaseAdapter<VideoBean>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: VideoBean?) {
        val img = holder.getView<ImageView>(R.id.imgVideo)
        val imgWidth = ScreenUtil.getScreenWidth() - 60
        val params = LinearLayout.LayoutParams(imgWidth, imgWidth * 1 / 3)
        img.layoutParams = params
        item?.let {
            holder.setText(R.id.tvTitle, it.title)
            img.setUrlRound(it.posterUrl)
        }
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_video, parent)
    }
}