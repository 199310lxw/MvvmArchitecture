package com.xwl.common_base.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.xwl.common_base.R
import com.xwl.common_lib.bean.ShareBean

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class ShareDialogAdapter : BaseQuickAdapter<ShareBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: ShareBean?) {
        val img = holder.getView<ImageView>(R.id.iv_share_image)
        item?.let {
            img.setImageDrawable(it.shareIcon)
            holder.setText(R.id.tv_share_text, it.shareName)
        }
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.share_item, parent)
    }
}