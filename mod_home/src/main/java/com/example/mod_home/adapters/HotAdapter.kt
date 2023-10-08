package com.example.mod_home.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.xwl.common_lib.bean.HotBean
import com.xwl.common_lib.ext.setUrlRound
import com.xwl.common_lib.utils.ScreenUtil

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class HotAdapter : BaseQuickAdapter<HotBean, QuickViewHolder>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: HotBean?) {
        val img = holder.getView<ImageView>(R.id.img)
        val imgWidth = ScreenUtil.getScreenWidth() / 2 - 100
        val params = LinearLayout.LayoutParams(imgWidth, imgWidth * 3 / 4)
        img.layoutParams = params
        item?.let {
            holder.setText(R.id.tvName, it.title)
            img.setUrlRound(it.mainPicUrl)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_hot, parent)
    }
}