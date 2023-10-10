package com.example.mod_home.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import cn.jzvd.JZMediaSystem
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.example.lib_net.download.DownloadService
import com.example.lib_net.download.DownloadService.ServiceBinder
import com.example.mod_home.databinding.ActivityCourseDetailBinding
import com.example.mod_home.viewmodel.CourseListViewModel
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.orhanobut.logger.Logger
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.CustomMedia.JZMediaAliyun
import com.xwl.common_lib.CustomMedia.JZMediaExo
import com.xwl.common_lib.CustomMedia.JZMediaIjk
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.constants.KeyConstant.APP_UPDATE_PATH
import com.xwl.common_lib.dialog.TipsToast.showTips
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setUrl
import com.xwl.common_lib.provider.ContextServiceProvider
import com.xwl.common_lib.utils.ScreenRotateUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File


/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CourseDetailActivity : BaseVmVbActivity<CourseListViewModel, ActivityCourseDetailBinding>(),
    ScreenRotateUtils.OrientationChangeListener {

    private var mVideoUrl: String? = null
    private var mPosterUrl: String? = null

    private var conn: ServiceConnection? = null

    companion object {
        fun startActivity(mContext: Context, videoUrl: String, posterUrl: String) {
            val intent = Intent(mContext, CourseDetailActivity::class.java)
            intent.putExtra(KeyConstant.KEY_COURSE_VIDEO_URL, videoUrl)
            intent.putExtra(KeyConstant.KEY_COURSE_VIDEO_POSTER_URL, posterUrl)
            mContext.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mVideoUrl = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_URL)
        mPosterUrl = intent.getStringExtra(KeyConstant.KEY_COURSE_VIDEO_POSTER_URL)
        mViewBinding.jzVideo.setUp(
            mVideoUrl,
            ""
        )
        mViewBinding.jzVideo.posterImageView.setUrl(mPosterUrl)
        ScreenRotateUtils.getInstance(this.applicationContext).setOrientationChangeListener(this)
        mViewBinding.titleBar.getRightTextView().onClick {
//            clickChangeToAliyun()
            clickChangeToExo()
        }
        mViewBinding.imgDownload.onClick {
            checkPermission()
        }
    }


    private fun checkPermission() {
        XXPermissions.with(mContext!!)
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .permission(Permission.READ_MEDIA_AUDIO)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    checkFile(APP_UPDATE_PATH)
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        showTips("请到设置界面手动授予读写内存权限")
                    }
                }
            })
    }

    private fun checkFile(path: String) {

        Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<File?> ->
            Logger.e(path)
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            emitter.onNext(file)
            emitter.onComplete()
        }).observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<File?> {
                override fun onSubscribe(d: Disposable) {}

                // 默认最先调用复写的 onSubscribe（）
                override fun onNext(file: File) {
                    if (file.exists()) {
                        Logger.e("文件夹创建成功")
                    } else {
                        Logger.e("文件夹不存在或为空")
                    }
                    initService()
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Logger.e("开始启动下载服务")
                    Logger.e("mDownloadUrl-->$mVideoUrl")
                    intent = Intent(
                        ContextServiceProvider.service.applicationContext,
                        DownloadService::class.java
                    )
                    mVideoUrl?.let {
                        intent.putExtra(KeyConstant.KEY_DOWNLOAD_URL, it)
                        if (it.endsWith(".mp4")) {
                            intent.putExtra(KeyConstant.KEY_DOWNLOAD_FILE, "$path/test.mp4")
                        } else if (it.endsWith(".m3u8")) {
                            intent.putExtra(KeyConstant.KEY_DOWNLOAD_FILE, "$path/test.txt")
                        } else if (it.endsWith(".apk")) {
                            intent.putExtra(
                                KeyConstant.KEY_DOWNLOAD_FILE,
                                "$path/${applicationInfo.loadLabel(packageManager)}.apk"
                            )
                        } else {
                            intent.putExtra(KeyConstant.KEY_DOWNLOAD_URL, "$path/test.mp4")
                        }
                    }
                    mContext!!.startService(intent)
                    conn?.let { mContext!!.bindService(intent, it, BIND_AUTO_CREATE) }
                }
            })
    }

    private fun initService() {
        conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
                val binder = iBinder as ServiceBinder
                if (binder != null) {
                    val service = binder.service
                    service!!.setOnDownloadListener(object : DownloadService.DownloadListener {
                        override fun onSuccess(path: String) {
                        }

                        override fun onDownload(progress: Int) {
                            mViewBinding.progressBar.progress = progress
                            mViewBinding.tvProgress.text = String.format("%d%%", progress)
                        }

                        override fun onError(msg: String) {
                            showTips(msg)
                        }
                    })
                } else {
                    showTips("服务错误")
                }
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Logger.e("service is disconnected")
                showTips("服务已关闭")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ScreenRotateUtils.getInstance(this).start(this)
        JzvdStd.goOnPlayOnResume()
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


    fun clickChangeToIjkplayer(view: View?) {
        Jzvd.releaseAllVideos()
        mViewBinding.jzVideo.setUp(
            mVideoUrl,
            "",
            JzvdStd.SCREEN_NORMAL,
            JZMediaIjk::class.java
        )
        mViewBinding.jzVideo.startVideo()
        Toast.makeText(this, "Change to Ijkplayer", Toast.LENGTH_SHORT).show()
    }

    fun clickChangeToSystem(view: View?) {
        Jzvd.releaseAllVideos()
        mViewBinding.jzVideo.setUp(
            mVideoUrl,
            "",
            JzvdStd.SCREEN_NORMAL,
            JZMediaSystem::class.java
        )
        mViewBinding.jzVideo.startVideo()
        Toast.makeText(this, "Change to MediaPlayer", Toast.LENGTH_SHORT).show()
    }

    fun clickChangeToExo() {
        Jzvd.releaseAllVideos()
        mViewBinding.jzVideo.setUp(
            mVideoUrl,
            "",
            JzvdStd.SCREEN_NORMAL,
            JZMediaExo::class.java
        )
        mViewBinding.jzVideo.startVideo()
        Toast.makeText(this, "Change to ExoPlayer", Toast.LENGTH_SHORT).show()
    }


    fun clickChangeToAliyun() {
        Jzvd.releaseAllVideos()
        mViewBinding.jzVideo.setMediaInterface(JZMediaAliyun::class.java)
        mViewBinding.jzVideo.startVideo()
        Toast.makeText(this, "Change to AliyunPlayer", Toast.LENGTH_SHORT).show()
    }

}