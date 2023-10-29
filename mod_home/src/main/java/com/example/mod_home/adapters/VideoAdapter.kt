package com.example.mod_home.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.ext.loadVideoFirstFrameRound
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class VideoAdapter : BaseQuickAdapter<VideoBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: VideoBean?) {
        val imgVideo = holder.getView<ImageView>(R.id.imgVideo)
        val imgWidth = ScreenUtil.getScreenWidth() / 2 - 40
        val params = LinearLayout.LayoutParams(imgWidth, imgWidth * 2 / 3)
        imgVideo.layoutParams = params
        item?.let {
            holder.setText(R.id.tvTitle, it.title)
            holder.setText(R.id.tvType, it.type)
            imgVideo.loadVideoFirstFrameRound(it.posterUrl?.ifEmpty { it.videoUrl })
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