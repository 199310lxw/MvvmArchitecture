package com.example.mod_home.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.mod_home.R
import com.orhanobut.logger.Logger
import com.xwl.common_lib.bean.HotBean
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.utils.ClickUtil

/**
 * @author  lxw
 * @date 2023/10/6
 * descripe
 */
class HomeSortAdapter : BaseQuickAdapter<Int, QuickViewHolder>() {
    private lateinit var mAdapter: HomeSortItemAdapter
    private var mLists: ArrayList<HotBean>? = null
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Int?) {
        val rv = holder.getView<RecyclerView>(R.id.rv)
        mAdapter = HomeSortItemAdapter()
        rv.adapter = mAdapter
        mLists?.let {
            mAdapter.submitList(it)
        }
        mAdapter.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener<HotBean> {
            override fun onClick(adapter: BaseQuickAdapter<HotBean, *>, view: View, position: Int) {
                if (ClickUtil.isFastClick()) {
                    Logger.e("点击速度太快了")
                    return
                }
                TipsToast.showTips("点击了第${position + 1}项")
            }
        })
    }

    fun setData(lists: ArrayList<HotBean>) {
        this.mLists = lists
    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_home_sort, parent)
    }
}