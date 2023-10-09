package com.example.mod_home.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.jzvd.Jzvd
import com.example.mod_home.databinding.ActivityCourseDetailBinding
import com.example.mod_home.viewmodel.CourseListViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.ext.setUrl


/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CourseDetailActivity : BaseVmVbActivity<CourseListViewModel, ActivityCourseDetailBinding>() {

    private var mUrl: String? = null

    companion object {
        fun startActivity(mContext: Context, type: String) {
            val intent = Intent(mContext, CourseDetailActivity::class.java)
            intent.putExtra(KeyConstant.KEY_COURSE_TYPE, type)
            mContext.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mUrl = intent.getStringExtra(KeyConstant.KEY_COURSE_TYPE)
        mViewBinding.jzVideo.setUp(
            UrlConstants.BASE_URL + "/test.mp4",
            "饺子闭眼睛"
        )
        mViewBinding.jzVideo.posterImageView.setUrl("https://img2.baidu.com/it/u=3574827082,3267311681&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1696957200&t=9a4ce225734ea732f356b2c083b1021e")
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

}