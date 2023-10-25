package com.example.mod_basics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.example.mod_basics.databinding.ActivityVideoDetailBinding
import com.example.mod_basics.viewmodel.VideoDetailViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.ext.setScanImage
import com.xwl.common_lib.utils.ScreenRotateUtils

class VideoDetailActivity : BaseVmVbActivity<VideoDetailViewModel, ActivityVideoDetailBinding>(),
    ScreenRotateUtils.OrientationChangeListener {
    private var mVideoUrl: String? = null
    private var mPosterUrl: String? = null
    private var mVideoName: String? = null

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
        mVideoUrl = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_URL)
        mPosterUrl = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_POSTER_URL)
        mVideoName = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_NAME)
        mViewBinding.jzVideo.setUp(
            mVideoUrl,
            mVideoName
        )
        mViewBinding.jzVideo.posterImageView.setScanImage(mVideoUrl)
        mViewBinding.jzVideo.startVideo()
        ScreenRotateUtils.getInstance(this.applicationContext).setOrientationChangeListener(this)
    }

    override fun initData() {

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

    override fun onDestroy() {
        super.onDestroy()
        ScreenRotateUtils.getInstance(this.applicationContext).setOrientationChangeListener(null)
        Jzvd.releaseAllVideos()
    }
}