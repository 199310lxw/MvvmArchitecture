package com.example.mod_home.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.lib_net.download.DownloadUtil.startDownload
import com.example.mod_home.databinding.DialogUpdateLayoutBinding
import com.hjq.permissions.Permission
import com.sum.user.dialog.ChoosePhotoDialog
import com.xwl.common_base.dialog.BaseDialog
import com.xwl.common_base.dialog.BaseDialogFragment
import com.xwl.common_lib.callback.Permissions
import com.xwl.common_lib.dialog.TipsToast.showTips
import com.xwl.common_lib.ext.gone
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.visible
import com.xwl.common_lib.utils.AppUtils

/**
 * @author  lxw
 * @date 2023/10/19
 * descripe
 */
class UpdateDialog {
    class Builder(activity: FragmentActivity) :
        BaseDialogFragment.Builder<ChoosePhotoDialog.Builder>(activity) {
        private val mBinding: DialogUpdateLayoutBinding =
            DialogUpdateLayoutBinding.inflate(LayoutInflater.from(activity))

        /** Apk 文件  */
        private var mApkFileName: String? = null

        /** 下载地址  */
        private var mDownloadUrl: String? = null

        /** 文件 MD5  */
        private val mFileMd5: String? = null

        /** 是否强制更新  */
        private var mForceUpdate = false

        /** 当前是否下载中  */
        private val mDownloading = false

        /** 当前是否下载完毕  */
        private val mDownloadComplete = false

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.SCALE)
            gravity = Gravity.CENTER

            mBinding.tvIgnore.onClick {
                dismiss()
            }
            mBinding.updateNow.onClick {
                downloadApk()
            }
        }

        fun setVersionName(versionName: CharSequence): Builder {
            mBinding.tvVersion.text = versionName
            return this
        }

        /**
         * 设置强制更新
         */
        fun setForceUpdate(force: Boolean): Builder {
            mForceUpdate = force
            mBinding.tvIgnore.visibility = if (force) View.GONE else View.VISIBLE
            setCancelable(!force)
            return this
        }

        /**
         * 设置下载 url
         */
        fun setDownloadUrl(url: String): Builder {
            mDownloadUrl = url
            return this
        }

        @Permissions(
            Permission.WRITE_EXTERNAL_STORAGE,
            Permission.READ_MEDIA_IMAGES,
            Permission.READ_MEDIA_VIDEO,
            Permission.READ_MEDIA_AUDIO
        )
        private fun downloadApk() {
            setCancelable(false)

            mApkFileName = "mvvmarchitecture"
            // 创建要下载的文件对象
            mDownloadUrl?.let {
                mApkFileName?.let { it1 ->
                    startDownload(context, it, it1, start = {
                        mBinding.progressBar.visible()
                        mBinding.updateNow.gone()
                        mBinding.tvProgress.visible()
                    }, { progress: Int ->
                        mBinding.progressBar.progress = progress
                        mBinding.tvProgress.text = String.format("%d%%", progress)

                    }, { path: String? ->
                        dismiss()
                        AppUtils.installApk(context, path)
                    }) { msg: String? ->
                        showTips(msg)
                    }
                }
            }
        }
    }
}