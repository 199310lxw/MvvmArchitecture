package com.example.mod_home.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mod_home.R
import com.example.mod_home.databinding.FragmentMineBinding
import com.example.mod_home.ui.activity.UserInfoActivity
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.dialog.CustomerDialog
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setUrlCircleBorder
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.provider.UserServiceProvider

class MineFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        mViewBinding.dampView.setTransverse(false)

        mViewBinding.imgHeader.onClick {
            if (UserServiceProvider.isLogin()) {
                startActivity(Intent(mContext, UserInfoActivity::class.java))
            } else {
                LoginServiceProvider.skipLoginActivity(mContext)
            }

        }
        mViewBinding.btnLogout.onClick {
            showLogoutDialog()
        }
    }

    override fun onLazyLoadData() {
        setView()
    }

    private fun showLogoutDialog() {
        val builder = CustomerDialog.Builder()
        val doalog = builder.setTitleText("提示")
//            .setIsCancelVisible(false)
            .setCancelText("取消")
            .setContentText("是否确认退出登陆？")
            .setConfirmText("确认")
            .build()
        doalog.show(parentFragmentManager, "dialog")
        doalog.setOnItemClickListener(object : CustomerDialog.OnItemClickListener {
            override fun onCancel() {

            }

            override fun onConfirm() {
                UserServiceProvider.clearUserInfo()
                setView()

                doalog.dismiss()
            }
        })
    }

    private fun setView() {
        UserServiceProvider.getUserInfo()?.let {
            mViewBinding.imgHeader.setUrlCircleBorder(
                it.icon,
                6f,
                R.color.white
            )
            mViewBinding.tcNickName.text = it.getName()?.ifEmpty { "游客" }
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