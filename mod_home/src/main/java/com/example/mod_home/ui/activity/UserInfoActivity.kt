package com.example.mod_home.ui.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_home.R
import com.example.mod_home.databinding.ActivityUserInfoBinding
import com.example.mod_home.dialog.SelectBirthdayDialog
import com.example.mod_home.viewmodel.UserInfoViewModel
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.orhanobut.logger.Logger
import com.sum.user.dialog.ChoosePhotoDialog
import com.sum.user.dialog.ChooseSexDialog
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.loadFile
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setUrlCircle
import com.xwl.common_lib.manager.FileManager
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.provider.UserServiceProvider
import com.xwl.common_lib.utils.ScreenUtil
import com.xwl.common_lib.utils.ViewUtils
import java.io.File

/**
 * @author mingyan.su
 * @date   2023/3/23 18:43
 * @desc   用户信息
 */
@Route(path = RoutMap.HOME_ACTIVITY_USER_INFO)
class UserInfoActivity : BaseVmVbActivity<UserInfoViewModel, ActivityUserInfoBinding>() {
    //相机拍照URI
    private var photoUri: Uri? = null

    //裁剪图片URI
    private var mUploadImageUri: Uri? = null

    //裁剪图片文件
    private var mUploadImageFile: File? = null

    //保存的头像路径
    private var saveAvatarPath: String? = null

    //拍照回调
    var mActivityResultLauncherTake: ActivityResultLauncher<Intent>? = null

    //裁剪回调
    var mActivityResultLauncherCrop: ActivityResultLauncher<Intent>? = null

