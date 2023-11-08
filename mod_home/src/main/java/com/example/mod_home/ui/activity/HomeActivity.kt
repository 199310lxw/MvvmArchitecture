package com.example.mod_home.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mod_home.R
import com.example.mod_home.adapters.FragmentAdapter
import com.example.mod_home.databinding.ActivityHomeBinding
import com.example.mod_home.ui.fragment.CommunityFragment
import com.example.mod_home.ui.fragment.HomeFragment
import com.example.mod_home.ui.fragment.MineFragment
import com.example.mod_home.ui.fragment.VideoFragment
import com.example.mod_home.viewmodel.HomeViewModel
import com.xwl.common_base.activity.BaseVmVbActivity
import com.xwl.common_base.dialog.UpdateDialog
import com.xwl.common_lib.constants.RoutMap
import com.xwl.common_lib.constants.UrlConstants
import com.xwl.common_lib.utils.AppExit

@Route(path = RoutMap.HOME_ACTIVITY_HOME)
class HomeActivity : BaseVmVbActivity<HomeViewModel, ActivityHomeBinding>() {
    private val fragmentList = arrayListOf<Fragment>()
    override fun initView(savedInstanceState: Bundle?) {
//        checkVersion()
        initTab()
    }


    private fun checkVersion() {
        UpdateDialog.Builder(this@HomeActivity).setForceUpdate(false)
            .setDownloadUrl(UrlConstants.APK_URL).setVersionName("v1.0.1").show()
    }

    private fun initTab() {
        fragmentList.add(HomeFragment.newInstance())
        fragmentList.add(VideoFragment.newInstance())
        fragmentList.add(CommunityFragment.newInstance())
        fragmentList.add(MineFragment.newInstance())
        val fragmentAdapter = FragmentAdapter(fragmentList, this@HomeActivity)
        mViewBinding.viewPager.adapter = fragmentAdapter
        mViewBinding.viewPager.setCurrentItem(0, false)
        mViewBinding.viewPager.offscreenPageLimit = 4

        mViewBinding.viewPager.isUserInputEnabled = false //是否可以滑动
        mViewBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //设置导航栏选中位置
                mViewBinding.navView.menu.getItem(position).isChecked = true
            }
        })
        
        mViewBinding.navView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> mViewBinding.viewPager.setCurrentItem(0, false)
                R.id.nav_news -> mViewBinding.viewPager.setCurrentItem(1, false)
                R.id.nav_message -> mViewBinding.viewPager.setCurrentItem(2, false)
                R.id.nav_mine -> mViewBinding.viewPager.setCurrentItem(3, false)
            }
            false
        }
    }


    override fun onResume() {
        super.onResume()
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        AppExit.onBackPressed(this)
    }
}