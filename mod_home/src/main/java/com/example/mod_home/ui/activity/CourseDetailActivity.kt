package com.example.mod_home.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mod_home.databinding.ActivityCourseDetailBinding
import com.example.mod_home.viewmodel.CourseListViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_lib.constants.KeyConstant

/**
 * @author  lxw
 * @date 2023/10/9
 * descripe
 */
class CourseDetailActivity : BaseVmVbActivity<CourseListViewModel, ActivityCourseDetailBinding>() {

    private var mType: String? = null

    companion object {
        fun startActivity(mContext: Context, type: String) {
            val intent = Intent(mContext, CourseDetailActivity::class.java)
            intent.putExtra(KeyConstant.KEY_COURSE_TYPE, type)
            mContext.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mType = intent.getStringExtra(KeyConstant.KEY_COURSE_TYPE)

    }

    override fun initData() {

    }

}