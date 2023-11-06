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
import com.example.lib_net.download.DownloadState
import com.example.lib_net.download.DownloadUtil
import com.example.mod_home.R
import com.example.mod_home.adapters.RecommendAdapter
import com.example.mod_home.databinding.ActivityVideoDetailBinding
import com.example.mod_home.viewmodel.CourseDetailViewModel
import com.gyf.immersionbar.ImmersionBar
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.dialog.ShareDialog
import com.xwl.common_lib.bean.User
import com.xwl.common_lib.bean.VideoBean
import com.xwl.common_lib.constants.CollectionType
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.dialog.TipsToast.showTips
import com.xwl.common_lib.ext.gone
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setScanImage
import com.xwl.common_lib.ext.visible
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.provider.UserServiceProvider
import com.xwl.common_lib.utils.ClickUtil
import com.xwl.common_lib.utils.ScreenRotateUtils


/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class VideoDetailActivity : BaseVmVbActivity<CourseDetailViewModel, ActivityVideoDetailBinding>(),
    ScreenRotateUtils.OrientationChangeListener {

    private var conn: ServiceConnection? = null
    private lateinit var mRecommendAdapter: RecommendAdapter

    private var mCurrentPage = 1
    private var mIsRefresh = true
    private var totalSize = 10

    private var isCollected = false

    private var mVideo: VideoBean? = null

    private var user: User? = null

    companion object {
        fun startActivity(mContext: Context, video: VideoBean) {
            val intent = Intent(mContext, VideoDetailActivity::class.java)
            intent.putExtra(KeyConstant.KEY_VIDEO, video)
            mContext.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        initbar()
        mVideo = intent.getParcelableExtra(KeyConstant.KEY_VIDEO)
        user = UserServiceProvider.getUserInfo()
        mVideo?.let { checkIsCollected(it) }
        mViewBinding.tvTitle.text = mVideo?.title
        mViewBinding.jzVideo.setUp(
            mVideo?.videoUrl,
            mVideo?.title
        )
        mViewBinding.jzVideo.posterImageView.setScanImage(mVideo?.videoUrl)
        mViewBinding.jzVideo.startVideo()
        ScreenRotateUtils.getInstance(this.applicationContext).setOrientationChangeListener(this)
        mViewBinding.tvDownload.onClick {
            mVideo?.videoUrl?.let { it1 ->
                mVideo?.title?.let { it2 ->
                    DownloadUtil.startDownload(this, it1, it2, state = {
                        when (it) {
                            is DownloadState.Start -> {
                                mViewBinding.downloadProgressbar.visible()
                                mViewBinding.tvDownload.isClickable = false
                            }
                            is DownloadState.InProgress -> {
                                mViewBinding.downloadProgressbar.progress = it.progress
                                mViewBinding.tvDownload.text = String.format("%d%%", it.progress)
                            }

                            is DownloadState.Success -> {
                                showTips("下载成功,请到我的下载页面查看详情")
                                mViewBinding.downloadProgressbar.gone()
                                mViewBinding.tvDownload.text =
                                    resources.getString(R.string.default_cache)
                                mViewBinding.tvDownload.isClickable = true
                            }
                            is DownloadState.Error -> {
                                showTips(it.throwable.message)
                                mViewBinding.tvDownload.isClickable = true
                            }
                            else -> {}
                        }
                    })
                }
            }
        }

        mViewBinding.tvShare.onClick {
            ShareDialog.Builder(this@VideoDetailActivity)
                .setOnItemClickListener { _, s ->
                    showTips("分享到：${s}")
                }
                .show()
        }

        mViewBinding.tvCollection.onClick {
            mVideo?.let { it1 -> setCollection(it1) }
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

    /**
     * 判断是否已经收藏了
     */
    private fun checkIsCollected(video: VideoBean) {
        user?.let {
            video.videoUrl?.let { it1 ->
                mViewModel.checkIsColleced(it.phone, it1).observe(this) { it2 ->
                    isCollected = it2 == "true"
                    showCollectView(isCollected)
                }
            }
        }
    }


    private fun setCollection(video: VideoBean) {
        if (!isCollected) {
            video.let { collectVideo(it) }
        } else {
            video.let { disCollectVideo(it) }
        }
    }

    private fun showCollectView(isCollected: Boolean) {
        val drawable: Drawable?
        if (isCollected) {
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

    /**
     * 收藏视频
     */
    private fun collectVideo(video: VideoBean) {
        user?.let {
            video.type?.let { it1 ->
                video.title?.let { it2 ->
                    video.videoUrl?.let { it3 ->
                        video.posterUrl?.let { it4 ->
                            mViewModel.uploadFavoriteVideo(
                                it.phone,
                                CollectionType.VIDEO.name,
                                it2,
                                video.type!!,
                                it3,
                                it4,
                                true
                            ).observe(this) {
                                Logger.e("收藏成功")
                                isCollected = true
                                showCollectView(isCollected)
                            }
                        }
                    }
                }
            }
        } ?: kotlin.run {
            LoginServiceProvider.skipLoginActivity(this@VideoDetailActivity)
        }
    }

    /**
     * 取消收藏视频
     */
    private fun disCollectVideo(video: VideoBean) {
        user?.let {
            video.videoUrl?.let { it1 ->
                mViewModel.disCollectVideo(
                    it.phone,
                    it1,
                    true
                ).observe(this) {
                    Logger.e("收藏成功")
                    if (it == "true") { //取消收藏成功
                        isCollected = false
                        showCollectView(isCollected)
                    }
                }
            }
        } ?: kotlin.run {
            LoginServiceProvider.skipLoginActivity(this@VideoDetailActivity)
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

    @SuppressLint("SetTextI18n")
    private fun getData() {
        mViewModel.getRecommendList(mCurrentPage, 10, showloading = true).observe(this) {
            mViewBinding.tvShare.text = "34"
            mViewBinding.tvDicuss.text = "0"
            if (it != null) {
                it.let {
                    if (mIsRefresh) {
                        if (it.isEmpty()) {
                            mRecommendAdapter.setEmptyView()
                        }
                    } else if (it.size >= totalSize) {
                        mViewBinding.smartRefresh.setEnableLoadMore(true)
                    }
                    mRecommendAdapter.submitList(it)
                    mViewBinding.smartRefresh.finishRefresh()
                }
            } else {
                if (mIsRefresh) {
                    mRecommendAdapter.setEmptyView()
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
        mViewBinding.rvRecommend.adapter = mRecommendAdapter
        mRecommendAdapter.setOnItemClickListener { _, _, position ->
            if (ClickUtil.isFastClick()) return@setOnItemClickListener
            mRecommendAdapter.getItem(position)?.let { startActivity(this@VideoDetailActivity, it) }
        }
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
        if (mViewBinding.jzVideo.screen == Jzvd.SCREEN_FULLSCREEN) {
            mViewBinding.jzVideo.autoQuitFullscreen()
        }
    }

    /**
     * 横屏
     */
    private fun changeScreenFullLandscape(x: Float) {
        //从竖屏状态进入横屏
        if (mViewBinding.jzVideo.screen != Jzvd.SCREEN_FULLSCREEN) {
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