package com.example.mod_home.adapters

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class HotAdapter: BaseQuickAdapter<String,QuickViewHolder>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: String?) {
        holder.setText(R.id.tv,item)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
       return QuickViewHolder(R.layout.item_hot,parent)
    }
}