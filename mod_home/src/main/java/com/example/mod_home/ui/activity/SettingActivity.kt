package com.example.mod_home.ui.activity

import android.os.Bundle
import com.example.mod_home.R
import com.example.mod_home.databinding.ActivitySettingBinding
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.activity.WebViewActivity
import com.xwl.common_base.dialog.MenuDialog
import com.xwl.common_base.dialog.MessageDialog
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.dialog.TipsToast
import com.xwl.common_lib.ext.onClick
import com.xwl.common_lib.provider.UserServiceProvider

/**
 * @author  lxw
 * @date 2023/10/20
 * descripe
 */
class SettingActivity : BaseVmVbActivity<EmptyViewModel, ActivitySettingBinding>() {
    private val languageList = arrayListOf<String>()
    private var isClickLogout = false
    override fun initView(savedInstanceState: Bundle?) {
        languageList.add(resources.getString(R.string.chinese))
        languageList.add(resources.getString(R.string.english))
        mViewBinding.sbSettingAgreement.onClick {
            WebViewActivity.start(
                this@SettingActivity,
                UrlConstants.AGREENMENT_URL,
                resources.getString(com.xwl.common_lib.R.string.setting_agreement)
            )
        }

        mViewBinding.sbSettingAbout.onClick {
            WebViewActivity.start(
                this@SettingActivity,
                UrlConstants.AGREENMENT_URL,
                resources.getString(com.xwl.common_lib.R.string.setting_about)
            )
        }

        mViewBinding.sbSettingUserInfo.onClick {
            UserInfoActivity.startActivity(this@SettingActivity)
        }

        mViewBinding.sbSettingLanguage.onClick {
            MenuDialog.Builder(this@SettingActivity)
                .setListData(languageList)
                .setCancelListener { }
                .setItemSelectListener { _, data ->
                    TipsToast.showTips(data)
                }.show()
        }
        mViewBinding.tvLogout.onClick {
            if (UserServiceProvider.isLogin()) {
                showLogoutDialog()
            } else {
                TipsToast.showTips("用户还未登录，请先登录")
            }

        }
    }

    override fun initData() {
        UserServiceProvider.getUserLiveData().observe(this) {
            if (isClickLogout && it == null) {
                finish()
            }
        }
    }

    private fun showLogoutDialog() {
        MessageDialog.Builder(this@SettingActivity)
            .setContent("确认退出登陆？")
            .setCancelText("取消")
            .setConfirmText("确认")
            .setOnConfirmClickListener {
                isClickLogout = true
                UserServiceProvider.clearUserInfo()
            }
            .show()
    }
}