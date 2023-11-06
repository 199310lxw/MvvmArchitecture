package com.example.mod_home.ui.activity

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.dragswipe.QuickDragAndSwipe
import com.chad.library.adapter.base.dragswipe.listener.OnItemDragListener
import com.chad.library.adapter.base.dragswipe.listener.OnItemSwipeListener
import com.example.mod_home.adapters.CollectionAdapter
import com.example.mod_home.databinding.ActivityCollectedBinding
import com.example.mod_home.viewmodel.CollectedViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.dialog.MessageDialog
import com.xwl.common_lib.bean.CollectionBean
import com.xwl.common_lib.bean.User
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.constants.CollectionType
import com.xwl.common_lib.provider.UserServiceProvider
import com.xwl.common_lib.utils.ClickUtil

class CollectedActivity : BaseVmVbActivity<CollectedViewModel, ActivityCollectedBinding>() {
    private var mQuickDragAndSwipe: QuickDragAndSwipe = QuickDragAndSwipe();//可进行左右滑动删除
    private lateinit var mAdapter: CollectionAdapter
    private var user: User? = null
    override fun initView(savedInstanceState: Bundle?) {
        initRv()
        user = UserServiceProvider.getUserInfo()
        mViewBinding.refreshLayout.setEnableLoadMore(false)
        mViewBinding.refreshLayout.setOnRefreshListener {
            user?.let { it1 -> getData(it1.phone, false) }
        }
    }

    override fun initData() {
        user?.let { it -> getData(it.phone) }
    }

    private fun initRv() {
        mAdapter = CollectionAdapter()
        mAdapter.isEmptyViewEnable = true
        mQuickDragAndSwipe
            .attachToRecyclerView(mViewBinding.rv)
            .setItemViewSwipeEnabled(true)
            .setDragMoveFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN)//可进行上下拖动，交换位置
            .setSwipeMoveFlags(ItemTouchHelper.LEFT)
            .setDataCallback(mAdapter)
            .setItemDragListener(object : OnItemDragListener {
                override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

                }

                override fun onItemDragMoving(
                    source: RecyclerView.ViewHolder,
                    from: Int,
                    target: RecyclerView.ViewHolder,
                    to: Int
                ) {

                }

                override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder, pos: Int) {

                }
            })
            .setItemSwipeListener(object : OnItemSwipeListener {
                override fun onItemSwipeStart(
                    viewHolder: RecyclerView.ViewHolder?,
                    bindingAdapterPosition: Int
                ) {

                }

                override fun onItemSwipeEnd(
                    viewHolder: RecyclerView.ViewHolder,
                    bindingAdapterPosition: Int
                ) {

                }

                override fun onItemSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int,
                    bindingAdapterPosition: Int
                ) {
                    user?.let {
                        mAdapter.getItem(bindingAdapterPosition)?.let { it1 ->
                            mViewModel.disCollectVideo(
                                it.phone,
                                it1.url,
                                true
                            ).observe(this@CollectedActivity) { it1 ->
                                if (it1 == "true") {
                                    getData(it.phone)
                                }
                            }
                        }
                    }

                }

                override fun onItemSwipeMoving(
                    canvas: Canvas,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    isCurrentlyActive: Boolean
                ) {

                }
            })
        mViewBinding.rv.adapter = mAdapter
        mAdapter.setOnItemClickListener(object :
            BaseQuickAdapter.OnItemClickListener<CollectionBean> {
            override fun onClick(
                adapter: BaseQuickAdapter<CollectionBean, *>,
                view: View,
                position: Int
            ) {
                if (ClickUtil.isFastClick()) {
                    return
                }
                val video = VideoBean()
                video.title = adapter.getItem(position)?.title
                video.videoUrl = adapter.getItem(position)?.url
                video.type = adapter.getItem(position)?.videoType
                video.posterUrl = adapter.getItem(position)?.posterUrl
                adapter.getItem(position)?.let {
                    VideoDetailActivity.startActivity(
                        this@CollectedActivity,
                        video
                    )
                }
            }
        })

        mAdapter.setOnItemLongClickListener { _, _, position ->
            mAdapter.getItem(position)?.let { showDeleteDialog(it.url) }
            true
        }

    }

    private fun showDeleteDialog(url: String) {
        MessageDialog.Builder(this@CollectedActivity)
            .setCancelText("取消")
            .setConfirmText("删除")
            .setContent("确定删除该收藏内容？")
            .setOnCancelClickListener { }
            .setOnConfirmClickListener {
                user?.let {
                    mViewModel.disCollectVideo(
                        it.phone,
                        url,
                        true
                    ).observe(this@CollectedActivity) { it1 ->
                        if (it1 == "true") {
                            getData(it.phone)
                        }
                    }
                }
            }
    }

    private fun getData(phone: String, showLoading: Boolean = true) {
        mViewModel.getCollectedList(phone, CollectionType.VIDEO.name, showLoading)
            .observe(this) {
                it?.let {
                    if (it.isEmpty()) {
                        mAdapter.setEmptyView()
                    } else {
                        mAdapter.submitList(it)
                    }
                    mViewBinding.refreshLayout.finishRefresh()
                } ?: kotlin.run {
                    mViewBinding.refreshLayout.finishRefresh()
                    mAdapter.setEmptyView()
                }
            }
    }
}