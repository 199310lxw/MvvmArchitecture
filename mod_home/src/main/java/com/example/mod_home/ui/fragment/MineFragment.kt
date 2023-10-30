package com.example.mod_home.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mod_home.R
import com.example.mod_home.databinding.FragmentMineBinding
import com.example.mod_home.ui.activity.CollectedActivity
import com.example.mod_home.ui.activity.LookRecordActivity
import com.example.mod_home.ui.activity.SettingActivity
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.ImagePagerActivity
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setUrlCircleBorder
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.provider.UserServiceProvider

class MineFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    var userHeard: String? = null

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        mViewBinding.dampView.setTransverse(false)

        mViewBinding.imgHeader.onClick {
            if (UserServiceProvider.isLogin()) {
                userHeard?.let {
                    val list = arrayListOf<String>()
                    list.add(it)
                    ImagePagerActivity.startImagePagerActivity(requireActivity(), list, 0)
                } ?: kotlin.run {
                    LoginServiceProvider.skipLoginActivity(mContext)
                }
            } else {
                LoginServiceProvider.skipLoginActivity(mContext)
            }
        }
        mViewBinding.imgSetting.onClick {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        mViewBinding.tvLookRecord.onClick {
            startActivity(Intent(activity, LookRecordActivity::class.java))
        }

        mViewBinding.tvCollectList.onClick {
            startActivity(Intent(activity, CollectedActivity::class.java))
        }

        UserServiceProvider.getUserLiveData().observe(this) {
            setView()
        }
    }

    override fun onLazyLoadData() {
        setView()
    }

    private fun setView() {
        UserServiceProvider.getUserInfo()?.let {
            mViewBinding.imgHeader.setUrlCircleBorder(
                it.icon,
                6f,
                R.color.white
            )
            userHeard = it.icon
            mViewBinding.tcNickName.text = it.getName()?.ifEmpty { it.phone }
        } ?: kotlin.run {
            mViewBinding.imgHeader.setUrlCircleBorder(
                "",
                6f,
                R.color.white
            )
            mViewBinding.tcNickName.text = "游客"
        }
    }
}