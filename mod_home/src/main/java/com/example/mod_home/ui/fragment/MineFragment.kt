package com.example.mod_home.ui.fragment


import android.os.Bundle
import android.view.View
import com.example.mod_home.R
import com.example.mod_home.databinding.FragmentMineBinding
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.fragment.BaseVmVbByLazyFragment
import com.xwl.common_lib.constants.KeyConstant
import com.xwl.common_lib.dialog.CustomerDialog
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.ext.setUrlCircleBorder
import com.xwl.common_lib.provider.LoginServiceProvider
import com.xwl.common_lib.utils.SharedPreferenceUtil

class MineFragment : BaseVmVbByLazyFragment<HomeViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        mViewBinding.dampView.setTransverse(false)

        mViewBinding.imgHeader.onClick {
            if (LoginServiceProvider.isLogin()) {
                TipsToast.showTips("用户已登陆")
            } else {
                LoginServiceProvider.skipLoginActivity(mContext)
            }

        }
        mViewBinding.btnLogout.onClick {
            showLogoutDialog()
        }
    }

    override fun onLazyLoadData() {
        mViewBinding.imgHeader.setUrlCircleBorder(
            SharedPreferenceUtil.getInstance().getString(KeyConstant.KEY_USER_HEADURL),
            6f,
            R.color.white
        )
        mViewBinding.tcNickName.text =
            SharedPreferenceUtil.getInstance().getString(KeyConstant.KEY_USER_NAME).ifEmpty { "游客" }
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
                clearUserInfo()
                mViewBinding.imgHeader.setUrlCircleBorder(
                    SharedPreferenceUtil.getInstance().getString(KeyConstant.KEY_USER_HEADURL),
                    6f,
                    R.color.white
                )
                mViewBinding.tcNickName.text =
                    SharedPreferenceUtil.getInstance().getString(KeyConstant.KEY_USER_NAME)
                        .ifEmpty { "游客" }

                doalog.dismiss()
            }
        })
    }

    private fun clearUserInfo() {
        SharedPreferenceUtil.getInstance().remove(KeyConstant.KEY_USER_PHONE)
        SharedPreferenceUtil.getInstance().remove(KeyConstant.KEY_USER_HEADURL)
        SharedPreferenceUtil.getInstance().remove(KeyConstant.KEY_USER_NAME)
        SharedPreferenceUtil.getInstance().remove(KeyConstant.KEY_SESSSION)
    }
}