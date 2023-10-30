package com.example.mod_home.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.xwl.common_base.dialog.ShareDialog
import com.xwl.common_lib.bean.CollectionBean
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.loadVideoFirstFrameRound
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class CollectionAdapter : BaseQuickAdapter<CollectionBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: CollectionBean?) {
        val imgVideo = holder.getView<ImageView>(R.id.imgVideo)
        val imgShare = holder.getView<ImageView>(R.id.imgShare)
        val imgWidth = ScreenUtil.getScreenWidth() / 2 - 40
        val params = LinearLayout.LayoutParams(imgWidth, imgWidth * 2 / 3)
        imgVideo.layoutParams = params
        item?.let {
            holder.setText(R.id.tvTitle, it.title)
            holder.setText(R.id.tvType, it.type)
            imgVideo.loadVideoFirstFrameRound(it.posterUrl?.ifEmpty { it.url })
        }
        imgShare.onClick {
            ShareDialog.Builder(context as FragmentActivity)
                .setOnItemClickListener { _, s ->
                    TipsToast.showTips("分享到：${s}")
                }
                .show()
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