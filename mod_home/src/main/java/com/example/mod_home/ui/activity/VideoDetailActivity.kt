package com.example.mod_home.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.Drawable
import android.os.Bundle
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout
import com.example.lib_net.download.DownloadUtil
import com.example.mod_home.R
import com.example.mod_home.adapters.RecommendAdapter
import com.example.mod_home.databinding.ActivityVideoDetailBinding
import com.example.mod_home.viewmodel.CourseDetailViewModel
import com.gyf.immersionbar.ImmersionBar
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.dialog.ShareDialog
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.dialog.TipsToast.showTips
import com.xwl.common_lib.ext.gone
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setScanImage
import com.xwl.common_lib.ext.visible
import com.xwl.common_lib.utils.ScreenRotateUtils


/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class VideoDetailActivity : BaseVmVbActivity<CourseDetailViewModel, ActivityVideoDetailBinding>(),
    ScreenRotateUtils.OrientationChangeListener {

    private var mVideoUrl: String? = null
    private var mPosterUrl: String? = null
    private var mVideoName: String? = null

    private var conn: ServiceConnection? = null

    private lateinit var mRecommendAdapter: RecommendAdapter


    private var mCurrentPage = 1
    private var mIsRefresh = true
    private var totalSize = 10

    private var isCollected = false

    companion object {
        fun startActivity(mContext: Context, videoUrl: String, posterUrl: String, name: String) {
            val intent = Intent(mContext, VideoDetailActivity::class.java)
            intent.putExtra(KeyConstant.KEY_COURSE_VIDEO_URL, videoUrl)
            intent.putExtra(KeyConstant.KEY_COURSE_VIDEO_POSTER_URL, posterUrl)
            intent.putExtra(KeyConstant.KEY_COURSE_VIDEO_NAME, name)
            mContext.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        initbar()
        mVideoUrl = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_URL)
        mPosterUrl = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_POSTER_URL)
        mVideoName = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_NAME)
        mViewBinding.tvTitle.text = mVideoName
        mViewBinding.jzVideo.setUp(
            mVideoUrl,
            mVideoName
        )
        mViewBinding.jzVideo.posterImageView.setScanImage(mVideoUrl)
        mViewBinding.jzVideo.startVideo()
        ScreenRotateUtils.getInstance(this.applicationContext).setOrientationChangeListener(this)
        mViewBinding.tvDownload.onClick {
            mVideoUrl?.let { it1 ->
                mVideoName?.let { it2 ->
                    DownloadUtil.startDownload(this, it1, it2, start = {
                        mViewBinding.downloadProgressbar.visible()
                    }, progress = {
                        mViewBinding.downloadProgressbar.progress = it
                        mViewBinding.tvDownload.text = String.format("%d%%", it)
                    }, success = {
                        showTips("下载成功,请到我的下载页面查看详情")
                        mViewBinding.downloadProgressbar.gone()
                        mViewBinding.tvDownload.text = resources.getString(R.string.default_cache)
                    }, failure = {
                        showTips(it)
                    })
                }
            }
        }

        mViewBinding.tvShare.onClick {
            ShareDialog.Builder(this@VideoDetailActivity)
                .setOnItemClickListener { _, s ->
                    TipsToast.showTips("分享到：${s}")
                }
                .show()
        }

        mViewBinding.tvCollection.onClick {
            setCollection()
        }


        mViewBinding.smartRefresh.setOnRefreshListener {
            getData()
        }

        mViewBinding.scrollerLayout.onStickyChangeListener =
            ConsecutiveScrollerLayout.OnStickyChangeListener { oldStickyView, newStickyView ->
                if (newStickyView != null && oldStickyView == null) {
                    mViewBinding.llTool.setBackgroundColor(resources.getColor(R.color.black))
                } else {
                    mViewBinding.llTool.setBackgroundColor(resources.getColor(R.color.black_73))
                }
            }
    }

    @SuppressLint("ResourceType")
    private fun initbar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false) //状态栏字体是深色，不写默认为亮色
            .statusBarColor(R.color.black)
            .init()
    }


    private fun setCollection() {
        var drawable: Drawable? = null
        if (!isCollected) {
            isCollected = true
            drawable =
                resources.getDrawable(com.xwl.common_lib.R.drawable.icon_collection_selected)
            drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            mViewBinding.tvCollection.setCompoundDrawables(
                drawable,
                null,
                null,
                null
            )
        } else {
            isCollected = false
            drawable =
                resources.getDrawable(com.xwl.common_lib.R.drawable.icon_collection_unselected)
            drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            mViewBinding.tvCollection.setCompoundDrawables(
                drawable,
                null,
                null,
                null
            )
        }
    }

    override fun onResume() {
        super.onResume()
        ScreenRotateUtils.getInstance(this).start(this)
        JzvdStd.goOnPlayOnResume()
    }

    override fun initData() {
        initRecomendList()
        getData()
    }

    private fun getData() {
        mViewModel.getRecommendList(mCurrentPage, 10, showloading = true).observe(this) {
            mViewBinding.tvShare.text = "34"
            mViewBinding.tvDicuss.text = "0"
            if (it != null) {
                it.let {
                    if (mIsRefresh) {
                        if (it.isEmpty()) {
                            mRecommendAdapter.setEmptyViewLayout(
                                this@VideoDetailActivity,
                                com.xwl.common_lib.R.layout.view_empty_data
                            )
                        }
                    } else if (it.size >= totalSize) {
                        mViewBinding.smartRefresh.setEnableLoadMore(true)
                    }
                    mRecommendAdapter.submitList(it)
                    mViewBinding.smartRefresh.finishRefresh()
                }
            } else {
                if (mIsRefresh) {
                    mRecommendAdapter.setEmptyViewLayout(
                        this@VideoDetailActivity,
                        com.xwl.common_lib.R.layout.view_empty_data
                    )
                    mViewBinding.smartRefresh.finishRefresh()
                } else {
                    mCurrentPage--
                    mViewBinding.smartRefresh.finishLoadMoreWithNoMoreData()
                }
            }
        }
    }


    private fun initRecomendList() {
        mRecommendAdapter = RecommendAdapter()
        mRecommendAdapter.isEmptyViewEnable = true
        mViewBinding.rvRecommend.adapter = mRecommendAdapter
    }

    override fun orientationChange(orientation: Int) {
        if (Jzvd.CURRENT_JZVD != null && (mViewBinding.jzVideo.state == Jzvd.STATE_AUTO_COMPLETE || mViewBinding.jzVideo.state == Jzvd.STATE_PLAYING || mViewBinding.jzVideo.state == Jzvd.STATE_PAUSE)
            && mViewBinding.jzVideo.screen != Jzvd.SCREEN_TINY
        ) {
            if (orientation in 45..315 && mViewBinding.jzVideo.screen == Jzvd.SCREEN_NORMAL) {
                changeScreenFullLandscape(ScreenRotateUtils.orientationDirection)
            } else if ((orientation in 0..44 || orientation > 315) && mViewBinding.jzVideo.screen == Jzvd.SCREEN_FULLSCREEN) {
                changeScrenNormal()
            }
        }
    }


    /**
     * 竖屏并退出全屏
     */
    private fun changeScrenNormal() {
        if (mViewBinding.jzVideo != null && mViewBinding.jzVideo.screen == Jzvd.SCREEN_FULLSCREEN) {
            mViewBinding.jzVideo.autoQuitFullscreen()
        }
    }

    /**
     * 横屏
     */
    private fun changeScreenFullLandscape(x: Float) {
        //从竖屏状态进入横屏
        if (mViewBinding.jzVideo != null && mViewBinding.jzVideo.screen != Jzvd.SCREEN_FULLSCREEN) {
            if (System.currentTimeMillis() - Jzvd.lastAutoFullscreenTime > 2000) {
                mViewBinding.jzVideo.autoFullscreen(x)
                Jzvd.lastAutoFullscreenTime = System.currentTimeMillis()
            }
        }
    }

    override fun onBackPressed() {
        if (JzvdStd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        ScreenRotateUtils.getInstance(this).stop()
        Jzvd.releaseAllVideos()
    }

    override fun onStop() {
        super.onStop()
        stopService(intent)
        conn?.let {
            unbindService(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ScreenRotateUtils.getInstance(this.applicationContext).setOrientationChangeListener(null)
        Jzvd.releaseAllVideos()
    }
}