    //相册回调
    var mActivityResultLauncherAlbum: ActivityResultLauncher<Intent>? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, UserInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        ViewUtils.setClipViewCornerRadius(mViewBinding.tvSave, ScreenUtil.dip2px(20f))
        initUserInfo()
        initListener()
        registerActivityResult()
    }

    /**
     * ActivityForResult
     */
    private fun registerActivityResult() {
        mActivityResultLauncherTake =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    //拍照回调
                    workCropFun(photoUri)
                }
            }
        mActivityResultLauncherCrop =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    //裁剪回调
                    setAvatar()
                }
            }
        mActivityResultLauncherAlbum =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.data.let {
                        mUploadImageUri = it
                        setAvatar()
                    }
                }
            }
    }

    /**
     * 设置用户信息
     */
    private fun initUserInfo() {
        val user = UserServiceProvider.getUserInfo() ?: return
        //通过DataBinding绑定数据
        mViewBinding.user = user
        mViewBinding.activity = this
        mViewBinding.ivHead.setUrlCircle(user.icon)
    }

    private fun initListener() {
        mViewBinding.clHead.onClick {
            showChoosePhotoDialog()
        }
        mViewBinding.clBirthday.onClick {
            showBirthdayDialog()
        }
        mViewBinding.tvSave.onClick {
            saveUserInfo()
        }
    }

    /**
     * 保存用户信息
     */
    private fun saveUserInfo() {
        val user = UserServiceProvider.getUserInfo()
        user?.let {
            if (!saveAvatarPath.isNullOrEmpty()) {
                user.icon = saveAvatarPath as String
            }
            user.nickname = mViewBinding.etName.text.toString()
            user.sex = mViewBinding.tvSex.text.toString()
            user.signature = mViewBinding.etSignature.text.toString()
            user.birthday = mViewBinding.tvBirthday.text.toString()
            Logger.e(saveAvatarPath)
            saveAvatarPath?.let {
                mViewModel.uploadUserIcon(File(it)).observe(this) {
                    it?.let {
                        user.icon = "${UrlConstants.BASE_URL}${it}"
                        mViewModel.updateUserInfo(user).observe(this) {
                            Logger.e(it.toString())
                            UserServiceProvider.saveUserInfo(user)
                            TipsToast.showTips(R.string.default_save_success)
                        }
                    }

                }
            } ?: kotlin.run {
                if (user != null) {
                    mViewModel.updateUserInfo(user).observe(this) {
                        Logger.e(it.toString())
                        UserServiceProvider.saveUserInfo(user)
                        TipsToast.showTips(R.string.default_save_success)
                    }
                }
            }
        } ?: kotlin.run {
            LoginServiceProvider.skipLoginActivity(this@UserInfoActivity)
        }
    }

    /**
     * 设置生日日期
     */
    private fun showBirthdayDialog() {
        SelectBirthdayDialog.Builder(this).setBirthDayDateCall { date ->
            mViewBinding.tvBirthday.text = date
        }.show()
    }

    /**
     * 选择图片弹框
     */
    private fun showChoosePhotoDialog() {
        XXPermissions.with(this@UserInfoActivity)
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .permission(Permission.READ_MEDIA_AUDIO)
            .permission(Permission.CAMERA)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    if (all) {
                        ChoosePhotoDialog.Builder(this@UserInfoActivity)
                            .setPhotoAlbumCall {
                                openAlbum()
                            }.setTakePicturesCall {
                                takePictures()
                            }.show()
                    } else {
                        TipsToast.showTips("请授权相关权限后再使用")
                    }
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        TipsToast.showTips("请到设置界面手动授予读写内存权限")
                    }
                }
            })
    }

    /**
     * 调取本地相机拍照
     */
    private fun takePictures() {
        val takeIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val values = ContentValues()
        //根据uri查询图片地址
        photoUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        Logger.i("photoUri:" + photoUri?.authority + ",photoUri:" + photoUri?.path)
        //放入拍照后的地址
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        //调起拍照
        mActivityResultLauncherTake?.launch(takeIntent)
    }

    /**
     * 设置用户头像
     */
    private fun setAvatar() {
        val file: File? = if (mUploadImageUri != null) {
            FileManager.getMediaUri2File(mUploadImageUri!!)
        } else {
            mUploadImageFile
        }
        saveAvatarPath = file?.absolutePath
        mViewBinding.ivHead.loadFile(file)
        Logger.i("FilePath:${file?.absolutePath}")
    }

    /**
     * 系统裁剪方法
     */
    private fun workCropFun(imgPathUri: Uri?) {
        mUploadImageUri = null
        mUploadImageFile = null
        imgPathUri?.let {
            val imageObject: Any? = FileManager.getHeadJpgFile()
            if (imageObject is Uri) {
                mUploadImageUri = imageObject
            }
            if (imageObject is File) {
                mUploadImageFile = imageObject
            }
            val intent = Intent("com.android.camera.action.CROP")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            intent.run {
                setDataAndType(it, "image/*")// 图片资源
                putExtra("crop", "true") // 裁剪
                putExtra("aspectX", 1) // 宽度比
                putExtra("aspectY", 1) // 高度比
                putExtra("outputX", 200) // 裁剪框宽度
                putExtra("outputY", 200) // 裁剪框高度
                putExtra("scale", true) // 缩放
                putExtra("return-data", false) // true-返回缩略图-data，false-不返回-需要通过Uri
                putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()) // 保存的图片格式
                putExtra("noFaceDetection", true) // 取消人脸识别
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    putExtra(MediaStore.EXTRA_OUTPUT, mUploadImageUri)
                } else {
                    val imgCropUri = Uri.fromFile(mUploadImageFile)
                    putExtra(MediaStore.EXTRA_OUTPUT, imgCropUri)
                }
            }
            mActivityResultLauncherCrop?.launch(intent)
        }
    }

    // 打开本地相册
    private fun openAlbum() {
        val intentAlbum = Intent(Intent.ACTION_PICK, null)
        intentAlbum.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        mActivityResultLauncherAlbum?.launch(intentAlbum)
    }

    /**
     * 性别选择弹框
     */
    fun showSelectSexDialog() {
        ChooseSexDialog.Builder(this).setOnSexChooseCall {
            mViewBinding.tvSex.text = it
        }.show()
    }

    override fun initData() {

    }
}