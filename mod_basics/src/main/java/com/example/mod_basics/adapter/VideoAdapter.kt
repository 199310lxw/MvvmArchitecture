package com.example.mod_basics.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_basics.R
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setScanImage
import com.xwl.common_lib.utils.ScreenUtil
import com.xwl.common_lib.views.CustomJzvdStd

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class VideoAdapter : BaseQuickAdapter<VideoBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: VideoBean?) {
        val jzVideo = holder.getView<CustomJzvdStd>(R.id.jzVideo)
        val imgWidth = ScreenUtil.getScreenWidth() / 2 - 40
        val params = ConstraintLayout.LayoutParams(imgWidth, imgWidth * 2 / 3)
        jzVideo.layoutParams = params
        item?.let {
            holder.setText(R.id.tvTitle, it.title)
            holder.setText(R.id.tvType, it.type)
//            jzVideo.loadVideoFirstFrameRound(it.videoUrl)
            jzVideo.setUp(
                it.videoUrl,
                it.title
            )
            jzVideo.posterImageView.setScanImage(it.videoUrl)
        }
        jzVideo.onClick {
            item?.let {
                startVideo(jzVideo)
            }
        }
    }

    private fun startVideo(view: CustomJzvdStd) {
        view.startVideo()
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.basics_item_video, parent)
    }
}