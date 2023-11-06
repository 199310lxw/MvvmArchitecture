package com.xwl.common_base.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.xwl.common_base.R

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class MenuDialogAdapter : BaseAdapter<String>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: String?) {
        item?.let {
            holder.setText(R.id.tv_menu_text, item)
        }
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.layout_menu_item, parent)
    }
}