package com.example.mod_home.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mod_home.adapters.CourseListAdapter
import com.example.mod_home.databinding.ActivityCourseListBinding
import com.example.mod_home.viewmodel.CourseListViewModel
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.bean.CourseListBean
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.utils.ClickUtil

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CourseListActivity : BaseVmVbActivity<CourseListViewModel, ActivityCourseListBinding>() {
    private lateinit var mAdapter: CourseListAdapter
    private var mType = 0
    private var mCurrentPage = 1
    private var size = 10
    private var mIsRefresh = true

    companion object {
        fun startActivity(mContext: Context, type: Int) {
            val intent = Intent(mContext, CourseListActivity::class.java)
            intent.putExtra(KeyConstant.KEY_COURSE_TYPE, type)
            mContext.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mType = intent.getIntExtra(KeyConstant.KEY_COURSE_TYPE, 0)
        initRv()
        mViewBinding.refreshLayout.setEnableLoadMore(false)
        mViewBinding.refreshLayout.setOnRefreshListener {
            mIsRefresh = true
            mCurrentPage = 1
            getData(mCurrentPage, size)
        }
        mViewBinding.refreshLayout.setOnLoadMoreListener {
            mIsRefresh = false
            mCurrentPage++
            Logger.e("----->${mCurrentPage}")
            getData(mCurrentPage, size)
        }
    }

    override fun initData() {
        getData(mCurrentPage, size)
    }

    private fun getData(page: Int, size: Int) {
        mViewModel.getCourseList(page, size, mType).observe(this) {
            if (it != null) {
                it.let {
                    if (mIsRefresh) {
                        if (it.isEmpty()) {
                            mAdapter.setEmptyViewLayout(
                                this@CourseListActivity,
                                com.xwl.common_lib.R.layout.view_empty_data
                            )
                        } else if (it.size >= size) {
                            mViewBinding.refreshLayout.setEnableLoadMore(true)
                        }
                        mAdapter.submitList(it)
                        mViewBinding.refreshLayout.finishRefresh()
                    } else {
                        if (it.isEmpty()) {
                            mCurrentPage--
                            mViewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                            return@let
                        }
                        mAdapter.addAll(it)
                        mViewBinding.refreshLayout.finishLoadMore()
                    }
                }
            } else {
                if (mIsRefresh) {
                    mAdapter.setEmptyViewLayout(
                        this@CourseListActivity,
                        com.xwl.common_lib.R.layout.view_empty_data
                    )
                    mViewBinding.refreshLayout.finishRefresh()
                } else {
                    mCurrentPage--
                    mViewBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                }
            }
        }
    }

    private fun initRv() {
        mAdapter = CourseListAdapter()
        mAdapter.isEmptyViewEnable = true
        mAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.ScaleIn)
        mViewBinding.rv.adapter = mAdapter
        mAdapter.setOnItemClickListener(object :
            BaseQuickAdapter.OnItemClickListener<CourseListBean> {
            override fun onClick(
                adapter: BaseQuickAdapter<CourseListBean, *>,
                view: View,
                position: Int
            ) {
                if (ClickUtil.isFastClick()) {
                    Logger.e("点击速度太快了")
                    return
                }
                adapter.getItem(position)
                    ?.let {
                        CourseDetailActivity.startActivity(
                            this@CourseListActivity,
                            it.videoUrl
                        )
                    }
            }
        })
    }
}