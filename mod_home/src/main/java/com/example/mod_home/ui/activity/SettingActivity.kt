package com.example.mod_home.ui.activity

import android.os.Bundle
import com.example.mod_home.databinding.ActivitySettingBinding
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.activity.WebViewActivity
import com.xwl.common_base.dialog.MenuDialog
import com.xwl.common_base.viewmodel.EmptyViewModel
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.dialog.MessageDialog
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
    override fun initView(savedInstanceState: Bundle?) {
        languageList.add("中文")
        languageList.add("English")
        mViewBinding.sbSettingAgreement.onClick {
            WebViewActivity.start(this@SettingActivity, UrlConstants.AGREENMENT_URL, "隐私协议")
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
            showLogoutDialog()
        }
    }

    override fun initData() {

    }

    private fun showLogoutDialog() {
        val builder = MessageDialog.Builder()
        val dialog = builder.setTitleText("提示")
            .setCancelText("取消")
            .setContentText("确认退出登陆？")
            .setConfirmText("确认")
            .build()
        dialog.show(supportFragmentManager, "dialog")
        dialog.setOnItemClickListener(object : MessageDialog.OnItemClickListener {
            override fun onCancel() {

            }

            override fun onConfirm() {
                UserServiceProvider.clearUserInfo()

                dialog.dismiss()
            }
        })
    }
}